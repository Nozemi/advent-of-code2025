package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction2D
import kotlin.math.max
import kotlin.math.min

data class Vector2Long(override val x: Long, override val y: Long) : IVector2<Long> {
    operator fun plus(value: Long) = Vector2Long(x + value, y + value)
    override operator fun plus(other: IVector2<Long>) = Vector2Long(x + other.x, y + other.y)
    override operator fun plus(direction: Direction2D) = plus(direction.asLong)

    override operator fun minus(other: IVector2<Long>) = Vector2Long(x - other.x, y - other.y)
    override operator fun minus(direction: Direction2D) = minus(direction.asLong)

    override operator fun times(other: IVector2<Long>) = Vector2Long(x * other.x, y * other.y)
    override operator fun times(value: Long) = Vector2Long(x * value, y * value)

    override operator fun div(other: IVector2<Long>) = Vector2Long(x / other.x, y / other.y)
    override operator fun div(value: Long) = Vector2Long(x / value, y / value)

    override fun distanceFrom(other: IVector2<Long>) = Vector2Long(
        max(x, other.x) - min(x, other.x),
        max(y, other.y) - min(y, other.y)
    )

    override fun toString(): String {
        return "${this::class.simpleName}(x=$x, y=$y)"
    }
}