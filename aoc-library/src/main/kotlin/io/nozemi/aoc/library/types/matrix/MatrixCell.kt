@file:Suppress("unused")

package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.vector.Vector2

data class MatrixCell<T>(
    val pos: Vector2,
    val value: T?
) {
    constructor(x: Long, y: Long, value: T?) : this(Vector2(x, y), value)
    constructor(x: Int, y: Int, value: T?) : this(Vector2(x.toLong(), y.toLong()), value)

    val x get() = pos.x
    val y get() = pos.y
}