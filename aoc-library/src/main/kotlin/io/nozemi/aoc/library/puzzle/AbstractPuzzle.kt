package io.nozemi.aoc.library.puzzle

import com.github.michaelbull.logging.InlineLogger
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream
import kotlin.collections.forEachIndexed
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.reflect.KFunction1
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

val logger = InlineLogger(AbstractPuzzle::class)

typealias PuzzleSolutions<T> = List<KFunction1<T, Long>>

abstract class AbstractPuzzle<T> {
    private val basePackage = "io.nozemi.aoc.solutions"
    private val dayOfYearRegex = Regex("$basePackage\\.year(\\d{4})\\.day(\\d{2})")

    private val inputFilePath: Path

    private val puzzleName = javaClass.simpleName
    private val year: Int
    private val day: Int

    protected abstract val parser: (input: Stream<String>) -> T
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

    fun loadInput(): List<ParsedInput<T>> = findInput().map {
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

        ParsedInput(title, expectedAnswers, parser(Files.lines(file).skip(linesToSkip)))
    }

    fun solve() {
        val parsedInputs = loadInput()

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
                    null -> ANSI_YELLOW
                    answer.value -> ANSI_GREEN
                    else -> ANSI_RED
                }

                println("- ${ANSI_BOLD}Part ${part + 1}:$ANSI_RESET $validAnswer${answer.value}$ANSI_RESET (${answer.duration})")
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

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BOLD = "\u001B[1m"
const val ANSI_UNDERLINE = "\u001B[4m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"