package io.nozemi.aoc.library.types.vectors

import io.nozemi.aoc.library.types.Direction
import kotlin.math.max
import kotlin.math.min

interface IVector2<T> {
    val x: T
    val y: T

    operator fun plus(other: IVector2<T>): IVector2<T>
    operator fun plus(direction: Direction): IVector2<T>

    operator fun minus(other: IVector2<T>): IVector2<T>
    operator fun minus(direction: Direction): IVector2<T>

    operator fun times(other: IVector2<T>): IVector2<T>
    operator fun times(value: T): IVector2<T>

    operator fun div(other: IVector2<T>): IVector2<T>
    operator fun div(value: T): IVector2<T>

    fun distanceFrom(other: IVector2<T>): IVector2<T>
}

data class Vector2(override val x: Int, override val y: Int) : IVector2<Int> {
    override operator fun plus(other: IVector2<Int>) = Vector2(x + other.x, y + other.y)
    override operator fun plus(direction: Direction) = plus(direction.value)

    override operator fun minus(other: IVector2<Int>) = Vector2(x - other.x, y - other.y)
    override operator fun minus(direction: Direction) = minus(direction.value)

    override operator fun times(other: IVector2<Int>) = Vector2(x * other.x, y * other.y)
    override operator fun times(value: Int) = Vector2(x * value, y * value)
    operator fun times(value: Double) = Vector2l(x * value.toLong(), y * value.toLong())
    operator fun times(value: Long) = Vector2l(x * value, y * value)

    override operator fun div(other: IVector2<Int>) = Vector2(x / other.x, y / other.y)
    override operator fun div(value: Int) = Vector2(x / value, y / value)

    override fun distanceFrom(other: IVector2<Int>) = Vector2(
        max(x, other.x) - min(x, other.x),
        max(y, other.y) - min(y, other.y)
    )

    override fun toString(): String {
        return "${this::class.simpleName}(x=$x, y=$y)"
    }
}

data class Vector2l(override val x: Long, override val y: Long) : IVector2<Long> {
    operator fun plus(value: Long) = Vector2l(x + value, y + value)
    override operator fun plus(other: IVector2<Long>) = Vector2l(x + other.x, y + other.y)
    override operator fun plus(direction: Direction) = plus(Vector2l(direction.x.toLong(), direction.y.toLong()))

    override operator fun minus(other: IVector2<Long>) = Vector2l(x - other.x, y - other.y)
    override operator fun minus(direction: Direction) = minus(Vector2l(direction.x.toLong(), direction.y.toLong()))

    override operator fun times(other: IVector2<Long>) = Vector2l(x * other.x, y * other.y)
    override operator fun times(value: Long) = Vector2l(x * value, y * value)

    override operator fun div(other: IVector2<Long>) = Vector2l(x / other.x, y / other.y)
    override operator fun div(value: Long) = Vector2l(x / value, y / value)

    override fun distanceFrom(other: IVector2<Long>) = Vector2l(
        max(x, other.x) - min(x, other.x),
        max(y, other.y) - min(y, other.y)
    )

    override fun toString(): String {
        return "${this::class.simpleName}(x=$x, y=$y)"
    }
}