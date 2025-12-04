package io.nozemi.aoc

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import io.nozemi.aoc.library.puzzle.PuzzleResolver
import io.nozemi.aoc.library.puzzle.inputDownloader
import java.nio.file.Files
import java.time.LocalDate

class ChallengeSelectScreen : CliktCommand() {
    private val date: LocalDate = LocalDate.now()

    val years: String by option(
        names = arrayOf("--years", "-y"),
        help = "input year(s) [example: 2020,2021,... || 2020-2025 || both together]"
    ).prompt(
        default = "${date.year}"
    )

    val days: String by option(
        names = arrayOf("--days", "-d"),
        help = "input day(s) [example: 1,2,... || 3-5 || both together]"
    ).prompt(
        default = "${date.dayOfMonth}",
    )

    var token: String? = null

    init {
       //if (!inputDownloader.hasAccessToken)
       //    this.registerOption(
       //        option("token").prompt(
       //            default = null
       //        )
       //    )
    }

    override fun run() {
        //if (!inputDownloader.hasAccessToken)
        //AocTokenCommand().main()

        this.echo("Solving day(s) '$days' for year(s) '$years'")

        val years = parseInput(years)
        val days = parseInput(days)
    }

    private fun parseInput(input: String): List<Int> {
        val values = mutableListOf<Int>()

        input.trim().replace(" ", "").split(",").forEach { value ->
            when (value.contains("-")) {
                true -> {
                    val range = value.split("-").map { it.toInt() }
                    for (i in range[0] until range[1] + 1) {
                        values.add(i)
                    }
                }

                false -> values.add(value.toInt())
            }
        }

        return values
    }
}

class AocTokenCommand : CliktCommand() {
    val token: String by option(
        names = arrayOf("--token"),
        help = "The advent of code token found in your browser cookies"
    ).prompt(
        default = null
    )

    override fun run() {
        if (token.isNotBlank()) {
            Files.writeString(inputDownloader.tokenFile, token)
            echo("AOC token was written...")
        }
    }
}

fun main(args: Array<String>) {
    val resolver = PuzzleResolver()

    resolver.resolvePuzzles()

    if (!inputDownloader.hasAccessToken)
        AocTokenCommand().main(args)

    ChallengeSelectScreen()
        .context {
            terminal = Terminal(ansiLevel = AnsiLevel.TRUECOLOR, interactive = true)
        }.main(args)
}