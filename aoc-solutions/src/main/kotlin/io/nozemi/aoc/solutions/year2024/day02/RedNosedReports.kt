package io.nozemi.aoc.solutions.year2024.day02

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class RedNosedReports(
    override val parser: AbstractPuzzleParser<List<RedNosedReport>> = RedNoseReportParser()
) : AbstractPuzzle<List<RedNosedReport>>() {

    private class RedNoseReportParser : AbstractPuzzleParser<List<RedNosedReport>>() {

        override fun parse(input: Stream<String>): List<RedNosedReport> = input.map { report ->
            RedNosedReport(
                report.trim()
                    .split(" ")
                    .mapNotNull { level -> level.toIntOrNull() }
                    .toMutableList()
            )
        }.toList()
    }

    private val maxLevelDiff = 3

    override val solutions: PuzzleSolutions<List<RedNosedReport>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(input: List<RedNosedReport>) = input.count {
        it.isValid(maxLevelDiff, false)
    }.toLong()

    private fun part2(input: List<RedNosedReport>) = input.count {
        it.isValid(maxLevelDiff, true)
    }.toLong()
}

fun main() {
    RedNosedReports().solve()
}