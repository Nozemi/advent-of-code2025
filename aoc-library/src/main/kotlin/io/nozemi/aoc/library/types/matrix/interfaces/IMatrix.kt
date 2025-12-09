@file:Suppress("unused")

package io.nozemi.aoc.library.types.matrix.interfaces

import io.nozemi.aoc.library.types.Direction2D
import io.nozemi.aoc.library.types.matrix.MatrixCell
import io.nozemi.aoc.library.types.vector.Vector2

interface IMatrix<T> : Iterable<MatrixCell<T>> {
    val rows: Int
    val cols: Int
    val size: Int get() = rows * cols

    val cells: List<MatrixCell<T>>
    val distinctValues: List<T>

    fun getOrNull(pos: Vector2): T?
    fun findAll(predicate: (MatrixCell<T>) -> Boolean): List<MatrixCell<T>>

    operator fun set(pos: Vector2, value: T): IMatrix<T>

    fun reversed(): IMatrix<T>
    fun transposed(): IMatrix<T>
    fun copyOf(): IMatrix<T>

    fun index(pos: Vector2) = pos.y * cols + pos.x
    fun index(x: Int, y: Int) = index(Vector2(x, y))

    fun getOrNull(x: Int, y: Int): T? = getOrNull(Vector2(x, y))
    operator fun get(x: Int, y: Int): T = get(Vector2(x, y))
    operator fun get(pos: Vector2): T = getOrNull(pos)
        ?: throw NoSuchElementException("No such element at: $pos")

    operator fun set(x: Int, y: Int, value: T) = set(Vector2(x, y), value)

    fun findAll(value: T) = findAll { it.value == value }
    fun first(predicate: (MatrixCell<T>) -> Boolean) = findAll(predicate).first()
    fun first(value: T) = first { it.value == value }
    fun single(predicate: (MatrixCell<T>) -> Boolean) = findAll(predicate).single()
    fun single(value: T) = single { it.value == value }

    fun isWithinBounds(x: Int, y: Int) = isWithinBounds(Vector2(x, y))
    fun isWithinBounds(pos: Vector2): Boolean =
        pos.x in 0..<cols && pos.y in 0..<rows

    fun adjacent(
        from: Vector2,
        includeDiagonals: Boolean = false,
        allowOutOfBounds: Boolean = false,
        filter: (Pair<MatrixCell<T>, Direction2D>) -> Boolean = { true }
    ): List<Pair<MatrixCell<T>, Direction2D>> {
        val directions = Direction2D.orthogonal + if (includeDiagonals)
            Direction2D.diagonal
        else emptyList()

        return directions.map { Pair(from + it, it) }
            .filter { (adjPos) -> allowOutOfBounds || isWithinBounds(adjPos) }
            .map { (adjPos, direction) -> Pair(MatrixCell(adjPos, this[adjPos]), direction) }
            .filter { filter(it) }
    }
}