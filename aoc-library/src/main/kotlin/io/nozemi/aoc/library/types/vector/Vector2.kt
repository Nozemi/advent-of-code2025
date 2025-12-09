@file:Suppress("unused")

package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction2D
import kotlin.math.max
import kotlin.math.min

data class Vector2(
    val x: Long,
    val y: Long
) {
    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    operator fun plus(value: Long) = Vector2(x + value, y + value)
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun plus(direction: Direction2D) = plus(direction.value)

    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun minus(direction: Direction2D) = minus(direction.value)

    operator fun times(other: Vector2) = Vector2(x * other.x, y * other.y)
    operator fun times(value: Long) = Vector2(x * value, y * value)

    operator fun div(other: Vector2) = Vector2(x / other.x, y / other.y)
    operator fun div(value: Long) = Vector2(x / value, y / value)

    fun distanceFrom(other: Vector2) = Vector2(
        max(x, other.x) - min(x, other.x), max(y, other.y) - min(y, other.y)
    )

    override fun toString() = "(x=$x, y=$y)"
}