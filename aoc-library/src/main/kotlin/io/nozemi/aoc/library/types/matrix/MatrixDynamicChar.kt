package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IMatrix
import io.nozemi.aoc.library.types.vector.IVector2
import io.nozemi.aoc.library.types.vector.Vector2Int

open class MatrixDynamicChar(
    private val values: Array<CharArray>
) : IMatrix<Char> {

    init {
        values.forEachIndexed { row, rowCols ->
            val newArray = CharArray(cols) { ' ' }
            rowCols.copyInto(newArray)
            values[row] = newArray
        }
    }

    override val cols get() = values.maxBy { it.size }.size
    override val rows get() = values.size

    override val cells: List<MatrixCell<Char>>
        get() = values.flatMapIndexed { y, columns ->
            columns.mapIndexed { x, value -> MatrixCell(Vector2Int(x, y), value) }
        }

    override val distinctValues: List<Char>
        get() = values.flatMap { it.distinct() }

    override fun getOrNull(pos: IVector2<Int>): Char? {
        if (!isWithinBounds(pos))
            return null

        return values[pos.y][pos.x]
    }

    override fun set(pos: IVector2<Int>, value: Char): MatrixDynamicChar {
        val newValues = values.copyOf()
        newValues[pos.y][pos.x] = value

        return MatrixDynamicChar(newValues)
    }

    override fun findAll(predicate: (MatrixCell<Char>) -> Boolean) = cells.filter(predicate)

    // TODO: This only reverses the y-axis, so probably not the best logic for the method here...
    override fun reversed() = MatrixDynamicChar(values.reversedArray())

    override fun transposed() = MatrixDynamicChar(Array(cols) { x ->
        CharArray(rows) { y ->
            values[y][x]
        }
    })

    override fun copyOf() = MatrixDynamicChar(values.copyOf())

    override fun iterator() = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, value -> MatrixCell(Vector2Int(x, y), value) }
    }.iterator()

    override fun toString() = values.joinToString("\n") {
        it.joinToString("")
    }

    companion object {

        fun from(input: Iterable<String>) = MatrixDynamicChar(
            input.map { it.toCharArray() }.toTypedArray()
        )
    }
}