package io.nozemi.aoc.solutions.year2025.day02

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class GiftShop : AbstractPuzzle<List<LongRange>>() {

    override val parser: (input: Stream<String>) -> List<LongRange> = { input ->
        input.map { ranges ->
            ranges.split(",").map { range ->
                val numbers = range.split("-").map { it.toLong() }

                LongRange(numbers[0], numbers[1])
            }.toList()
        }.toList()[0]
    }

    override val solutions: PuzzleSolutions<List<LongRange>> = listOf(
        ::part1
    )

    fun part1(input: List<LongRange>): Long {
        val numbers = mutableListOf<Long>()

        input.forEach { range ->
            numbers.addAll(range.toList())
        }

        val invalidIds = mutableListOf<Long>()
        val stringNumbers = numbers.map { it.toString() }
        stringNumbers.forEach { number ->
            if (number.length % 2 == 0) {
                val part1 = number.take(number.length / 2)
                val part2 = number.takeLast(number.length / 2)

                if (part1 == part2)
                    invalidIds.add(number.toLong())
            }
        }

        return invalidIds.sum()
    }
}

fun main() {
    GiftShop().solve()
}