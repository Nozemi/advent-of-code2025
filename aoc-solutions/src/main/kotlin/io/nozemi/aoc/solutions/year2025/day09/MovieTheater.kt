package io.nozemi.aoc.solutions.year2025.day09

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.Vector2Parser
import io.nozemi.aoc.library.types.vector.Vector2
import kotlin.math.max
import kotlin.math.min

class MovieTheater(
    override val parser: AbstractPuzzleParser<List<Vector2>> = Vector2Parser()
) : AbstractPuzzle<List<Vector2>>() {

    override val solutions: PuzzleSolutions<List<Vector2>> = listOf(
        ::part1
    )

    private fun part1(corners: List<Vector2>): Long {
        val cornerPairs: List<CornerPair> = buildList {
            for ((idx, a) in corners.withIndex()) {
                for (b in corners.subList(0, idx)) {
                    add(CornerPair(a, b))
                }
            }

            sortBy(CornerPair::gridSize)
        }

        return cornerPairs.maxByOrNull { it.gridSize }?.gridSize ?: 0
    }

    private class CornerPair(val a: Vector2, val b: Vector2) {

        val gridSize: Long get() {
            val cols = max(a.x, b.x) - min(a.x, b.x) + 1
            val rows = max(a.y, b.y) - min(a.y, b.y) + 1

            return cols * rows
        }

        override fun toString() = "$a -> $b = $gridSize"
    }
}

fun main() {
    MovieTheater().solve(exampleOnly = false)
}