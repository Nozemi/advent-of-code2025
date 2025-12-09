package io.nozemi.aoc.solutions.year2025.day07

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.CharMatrixParser
import io.nozemi.aoc.library.types.matrix.MatrixDynamicChar
import io.nozemi.aoc.library.types.vector.Vector2

class Laboratories(
    override val parser: AbstractPuzzleParser<MatrixDynamicChar> = CharMatrixParser()
) : AbstractPuzzle<MatrixDynamicChar>() {

    override val solutions: PuzzleSolutions<MatrixDynamicChar> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(matrix: MatrixDynamicChar): Int =
        matrix.beamSplits().size

    private fun part2(matrix: MatrixDynamicChar): Long =
        matrix.beamPaths(matrix.first { it.value == beamStart }.pos)

    private fun MatrixDynamicChar.beamPaths(start: Vector2): Long {
        val cache = Array(this.rows) { LongArray(this.cols) }

        for (x in 0 until this.cols) {
            cache[this.rows - 1][x] = 1L
        }

        for (y in this.rows - 2 downTo 0) {
            for (x in 0..<this.cols) {
                when (this[x, y]) {
                    emptyTile, beamStart -> {
                        cache[y][x] = cache[y + 1][x]
                    }

                    beamSplitter -> {
                        var total = 0L
                        if (x > 0) total += cache[y + 1][x - 1]
                        if (x < this.cols - 1) total += cache[y + 1][x + 1]
                        cache[y][x] = total
                    }
                }
            }
        }

        return cache[start]
    }

    private fun MatrixDynamicChar.beamSplits(
        currentRow: Long? = null,
        columns: List<Long> = listOf(),
        splitters: List<Vector2> = listOf(),
    ): List<Vector2> {
        if (currentRow == null) {
            val start = this.first { it.value == beamStart }.pos

            return this.beamSplits(
                start.y,
                listOf(start.x),
                listOf()
            )
        }

        val currentRow: Long = currentRow

        if (currentRow >= this.rows)
            return splitters

        val rowSplitters = this.findAll { cell ->
            cell.y == currentRow && cell.value == beamSplitter
        }.filter { it.x in columns }.map { it.pos }

        val additionalColumns = rowSplitters.map { it.x }
            .flatMap { x ->
                listOf(x - 1, x + 1)
            }

        val columns = (columns + additionalColumns).filter {
            it !in rowSplitters.map { s -> s.x }
        }

        return beamSplits(currentRow + 1, columns, splitters + rowSplitters)
    }

    private operator fun Array<LongArray>.get(pos: Vector2) = this[pos.y.toInt()][pos.x.toInt()]

    private val emptyTile = '.'
    private val beamStart = 'S'
    private val beamSplitter = '^'
    private val beamPath = '|'
}

fun main() {
    Laboratories().solve(exampleOnly = false)
}