package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.matrix.interfaces.IFixedSizeMatrix

class FixedDouble2DMatrix(
    val values: DoubleArray = DoubleArray(4, { 0.0 })
) : IFixedSizeMatrix<Double> {
    override val rows = 2
    override val cols = 2

    private fun index(r: Int, c: Int) = r * cols + c

    override val determinant get() = this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]

    override fun get(row: Int, column: Int) = values[index(row, column)]

    override fun set(row: Int, column: Int, value: Double) {
        values[index(row, column)] = value
    }

    override fun inverse(): FixedDouble2DMatrix {
        val d = determinant

        return FixedDouble2DMatrix(
            doubleArrayOf(
                this[1, 1] / d,
                -this[0, 1] / d,
                -this[1, 0] / d,
                this[0, 0] / d
            )
        )
    }

    override fun multiply(other: IFixedSizeMatrix<Double>): IFixedSizeMatrix<Double> {
        TODO("Not yet implemented")
    }

    /**
     * @param other The other array/matrix to multiply by.
     * @param tolerance The threshold for numerical errors
     */
    fun multiply(other: DoubleArray, tolerance: Double = 0.00001): DoubleArray {
        if (other.size != 2)
            error("Invalid size of array to multiply by")

        val result = (this * other).map {
            //if (it - it.roundToInt() <= tolerance)
            //    it.roundToInt() + 0.0
            //else it
            it
        }.toDoubleArray()

        return result
    }

    /**
     * @param other The other array/matrix to multiply by.
     * @param tolerance The threshold for numerical errors
     */
    fun multiply(other: LongArray, tolerance: Double = 0.00001): DoubleArray {
        if (other.size != 2)
            error("Invalid size of array to multiply by")

        val result = doubleArrayOf(
            (this[0, 0] * other[0] + this[0, 1] * other[1]),
            (this[1, 0] * other[0] + this[1, 1] * other[1])
        )

        return result
    }

    operator fun times(other: DoubleArray): DoubleArray {
        if (other.size != 2)
            error("Invalid size of array to multiply by")

        return doubleArrayOf(
            this[0, 0] * other[0] + this[0, 1] * other[1],
            this[1, 0] * other[0] + this[1, 1] * other[1]
        )
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (y in 0 until rows) {
            for (x in 0 until cols) {
                builder.append(this[y, x])
                    .append(" ")
            }
            builder.appendLine()
        }

        return builder.toString()
    }
}