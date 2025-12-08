package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction3D
import kotlin.math.max
import kotlin.math.min

data class Vector3Int(
    override val x: Int,
    override val y: Int,
    override val z: Int
) : IVector3<Int> {

    override fun plus(direction: Direction3D) = plus(direction.value)
    override fun plus(other: IVector3<Int>) = Vector3Int(
        x = x + other.x,
        y = y + other.y,
        z = z + other.z
    )

    override fun minus(direction: Direction3D) = minus(direction.value)
    override fun minus(other: IVector3<Int>) = Vector3Int(
        x = x - other.x,
        y = y - other.y,
        z = z - other.z
    )

    override fun times(value: Int) = times(Vector3Int(value, value, value))
    override fun times(other: IVector3<Int>) = Vector3Int(
        x = x * other.x,
        y = y * other.y,
        z = z * other.z
    )

    override fun div(value: Int) = div(Vector3Int(value, value, value))
    override fun div(other: IVector3<Int>) = Vector3Int(
        x = x / other.x,
        y = y / other.y,
        z = z / other.z
    )

    override fun distanceFrom(other: IVector3<Int>) = Vector3Int(
        x = max(x, other.x) - min(x, other.x),
        y = max(y, other.y) - min(y, other.y),
        z = max(z, other.z) - min(z, other.z)
    )

    override fun toString() = "${this::class.simpleName}(x=$x, y=$y, z=$z)"
}