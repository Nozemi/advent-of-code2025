package io.nozemi.aoc.solutions.year2025.day05

import io.nozemi.aoc.library.extensions.merged
import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class Cafeteria(
    override val parser: AbstractPuzzleParser<Ingredients> = IngredientsParser()
) : AbstractPuzzle<Ingredients>() {

    class IngredientsParser : AbstractPuzzleParser<Ingredients>() {

        override fun parse(input: Stream<String>): Ingredients {
            val ranges = mutableListOf<LongRange>()
            val available = mutableListOf<Long>()

            input.forEach { line ->
                if (line.isBlank())
                    return@forEach

                if (line.contains('-')) {
                    val range = line.split('-').map { it.toLong() }
                    ranges.add(LongRange(range[0], range[1]))
                    return@forEach
                }

                available.add(line.toLong())
            }

            return Ingredients(available, ranges.merged)
        }
    }

    override val solutions: PuzzleSolutions<Ingredients> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(ingredients: Ingredients): Long =
        ingredients.available.count { available ->
            ingredients.fresh.any { available in it }
        }.toLong()

    private fun part2(ingredients: Ingredients): Long =
        ingredients.fresh.sumOf { (it.last - it.first) + 1 }
}

data class Ingredients(
    val available: List<Long>,
    val fresh: List<LongRange>
)

fun main() {
    Cafeteria().solve()
}