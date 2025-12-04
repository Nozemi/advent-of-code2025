package io.nozemi.aoc.solutions.year2025.day04

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.CharMatrixParser
import io.nozemi.aoc.library.types.Direction
import io.nozemi.aoc.library.types.matrix.VariableCharMatrix
import io.nozemi.aoc.library.types.vectors.Vector2

class PrintingDepartment(
    override val parser: AbstractPuzzleParser<VariableCharMatrix> = CharMatrixParser()
) : AbstractPuzzle<VariableCharMatrix>() {


    override val solutions: PuzzleSolutions<VariableCharMatrix> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(matrix: VariableCharMatrix): Long =
        accessibleRolls(matrix).size.toLong()

    private fun part2(matrix: VariableCharMatrix): Long {
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
                matrix.setAt(it, '.')
            }
        }

        return removedRolls.size.toLong()
    }

    private fun accessibleRolls(matrix: VariableCharMatrix): List<Vector2> {
        val accessibleRolls = matrix.map { cell ->
            if (cell.value == '.')
                return@map null

            val adjacent = listOf(
                cell.coordinates + Direction.NORTH,
                cell.coordinates + Direction.SOUTH,
                cell.coordinates + Direction.EAST,
                cell.coordinates + Direction.WEST,
                cell.coordinates + Direction.NORTH_EAST,
                cell.coordinates + Direction.NORTH_WEST,
                cell.coordinates + Direction.SOUTH_EAST,
                cell.coordinates + Direction.SOUTH_WEST
            ).filter { (x, y) -> x >= 0 && y >= 0 && x < matrix.cols && y < matrix.rows }
                .map { matrix.getAt(it) }

            if (adjacent.count { it == '@' } < 4)
                return@map cell.coordinates

            return@map null
        }.filterNotNull()
            .distinct()

        return accessibleRolls
    }
}

fun main() {
    PrintingDepartment().solve()
}