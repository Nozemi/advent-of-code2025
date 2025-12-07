package io.nozemi.aoc.solutions.year2024.day01

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream
import kotlin.math.max
import kotlin.math.min

class HistorianHysteria(
    override val parser: AbstractPuzzleParser<List<List<Int>>> = HistorianHysteriaParser()
) : AbstractPuzzle<List<List<Int>>>() {

    private class HistorianHysteriaParser : AbstractPuzzleParser<List<List<Int>>>() {
        override fun parse(input: Stream<String>): List<List<Int>> {
            val pattern = Regex(" +")

            return input.map { line ->
                pattern.replace(line, " ").split(" ")
                    .map { it.toInt() }
            }.toList()
        }
    }

    override val solutions: PuzzleSolutions<List<List<Int>>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(input: List<List<Int>>): Long {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        input.forEach {
            left.add(it.first())
            right.add(it.last())
        }

        val sortedL = left.sorted()
        val sortedR = right.sorted()

        val distances = mutableListOf<Int>()
        for (i in sortedL.indices) {
            distances.add(max(sortedL[i], sortedR[i]) - min(sortedL[i], sortedR[i]))
        }

        return distances.sum().toLong()
    }

    private fun part2(input: List<List<Int>>): Long {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        input.forEach {
            left.add(it.first())
            right.add(it.last())
        }

        var score = 0
        left.forEach { leftId ->
            score += leftId * right.count { it == leftId }
        }

        return score.toLong()
    }
}

fun main() {
    HistorianHysteria().solve()
}