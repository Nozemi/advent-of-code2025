package io.nozemi.aoc.solutions.year2025.day06

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.CharMatrixParser
import io.nozemi.aoc.library.types.matrix.VariableCharMatrix

class TrashCompactor(
    override val parser: AbstractPuzzleParser<VariableCharMatrix> = CharMatrixParser()
) : AbstractPuzzle<VariableCharMatrix>() {

    override val solutions: PuzzleSolutions<VariableCharMatrix> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(matrix: VariableCharMatrix): Long {
        val parsed = matrix.toString().split("\n").map {
            it.trim().replace("\\s+".toRegex(), " ")
                .split(" ")
        }

        val operators = parsed.last()
        val groups = parsed.dropLast(1).map {
            it.map { num -> num.toLong() }
        }

        val mapped = groups.flatMap { group ->
            group.mapIndexed { index, num -> index to num }
        }.groupBy { it.first }
            .map { it.key to it.value.map { a -> a.second } }
            .toMap()

        return mapped.map { (group, numbers) ->
            val operator = operators[group]

            return@map when (operator) {
                "+" -> numbers.sum()
                "*" -> numbers.reduce { a, b -> a * b }
                else -> 0
            }
        }.sum()
    }

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
    TrashCompactor().solve()
}