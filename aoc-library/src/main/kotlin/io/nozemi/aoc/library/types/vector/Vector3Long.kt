package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction3D
import kotlin.math.max
import kotlin.math.min

data class Vector3Long(
    override val x: Long,
    override val y: Long,
    override val z: Long
) : IVector3<Long> {

    override fun plus(direction: Direction3D) = plus(direction.asLong)
    override fun plus(other: IVector3<Long>) = Vector3Long(
        x = x + other.x,
        y = y + other.y,
        z = z + other.z
    )

    override fun minus(direction: Direction3D) = minus(direction.asLong)
    override fun minus(other: IVector3<Long>) = Vector3Long(
        x = x - other.x,
        y = y - other.y,
        z = z - other.z
    )

    override fun times(value: Long) = times(Vector3Long(value, value, value))
    override fun times(other: IVector3<Long>) = Vector3Long(
        x = x * other.x,
        y = y * other.y,
        z = z * other.z
    )

    override fun div(value: Long) = div(Vector3Long(value, value, value))
    override fun div(other: IVector3<Long>) = Vector3Long(
        x = x / other.x,
        y = y / other.y,
        z = z / other.z
    )

    override fun distanceFrom(other: IVector3<Long>) = Vector3Long(
        x = max(x, other.x) - min(x, other.x),
        y = max(y, other.y) - min(y, other.y),
        z = max(z, other.z) - min(z, other.z)
    )

    override fun toString() = "${this::class.simpleName}(x=$x, y=$y, z=$z)"
}