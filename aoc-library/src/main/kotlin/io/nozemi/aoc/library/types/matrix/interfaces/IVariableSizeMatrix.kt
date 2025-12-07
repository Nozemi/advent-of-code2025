package io.nozemi.aoc.library.types.matrix.interfaces

import io.nozemi.aoc.library.types.Direction
import io.nozemi.aoc.library.types.matrix.MatrixCell
import io.nozemi.aoc.library.types.vectors.Vector2
import kotlin.collections.plusAssign

interface IVariableSizeMatrix<T> : Iterable<MatrixCell<T>> {
    val cols: Int
    val rows: Int
    val size: Int

    val distinctValues: List<T>

    fun index(x: Int, y: Int) = y * cols + x
    fun transposed(): IVariableSizeMatrix<T>

    fun isWithinBounds(coords: Vector2) = coords.x in 0..<cols && coords.y in 0..<rows

    fun getAt(coords: Vector2): T = getAtOrNull(coords) ?: error("No value at $coords")
    fun getAt(x: Int, y: Int) = getAt(Vector2(x, y))
    fun getAt(coords: List<Vector2>): List<T> = coords.mapNotNull { getAt(it) }
    fun getAtOrNull(coords: Vector2): T?
    fun getAtOrNull(x: Int, y: Int) = getAtOrNull(Vector2(x, y))

    fun setAt(x: Int, y: Int, value: T) = setAt(Vector2(x, y), value)
    fun setAt(coords: Vector2, value: T): Boolean
    fun setAt(coords: List<Vector2>, value: T): Map<Vector2, Boolean> =
        coords.associateWith { setAt(it, value) }

    fun set(cell: MatrixCell<T>) = setAt(
        Vector2(cell.x, cell.y),
        cell.value ?: error("Cell value cannot be null")
    )

    fun findAll(predicate: (MatrixCell<T>) -> Boolean): List<Vector2>
    fun findAll(search: T): List<Vector2>

    fun adjacent(
        coords: Vector2,
        includeDiagonal: Boolean = false,
        allowOutOfBounds: Boolean = false,
        filter: ((Pair<MatrixCell<T>, Direction>) -> Boolean)? = null
    ): List<Pair<MatrixCell<T>, Direction>> {
        val directions = mutableListOf(
            Direction.NORTH,
            Direction.SOUTH,
            Direction.EAST,
            Direction.WEST
        )

        if (includeDiagonal)
            directions += listOf(
                Direction.NORTH_WEST,
                Direction.NORTH_EAST,
                Direction.SOUTH_WEST,
                Direction.SOUTH_EAST
            )

        return directions.map { Pair(coords + it, it) }
            .filter { allowOutOfBounds || isWithinBounds(it.first) }
            .map { Pair(MatrixCell(it.first, getAt(it.first)), it.second) }
            .filter { filter?.invoke(it) ?: true }
    }

    val allGroups get() = distinctValues.associateWith { findGroups(it) }

    fun findGroups(value: T): List<List<Vector2>> {
        val visited = mutableSetOf<Vector2>()
        val currentGroups = mutableListOf<List<Vector2>>()

        this.findAll(value).forEach valueLoop@{ v ->
            if (v in visited)
                return@valueLoop

            val group = mutableListOf<Vector2>()
            floodFill(v, value, visited, group)

            if (group.isEmpty())
                return@valueLoop

            currentGroups.add(group)
        }

        return currentGroups
    }

    fun floodFill(
        start: Vector2,
        value: T,
        visited: MutableSet<Vector2>,
        group: MutableList<Vector2>
    ) {
        val stack = mutableListOf(start)

        while (stack.isNotEmpty()) {
            val current = stack.removeAt(stack.size - 1)

            if (current in visited)
                continue

            visited.add(current)
            if (this.getAt(current) == value) {
                group.add(current)

                stack.addAll(
                    this.adjacent(current)
                        .map { it.first.coordinates }
                        .filter {
                            it !in visited && this.getAt(current) == value
                        }
                )
            }
        }
    }

    fun copyOf(): IVariableSizeMatrix<T>
}