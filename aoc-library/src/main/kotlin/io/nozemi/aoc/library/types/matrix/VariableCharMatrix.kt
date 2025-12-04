package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IVariableSizeMatrix
import io.nozemi.aoc.library.types.vectors.Vector2
import java.util.stream.Stream

class VariableCharMatrix(
    private val values: Array<CharArray>
) : IVariableSizeMatrix<Char> {
    override val cols get() = values.getOrNull(0)?.size ?: 0
    override val rows get() = values.size
    override val size get() = rows * cols

    override val distinctValues get() = values.map { it.distinct() }.flatten().distinct()

    override fun transposed(): VariableCharMatrix {

        return VariableCharMatrix(Array(cols) { x ->
            CharArray(rows) { y ->
                values[y][x]
            }
        })
    }

    fun forEach(action: (row: CharArray) -> Unit) {
        values.forEach { action(it) }
    }

    override fun getAtOrNull(coords: Vector2): Char? {
        if (!isWithinBounds(coords)) return null

        return values[coords.y][coords.x]
    }

    override fun setAt(coords: Vector2, value: Char): Boolean {
        if (!isWithinBounds(coords)) return false

        values[coords.y][coords.x] = value
        return true
    }

    operator fun get(x: Int, y: Int) = values[y][x]
    operator fun set(x: Int, y: Int, value: Char) {
        values[y][x] = value
    }

    override fun findAll(search: Char): List<Vector2> = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (col == search) return@mapIndexed Vector2(x, y)

            return@mapIndexed null
        }.filterNotNull()
    }

    override fun toString() = values.joinToString("\n") { it.joinToString(" ") }
    override fun copyOf() = VariableCharMatrix(values.copyOf())

    override fun iterator() = values.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> MatrixCell(x, y, values[y][x]) }
    }.flatten().iterator()

    companion object {

        fun from(rows: Int, cols: Int, defaultValue: Char = '.') = VariableCharMatrix(
            Array(rows) { CharArray(cols) { defaultValue } }
        )

        fun from(input: Iterable<String>) = VariableCharMatrix(
            input.map { it.toCharArray() }.toList().toTypedArray()
        )

        fun from(input: String) = VariableCharMatrix(
            input.split("\n").map { it.toCharArray() }.toTypedArray()
        )
    }
}