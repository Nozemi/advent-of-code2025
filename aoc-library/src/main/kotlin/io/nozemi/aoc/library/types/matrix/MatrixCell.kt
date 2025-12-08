@file:Suppress("unused")

package io.nozemi.aoc.library.types.matrix

import io.nozemi.aoc.library.types.vector.IVector2
import io.nozemi.aoc.library.types.vector.Vector2Int

data class MatrixCell<T>(
    val pos: IVector2<Int>,
    val value: T?
) {
    constructor(x: Int, y: Int, value: T?) : this(Vector2Int(x, y), value)

    val x get() = pos.x
    val y get() = pos.y
}