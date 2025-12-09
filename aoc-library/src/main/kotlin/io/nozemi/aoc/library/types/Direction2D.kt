@file:Suppress("unused")

package io.nozemi.aoc.library.types

import io.nozemi.aoc.library.types.vector.Vector2

enum class Direction2D(val value: Vector2) {
    NORTH(Vector2(0, -1)),
    SOUTH(Vector2(0, 1)),
    EAST(Vector2(1, 0)),
    WEST(Vector2(-1, 0)),

    NORTH_WEST(NORTH.value + WEST.value),
    NORTH_EAST(NORTH.value + EAST.value),
    SOUTH_WEST(SOUTH.value + WEST.value),
    SOUTH_EAST(SOUTH.value + EAST.value)
    ;

    val x = value.x
    val y = value.y

    companion object {

        val orthogonal = listOf(
            NORTH, WEST,
            SOUTH, EAST
        )

        val diagonal = listOf(
            NORTH_WEST, NORTH_EAST,
            SOUTH_WEST, SOUTH_EAST
        )
    }
}