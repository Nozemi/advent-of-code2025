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
        ::part1,
        ::part2
    )

    private fun stringNumbers(input: List<LongRange>) =
        input.map {
            it.toList()
        }.flatten()
            .map {
                it.toString()
            }

    private fun findInvalidIds(ids: List<LongRange>, vararg conditions: (number: String) -> Boolean): List<Long> {
        val invalidIds = mutableListOf<Long>()
        val stringNumbers = stringNumbers(ids)
        stringNumbers.forEach { number ->
            if (conditions.any { it(number)})
                invalidIds.add(number.toLong())
        }

        return invalidIds
    }

    private fun part1(input: List<LongRange>): Long {
        val invalidIds = findInvalidIds(input, {number ->
            if (number.length % 2 == 0) {
                val part1 = number.take(number.length / 2)
                val part2 = number.takeLast(number.length / 2)

                return@findInvalidIds part1 == part2
            }

            return@findInvalidIds false
        })

        return invalidIds.sum()
    }

    private fun part2(input: List<LongRange>): Long {
        val invalidIds = findInvalidIds(input, {number ->
            return@findInvalidIds number.matches("([0-9]+)\\1+".toRegex())
        })

        return invalidIds.sum()
    }
}

fun main() {
    GiftShop().solve()
}