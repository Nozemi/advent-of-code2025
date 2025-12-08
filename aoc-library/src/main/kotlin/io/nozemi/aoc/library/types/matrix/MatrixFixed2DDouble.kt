package io.nozemi.aoc.library.types.matrix

class MatrixFixed2DDouble(
    values: Array<DoubleArray> = Array(2) { DoubleArray(2) }
) : MatrixDynamicDouble(values) {

    init {
        if (rows > 2 || cols > 2)
            error("Not a valid 2x2 matrix")
    }
}