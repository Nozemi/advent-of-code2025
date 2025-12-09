@file:Suppress("unused")

package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction3D
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

data class Vector3(
    val x: Long,
    val y: Long,
    val z: Long
) {
    constructor(x: Int, y: Int, z: Int) : this(x.toLong(), y.toLong(), z.toLong())

    operator fun plus(direction: Direction3D) = plus(direction.value)
    operator fun plus(other: Vector3) = Vector3(
        x = x + other.x,
        y = y + other.y,
        z = z + other.z
    )

    operator fun minus(direction: Direction3D) = minus(direction.value)
    operator fun minus(other: Vector3) = Vector3(
        x = x - other.x,
        y = y - other.y,
        z = z - other.z
    )

    operator fun times(value: Long) = times(Vector3(value, value, value))
    operator fun times(other: Vector3) = Vector3(
        x = x * other.x,
        y = y * other.y,
        z = z * other.z
    )

    operator fun div(value: Long) = div(Vector3(value, value, value))
    operator fun div(other: Vector3) = Vector3(
        x = x / other.x,
        y = y / other.y,
        z = z / other.z
    )

    fun distanceTo(other: Vector3) = Vector3(
        x = max(x, other.x) - min(x, other.x),
        y = max(y, other.y) - min(y, other.y),
        z = max(z, other.z) - min(z, other.z)
    )

    fun distanceSquared(other: Vector3): Long {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z

        return dx * dx + dy * dy + dz * dz
    }

    fun euclideanDistanceTo(other: Vector3) = sqrt(1.0 * distanceSquared(other)).toLong()

    override fun toString() = "(x=$x, y=$y, z=$z)"
}