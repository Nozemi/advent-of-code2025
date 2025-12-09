package io.nozemi.aoc.solutions.year2025.day04

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.CharMatrixParser
import io.nozemi.aoc.library.types.matrix.MatrixDynamicChar
import io.nozemi.aoc.library.types.vector.Vector2

class PrintingDepartment(
    override val parser: AbstractPuzzleParser<MatrixDynamicChar> = CharMatrixParser()
) : AbstractPuzzle<MatrixDynamicChar>() {


    override val solutions: PuzzleSolutions<MatrixDynamicChar> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(matrix: MatrixDynamicChar): Long =
        accessibleRolls(matrix).size.toLong()

    private fun part2(matrix: MatrixDynamicChar): Long {
        var matrix = matrix
        val removedRolls = mutableListOf<Vector2>()

        var remove = true
        while (remove) {
            val accessible = accessibleRolls(matrix)
            if (accessible.isEmpty()) {
                remove = false
                continue
            }

            accessible.forEach {
                removedRolls.add(it)
                matrix = matrix.set(it, '.')
            }
        }

        return removedRolls.size.toLong()
    }

    private fun accessibleRolls(matrix: MatrixDynamicChar): List<Vector2> {
        val accessibleRolls = matrix.map { cell ->
            if (cell.value == '.')
                return@map null

            val adjacentRolls = matrix.adjacent(
                from = cell.pos,
                includeDiagonals = true,
                filter = { (cell) ->
                    cell.value == '@'
                }
            ).size

            if (adjacentRolls < 4)
                return@map cell.pos

            return@map null
        }.filterNotNull()
            .distinct()

        return accessibleRolls
    }
}

fun main() {
    PrintingDepartment().solve()
}