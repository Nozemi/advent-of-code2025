package io.nozemi.aoc.climode

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import io.nozemi.aoc.library.cli.ansi.ANSI_BLUE
import io.nozemi.aoc.library.cli.ansi.ANSI_BOLD
import io.nozemi.aoc.library.cli.ansi.ANSI_PURPLE
import io.nozemi.aoc.library.cli.ansi.ANSI_RESET
import io.nozemi.aoc.library.puzzle.PuzzleResolver
import java.time.LocalDate

class SelectPuzzleCommand(
    private val resolver: PuzzleResolver
) : CliktCommand() {
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

        //this.echo("Solving day(s) '$days' for year(s) '$years'")

        println("")

        val years = parseInput(years)
        val days = parseInput(days)

        solvePuzzles(years, days)
    }

    private fun solvePuzzles(years: List<Int>, days: List<Int>) {
        years.forEach yearLoop@ { year ->
            if (!resolver.hasPuzzlesForYear(year))
                return@yearLoop

            echo("${ANSI_PURPLE}${ANSI_BOLD}==== Solutions for $year ====")
            echo("${ANSI_PURPLE}${ANSI_BOLD}============================${ANSI_RESET}")

            days.forEach dayLoop@ { day ->
                val puzzle = resolver.getPuzzle(year, day)
                    ?: return@dayLoop

                println("${ANSI_BLUE}${ANSI_BOLD}== Day $day - ${puzzle.name}${ANSI_RESET}")

                puzzle.solve()
            }
        }
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