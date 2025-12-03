package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.vectors.Vector2

data class MatrixCell<T>(val x: Int, val y: Int, val value: T?) {
    constructor(coordinates: Vector2, value: T?) : this(coordinates.x, coordinates.y, value)

    val coordinates = Vector2(x, y)
}