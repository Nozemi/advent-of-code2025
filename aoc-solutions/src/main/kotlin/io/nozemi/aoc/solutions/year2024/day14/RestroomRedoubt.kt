package io.nozemi.aoc.solutions.year2024.day14

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions

class RestroomRedoubt(
    override val parser: AbstractPuzzleParser<Any>
) : AbstractPuzzle<Any>() {

    override val solutions: PuzzleSolutions<Any> = listOf()
}

fun main() {
    for (i in 1..100) {
        println(
            "$i: " + when {
                i % 3 == 0 && i % 5 == 0 -> "FizzBuzz"
                i % 3 == 0 -> "Fizz"
                i % 5 == 0 -> "Buzz"
                else -> i.toString()
            }
        )
    }
}