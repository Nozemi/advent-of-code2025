package io.nozemi.aoc.library.puzzle

import com.github.michaelbull.logging.InlineLogger
import com.github.michaelbull.result.onSuccess
import io.nozemi.aoc.library.cli.ansi.*
import io.nozemi.aoc.library.puzzle.InputDownloader.Companion.inputDownloader
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.forEachIndexed
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.time.TimedValue
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

abstract class AbstractPuzzle<T> {
    val logger = InlineLogger(AbstractPuzzle::class)

    private val basePackage = "io.nozemi.aoc.solutions"
    private val dayOfYearRegex = Regex("$basePackage\\.year(\\d{4})\\.day(\\d{2})")

    private val inputFilePath: Path

    private val year: Int
    private val day: Int

    val name = javaClass.simpleName

    protected abstract val parser: AbstractPuzzleParser<T>
    abstract val solutions: PuzzleSolutions<T>

    init {
        val dayAndYear = dayOfYearRegex.find(javaClass.packageName)
            ?: error(
                "Package name '${javaClass.packageName}' is not valid. " +
                        "Follow the correct format: $basePackage.year0000.day00"
            )

        year = dayAndYear.groupValues[1].toInt()
        day = dayAndYear.groupValues[2].toInt()

        inputFilePath = Path.of("./data/inputs/${year}")
    }

    private fun findInput(): List<String> {
        val dir = Path("./data").resolve("inputs").resolve("year$year")

        logger.debug { "Finding input for day '$day' in: $dir" }

        if (!dir.exists() || !dir.isDirectory()) {
            logger.error { "Directory didn't exist, or wasn't a directory: $dir" }
            return emptyList()
        }

        val files = dir.toFile().listFiles()

        logger.trace { "Found ${files?.size ?: 0} files in: $dir" }

        val matching = files?.filter { file ->
            file.extension == "txt" && file.name.startsWith("day$day")
        }

        logger.debug { "Found ${matching?.size} matching files in: $dir" }

        return matching?.map { it.path } ?: emptyList()
    }

    private val v = "$ANSI_BOLD$ANSI_CYAN"
    private val r = ANSI_RESET
    fun loadInput(exampleOnly: Boolean = false): List<ParsedInput<T>> {
        val input = findInput()
        if (input.none { !it.contains("example") }) {
            inputDownloader.downloadInput(year, day).onSuccess {
                return loadInput(exampleOnly)
            }
        }

        val parsedInput = input.filter {
            (exampleOnly && it.contains("example")) || !exampleOnly
        }.map {
                val file = Path(it)
                var title = file.name
                val expectedAnswers = mutableListOf<Long>()

                val linesToSkip: Long
                val duration = measureTime {
                    Files.lines(file).use { lines ->
                        lines.takeWhile { line ->
                            line.startsWith("TITLE:") || line.startsWith("PART ")
                        }.use { result ->
                            val properties = result.toList()

                            linesToSkip = properties.size.toLong()

                            properties.forEach { line ->
                                if (line.startsWith("TITLE:"))
                                    title = line.replaceFirst("TITLE:", "").trim()

                                if (line.startsWith("PART "))
                                    expectedAnswers.add(line.replaceFirst("PART \\d+:".toRegex(), "").trim().toLong())
                            }
                        }
                    }
                }

                val parsed = measureTimedValue { parser.parse(Files.lines(file).skip(linesToSkip)) }
                println("Parsing: $v$title$r, headers in $v$duration$r, data in $v${parsed.duration}$r")
                ParsedInput(title, expectedAnswers, parsed.value)
            }

        println()

        return parsedInput
    }

    fun solve(exampleOnly: Boolean = false) {
        val parsedInputs = loadInput(exampleOnly)

        parsedInputs.forEach { input ->
            solutions.forEachIndexed { index, solution ->
                repeat(0) {
                    solution.invoke(input.data)
                }

                input.answers[index] = measureTimedValue { solution.invoke(input.data).toString().toLong() }
            }
        }

        parsedInputs.forEach { input ->
            println(ANSI_BOLD + ANSI_BLUE + "== Input: ${input.title} ==" + ANSI_RESET)
            input.answers.forEach { (part, answer) ->
                val expected = input.expectedAnswers.getOrNull(part)
                val validAnswer = when (expected) {
                    null -> Pair(ANSI_YELLOW, false)
                    answer.value -> Pair(ANSI_GREEN, false)
                    else -> Pair(ANSI_RED, true)
                }

                val (color, failed) = validAnswer

                var output = "$color${answer.value}${ANSI_RESET}"
                if (failed)
                    output += " (Expected: ${ANSI_GREEN}${expected}${ANSI_RESET})"

                println("- ${ANSI_BOLD}Part ${part + 1}:${ANSI_RESET} $output ($ANSI_BOLD$ANSI_CYAN${answer.duration}$ANSI_RESET)")
            }
            println()
        }
    }

    data class ParsedInput<T>(
        val title: String,
        val expectedAnswers: List<Long>,
        val data: T,
        val answers: MutableMap<Int, TimedValue<Any>> = mutableMapOf()
    )
}