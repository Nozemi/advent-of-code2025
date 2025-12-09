package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IMatrix
import io.nozemi.aoc.library.types.vector.Vector2

open class MatrixDynamicDouble(
    private val values: Array<DoubleArray>
) : IMatrix<Double> {
    override val cols get() = values.getOrNull(0)?.size ?: 0
    override val rows get() = values.size

    override val cells: List<MatrixCell<Double>>
        get() = values.flatMapIndexed { y, columns ->
            columns.mapIndexed { x, value -> MatrixCell(Vector2(x, y), value) }
        }

    override val distinctValues: List<Double>
        get() = values.flatMap { it.distinct() }

    override fun getOrNull(pos: Vector2): Double? {
        if (!isWithinBounds(pos))
            return null

        return values[pos]
    }

    override fun set(pos: Vector2, value: Double): MatrixDynamicDouble {
        val newValues = values.copyOf()
        newValues[pos] = value

        return MatrixDynamicDouble(newValues)
    }

    override fun findAll(predicate: (MatrixCell<Double>) -> Boolean) = cells.filter(predicate)

    // TODO: This only reverses the y-axis, so probably not the best logic for the method here...
    override fun reversed() = MatrixDynamicDouble(values.reversedArray())

    override fun transposed() = MatrixDynamicDouble(Array(cols) { x ->
        DoubleArray(rows) { y ->
            values[y][x]
        }
    })

    override fun copyOf() = MatrixDynamicDouble(values.copyOf())

    override fun iterator() = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, value -> MatrixCell(Vector2(x, y), value) }
    }.iterator()

    private operator fun Array<DoubleArray>.get(pos: Vector2) = this[pos.y.toInt()][pos.x.toInt()]
    private operator fun Array<DoubleArray>.set(pos: Vector2, value: Double) {
        this[pos.y.toInt()][pos.x.toInt()] = value
    }
}