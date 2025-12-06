package io.nozemi.aoc.solutions.year2025.day06

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.CharMatrixParser
import io.nozemi.aoc.library.types.matrix.VariableCharMatrix
import java.util.stream.Stream

class TrashCompactor(
    override val parser: AbstractPuzzleParser<VariableCharMatrix> = CharMatrixParser()
) : AbstractPuzzle<VariableCharMatrix>() {

    class MathGridParser : AbstractPuzzleParser<MathGrid>() {
        override fun parse(input: Stream<String>): MathGrid =
            MathGrid.from(input.toList())
    }

    override val solutions: PuzzleSolutions<VariableCharMatrix> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(matrix: VariableCharMatrix): Long = 0
    private fun part2(matrix: VariableCharMatrix): Long {
        val orientatedMatrix = matrix.transposed().reversed()

        var sum = 0L
        var operator: Operator? = null
        var numbers = mutableListOf<Long>()
        for (y in 0..<orientatedMatrix.rows) {
            val number = StringBuilder()

            for (x in 0..<orientatedMatrix.cols) {
                val current = orientatedMatrix[x, y]

                operator = Operator.entries.singleOrNull { it.symbol == current }

                if (current.digitToIntOrNull() != null) {
                    number.append(current)
                    continue
                }
            }

            if (number.isNotBlank())
                numbers.add(number.toString().toLong())

            when (operator) {
                Operator.PLUS -> sum += numbers.sum()
                Operator.MULTIPLY -> sum += numbers.reduce { a, b -> a * b }
                null -> {}
                else -> {}
            }

            if (operator != null) {
                operator = null
                numbers = mutableListOf()
            }
        }

        //println(orientatedMatrix)

        return sum
    }
}

enum class Operator(val symbol: Char) {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/')
}

fun main() {
    TrashCompactor().solve(exampleOnly = false)
}

/*
1 2 3 3 2 8 5 1 1 6 4 0
4 5 1 6 4 0 3 8 7 2 3 0
6 1 1 9 8 0 2 1 5 3 1 4
* * * + + + * * * + + +
 */