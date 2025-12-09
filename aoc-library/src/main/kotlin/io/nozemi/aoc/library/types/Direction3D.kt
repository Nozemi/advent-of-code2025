@file:Suppress("unused")

package io.nozemi.aoc.library.types

import io.nozemi.aoc.library.types.vector.Vector3

enum class Direction3D(val value: Vector3) {
    UNDER(Vector3(0, 0, -1)),
    OVER(Vector3(0, 0, 1)),
    WEST(Vector3(-1, 0, 0)),
    EAST(Vector3(1, 0, 0)),
    NORTH(Vector3(0, 1, 0)),
    SOUTH(Vector3(0, -1, 0)),

    NORTH_WEST(NORTH.value + WEST.value)
    ;

    companion object {
        val orthogonal = listOf(
            OVER, UNDER, NORTH, SOUTH, WEST, EAST
        )

        val diagonal = listOf(
            NORTH_WEST
        )
    }
}