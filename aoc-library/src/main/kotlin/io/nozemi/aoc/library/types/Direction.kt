package io.nozemi.aoc.library.types

import io.nozemi.aoc.library.types.vectors.IVector2
import io.nozemi.aoc.library.types.vectors.Vector2

enum class Direction(val value: IVector2<Int>) {
    NORTH(Vector2(0, -1)),
    SOUTH(Vector2(0, 1)),
    EAST(Vector2(1, 0)),
    WEST(Vector2(-1, 0)),

    NORTH_WEST(Vector2(-1, -1)),
    NORTH_EAST(Vector2(1, -1)),
    SOUTH_WEST(Vector2(-1, 1)),
    SOUTH_EAST(Vector2(1, 1));

    val x = value.x
    val y = value.y

    companion object {

        val orthogonal = listOf(
            NORTH, WEST, EAST, SOUTH
        )
    }
}