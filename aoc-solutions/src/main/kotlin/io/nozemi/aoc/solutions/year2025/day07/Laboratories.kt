package io.nozemi.aoc.solutions.year2025.day07

import io.nozemi.aoc.library.cli.ansi.*
import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.CharMatrixParser
import io.nozemi.aoc.library.types.Direction
import io.nozemi.aoc.library.types.matrix.VariableCharMatrix
import io.nozemi.aoc.library.types.vectors.Vector2
import kotlin.collections.first

class Laboratories(
    override val parser: AbstractPuzzleParser<VariableCharMatrix> = CharMatrixParser()
) : AbstractPuzzle<VariableCharMatrix>() {

    override val solutions: PuzzleSolutions<VariableCharMatrix> = listOf(
        ::part1
    )

    private fun part1(matrix: VariableCharMatrix): Int {
        val startingPoint = matrix.first { it.value == beamStart }.coordinates
        matrix.setAt(startingPoint, beamPath)

        val splitters = matrix.beamSplits(startingPoint.y, listOf(startingPoint.x))

        //matrix.printColorized(colorRules)

        return splitters.size
    }

    private fun VariableCharMatrix.beamSplits(
        currentRow: Int,
        columns: List<Int>,
        splitters: List<Vector2> = listOf()
    ): List<Vector2> {
        columns.forEach { column ->
            if (this.getAtOrNull(column, currentRow) == '.')
                this.setAt(column, currentRow, beamPath)
        }

        if (currentRow > this.rows)
            return splitters

        val rowSplitters = this.findAll { cell ->
            cell.y == currentRow && cell.value == beamSplitter
        }.filter { it.x in columns }

        val additionalColumns = rowSplitters.flatMap { (x) ->
            listOf(x - 1, x + 1)
        }

        val columns = (columns + additionalColumns).filter {
            it !in rowSplitters.map { s -> s.x }
        }

        return beamSplits(currentRow + 1, columns + additionalColumns, splitters + rowSplitters)
    }

    private fun VariableCharMatrix.printColorized(colorRules: Map<Char, String>) {
        var output = this.toString()
        colorRules.forEach { (char, color) ->
            output = output.replace("$char", "$color$char$ANSI_RESET")
        }

        println(output)
        println()
    }

    private val beamStart = 'S'
    private val beamSplitter = '^'
    private val beamPath = '|'

    private val colorRules = mapOf(
        beamStart to ANSI_RED,
        beamSplitter to ANSI_BLUE + ANSI_BOLD,
        beamPath to ANSI_GREEN + ANSI_BOLD
    )
}

fun main() {
    Laboratories().solve(exampleOnly = false)
}