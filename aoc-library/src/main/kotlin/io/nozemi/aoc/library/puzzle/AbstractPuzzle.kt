package io.nozemi.aoc.library.puzzle

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.library.cli.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream
import kotlin.collections.forEachIndexed
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class AbstractPuzzle<T> {
    val logger = InlineLogger(AbstractPuzzle::class)

    private val basePackage = "io.nozemi.aoc.solutions"
    private val dayOfYearRegex = Regex("$basePackage\\.year(\\d{4})\\.day(\\d{2})")

    private val inputFilePath: Path

    private val puzzleName = javaClass.simpleName
    private val year: Int
    private val day: Int

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

        inputFilePath = Path.of("./data/input/${year}")
    }

    private fun findInput(): List<String> {
        val dir = Path("./data")

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

    fun loadInput(ignoredPatterns: List<String> = listOf()): List<ParsedInput<T>> =
        findInput()
            .filter {
                !ignoredPatterns.any { ignore ->
                    it == ignore
                            || it.startsWith(ignore)
                            || it.endsWith(ignore)
                            || it.contains(ignore)
                }
            }
            .map {
                val file = Path(it)
                var title = file.name
                val expectedAnswers = mutableListOf<Long>()

                val linesToSkip: Long
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

                ParsedInput(title, expectedAnswers, parser.parse(Files.lines(file).skip(linesToSkip)))
            }

    fun solve(ignoredPatterns: List<String> = listOf()) {
        val parsedInputs = loadInput(ignoredPatterns)

        println("")
        val titleLine = "==   Solving $puzzleName   =="
        println(ANSI_BOLD + ANSI_BLUE + "=".padEnd(titleLine.length, '=') + ANSI_RESET)
        println(ANSI_BOLD + ANSI_BLUE + titleLine + ANSI_RESET)
        println(ANSI_BOLD + ANSI_BLUE + "=".padEnd(titleLine.length, '=') + ANSI_RESET)

        parsedInputs.forEach { input ->
            solutions.forEachIndexed { index, solution ->
                input.answers[index] = measureTimedValue { solution.invoke(input.data) }
            }
        }

        parsedInputs.forEach { input ->
            println(ANSI_BOLD + ANSI_BLUE + "==== ${input.title} ====" + ANSI_RESET)
            input.answers.forEach { (part, answer) ->
                val expected = input.expectedAnswers.getOrNull(part)
                val validAnswer = when (expected) {
                    null -> Pair(ANSI_YELLOW, false)
                    answer.value -> Pair(ANSI_GREEN, false)
                    else -> Pair(ANSI_RED, true)
                }

                val (color, failed) = validAnswer

                var output = "$color${answer.value}$ANSI_RESET"
                if (failed)
                    output += " (Expected: $ANSI_GREEN${expected}$ANSI_RESET)"

                println("- ${ANSI_BOLD}Part ${part + 1}:$ANSI_RESET $output (${answer.duration})")
            }
            println("")
        }
    }

    data class ParsedInput<T>(
        val title: String,
        val expectedAnswers: List<Long>,
        val data: T,
        val answers: MutableMap<Int, TimedValue<Any>> = mutableMapOf()
    )
}