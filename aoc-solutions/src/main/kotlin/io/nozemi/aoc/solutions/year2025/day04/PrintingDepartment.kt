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
        ::part1
    )

    private fun part1(matrix: VariableCharMatrix): Long {
        val accessibleRolls = mutableListOf<Vector2>()

        matrix.forEach { cell ->
            if (cell.value == '.')
                return@forEach

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
                accessibleRolls.add(cell.coordinates)
        }

        return accessibleRolls.distinct().size.toLong()
    }
}

fun main() {
    PrintingDepartment().solve()
}