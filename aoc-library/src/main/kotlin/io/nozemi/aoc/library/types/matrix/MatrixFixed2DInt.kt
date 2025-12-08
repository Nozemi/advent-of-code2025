package io.nozemi.aoc.library.types.matrix

class MatrixFixed2DInt(
    values: Array<IntArray> = Array(2) { IntArray(2) }
) : MatrixDynamicInt(values) {

    init {
        if (rows > 2 || cols > 2)
            error("Not a valid 2x2 matrix")
    }
}