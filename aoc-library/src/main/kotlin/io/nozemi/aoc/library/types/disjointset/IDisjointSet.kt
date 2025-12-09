@file:Suppress("unused")

package io.nozemi.aoc.library.types.disjointset

/**
 * This is more or less a straight copy of [Graham's implementation](https://github.com/grahamedgecombe/advent-2025/blob/main/src/main/kotlin/com/grahamedgecombe/advent2025/util/DisjointSet.kt)
 */
interface IDisjointSet<T> : Iterable<IDisjointSet.Partition<T>> {
    interface Partition<T> : Iterable<T> {
        val size: Int
    }

    val elements: Int
    val partitions: Int

    fun add(x: T): Partition<T>
    fun addAll(x: List<T>) = x.forEach { add(it) }
    fun addAll(vararg x: T) = x.forEach { add(it) }

    operator fun get(x: T): Partition<T>?
    fun union(x: Partition<T>, y: Partition<T>)
}