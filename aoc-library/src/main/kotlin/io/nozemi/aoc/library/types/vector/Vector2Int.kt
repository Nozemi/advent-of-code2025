package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction2D
import kotlin.math.max
import kotlin.math.min

data class Vector2Int(override val x: Int, override val y: Int) : IVector2<Int> {

    override operator fun plus(other: IVector2<Int>) =
        Vector2Int(x + other.x, y + other.y)
    override operator fun plus(direction: Direction2D) =
        plus(direction.value)

    override operator fun minus(other: IVector2<Int>) =
        Vector2Int(x - other.x, y - other.y)
    override operator fun minus(direction: Direction2D) =
        minus(direction.value)

    override operator fun times(other: IVector2<Int>) =
        Vector2Int(x * other.x, y * other.y)
    override operator fun times(value: Int) =
        Vector2Int(x * value, y * value)

    operator fun times(value: Double) =
        Vector2Long(x * value.toLong(), y * value.toLong())

    operator fun times(value: Long) =
        Vector2Long(x * value, y * value)

    override operator fun div(other: IVector2<Int>) =
        Vector2Int(x / other.x, y / other.y)

    override operator fun div(value: Int) =
        Vector2Int(x / value, y / value)

    override fun distanceFrom(other: IVector2<Int>) = Vector2Int(
        max(x, other.x) - min(x, other.x),
        max(y, other.y) - min(y, other.y)
    )

    override fun toString() = "${this::class.simpleName}(x=$x, y=$y)"
}