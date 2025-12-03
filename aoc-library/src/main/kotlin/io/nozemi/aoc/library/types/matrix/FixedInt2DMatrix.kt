package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IFixedSizeMatrix

class FixedInt2DMatrix(
    private val values: IntArray = IntArray(4)
) : IFixedSizeMatrix<Int> {
    override val rows: Int = 2
    override val cols: Int = 2

    private fun index(r: Int, c: Int) = r * cols + c

    override val determinant: Int
        get() = this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]

    override fun inverse(): IFixedSizeMatrix<Int> {
        TODO("Not yet implemented")
    }

    override fun multiply(other: IFixedSizeMatrix<Int>): IFixedSizeMatrix<Int> {
        TODO("Not yet implemented")
    }

    override fun get(row: Int, column: Int) = values[index(row, column)]

    override fun set(row: Int, column: Int, value: Int) {
        values[index(row, column)] = value
    }
}