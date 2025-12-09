package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IMatrix
import io.nozemi.aoc.library.types.vector.Vector2

open class MatrixDynamicInt(
    private val values: Array<IntArray>
) : IMatrix<Int> {
    override val cols get() = values.getOrNull(0)?.size ?: 0
    override val rows get() = values.size

    override val cells: List<MatrixCell<Int>>
        get() = values.flatMapIndexed { y, columns ->
            columns.mapIndexed { x, value -> MatrixCell(Vector2(x, y), value) }
        }

    override val distinctValues: List<Int>
        get() = values.flatMap { it.distinct() }

    override fun getOrNull(pos: Vector2): Int? {
        if (!isWithinBounds(pos))
            return null

        return values[pos]
    }

    override fun set(pos: Vector2, value: Int): MatrixDynamicInt {
        val newValues = values.copyOf()
        newValues[pos] = value

        return MatrixDynamicInt(newValues)
    }

    override fun findAll(predicate: (MatrixCell<Int>) -> Boolean) = cells.filter(predicate)

    // TODO: This only reverses the y-axis, so probably not the best logic for the method here...
    override fun reversed() = MatrixDynamicInt(values.reversedArray())

    override fun transposed() = MatrixDynamicInt(Array(cols) { x ->
        IntArray(rows) { y ->
            values[y][x]
        }
    })

    override fun copyOf() = MatrixDynamicInt(values.copyOf())

    override fun iterator() = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, value -> MatrixCell(Vector2(x, y), value) }
    }.iterator()

    private operator fun Array<IntArray>.get(pos: Vector2) = this[pos.y.toInt()][pos.x.toInt()]
    private operator fun Array<IntArray>.set(pos: Vector2, value: Int) {
        this[pos.y.toInt()][pos.x.toInt()] = value
    }

    companion object {

        fun from(input: Iterable<String>) = MatrixDynamicInt(
            input.map { it.map { digit -> digit.digitToInt() }.toIntArray() }.toTypedArray()
        )
    }
}