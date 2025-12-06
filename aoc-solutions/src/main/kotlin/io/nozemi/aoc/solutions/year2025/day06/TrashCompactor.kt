package io.nozemi.aoc.solutions.year2025.day06

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class TrashCompactor(
    override val parser: AbstractPuzzleParser<MathGrid> = MathGridParser()
) : AbstractPuzzle<MathGrid>() {

    class MathGridParser : AbstractPuzzleParser<MathGrid>() {
        override fun parse(input: Stream<String>): MathGrid =
            MathGrid.from(input.toList())
    }

    override val solutions: PuzzleSolutions<MathGrid> = listOf(
        ::part1
    )

    private fun part1(grid: MathGrid): Long = grid.sumOfGroups.sum()
}

fun main() {
    TrashCompactor().solve(exampleOnly = false)
}