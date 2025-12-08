@file:Suppress("unused")

package io.nozemi.aoc.library.types.matrix.interfaces

import io.nozemi.aoc.library.types.Direction2D
import io.nozemi.aoc.library.types.matrix.MatrixCell
import io.nozemi.aoc.library.types.vector.IVector2
import io.nozemi.aoc.library.types.vector.Vector2Int

interface IMatrix<T> : Iterable<MatrixCell<T>> {
    val rows: Int
    val cols: Int
    val size: Int get() = rows * cols

    val cells: List<MatrixCell<T>>
    val distinctValues: List<T>

    fun getOrNull(pos: IVector2<Int>): T?
    fun findAll(predicate: (MatrixCell<T>) -> Boolean): List<MatrixCell<T>>

    operator fun set(pos: IVector2<Int>, value: T): IMatrix<T>

    fun reversed(): IMatrix<T>
    fun transposed(): IMatrix<T>
    fun copyOf(): IMatrix<T>

    fun index(pos: IVector2<Int>) = pos.y * cols + pos.x
    fun index(x: Int, y: Int) = index(Vector2Int(x, y))

    fun getOrNull(x: Int, y: Int): T? = getOrNull(Vector2Int(x, y))
    operator fun get(x: Int, y: Int): T = get(Vector2Int(x, y))
    operator fun get(pos: IVector2<Int>): T = getOrNull(pos)
        ?: throw NoSuchElementException("No such element at: $pos")

    operator fun set(x: Int, y: Int, value: T) = set(Vector2Int(x, y), value)

    fun findAll(value: T) = findAll { it.value == value }
    fun first(predicate: (MatrixCell<T>) -> Boolean) = findAll(predicate).first()
    fun first(value: T) = first { it.value == value }
    fun single(predicate: (MatrixCell<T>) -> Boolean) = findAll(predicate).single()
    fun single(value: T) = single { it.value == value }

    fun isWithinBounds(x: Int, y: Int) = isWithinBounds(Vector2Int(x, y))
    fun isWithinBounds(pos: IVector2<Int>): Boolean =
        pos.x in 0..<cols && pos.y in 0..<rows

    fun adjacent(
        from: IVector2<Int>,
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