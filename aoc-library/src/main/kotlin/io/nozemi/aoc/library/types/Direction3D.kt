package io.nozemi.aoc.library.types

import io.nozemi.aoc.library.extensions.asLong
import io.nozemi.aoc.library.types.vector.IVector3
import io.nozemi.aoc.library.types.vector.Vector3Int

enum class Direction3D(val value: IVector3<Int>) {
    UNDER(Vector3Int(0, 0, -1)),
    OVER(Vector3Int(0, 0, 1)),
    WEST(Vector3Int(-1, 0, 0)),
    EAST(Vector3Int(1, 0, 0)),
    NORTH(Vector3Int(0, 1, 0)),
    SOUTH(Vector3Int(0, -1, 0)),

    NORTH_WEST(NORTH.value + WEST.value)
    ;

    val asLong get() = value.asLong

    companion object {
        val orthogonal = listOf(
            OVER, UNDER, NORTH, SOUTH, WEST, EAST
        )

        val diagonal = listOf(
            NORTH_WEST
        )
    }
}