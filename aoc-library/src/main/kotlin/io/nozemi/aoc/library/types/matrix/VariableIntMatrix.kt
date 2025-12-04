package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IVariableSizeMatrix
import io.nozemi.aoc.library.types.vectors.Vector2
import java.util.stream.Stream

class VariableIntMatrix(
    private val values: Array<IntArray>
) : IVariableSizeMatrix<Int> {
    override val cols get() = values.getOrNull(0)?.size ?: 0
    override val rows get() = values.size
    override val size get() = rows * cols

    override val distinctValues
        get() = values.map { it.distinct() }
            .flatten()
            .distinct()

    override fun transposed(): VariableIntMatrix {

        return VariableIntMatrix(Array(cols) { x ->
            IntArray(rows) { y ->
                values[y][x]
            }
        })
    }

    fun getRow(y: Int) = values[y]
    fun getColumn(x: Int) = filter { it.x == x }.mapNotNull { it.value }.toIntArray()

    override fun getAtOrNull(coords: Vector2): Int? {
        if (!isWithinBounds(coords))
            return null

        return values[coords.y][coords.x]
    }

    override fun setAt(coords: Vector2, value: Int): Boolean {
        if (!isWithinBounds(coords))
            return false

        values[coords.y][coords.x] = value
        return true
    }

    override fun findAll(search: Int): List<Vector2> = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (col == search)
                return@mapIndexed Vector2(x, y)

            return@mapIndexed null
        }.filterNotNull()
    }

    override fun toString() = values.joinToString("\n") { it.joinToString(" ") }
    override fun copyOf() = VariableIntMatrix(values.copyOf())

    override fun iterator() = values.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> MatrixCell(x, y, values[y][x]) }
    }.flatten().iterator()

    operator fun get(x: Int, y: Int) = values[y][x]
    operator fun set(x: Int, y: Int, value: Int) {
        values[y][x] = value
    }

    companion object {

        fun from(rows: Int, cols: Int, defaultValue: Int = Int.MIN_VALUE) = VariableIntMatrix(
            Array(rows) { IntArray(cols) { defaultValue } }
        )

        fun from(input: Iterable<String>) = VariableIntMatrix(
            input.map { line ->
                line.mapNotNull { it.digitToIntOrNull() }.toIntArray()
            }.toList().toTypedArray()
        )

        fun from(input: String) = VariableIntMatrix(
            input.split("\n").map { line ->
                line.mapNotNull { it.digitToIntOrNull() }.toIntArray()
            }.toTypedArray()
        )
    }
}