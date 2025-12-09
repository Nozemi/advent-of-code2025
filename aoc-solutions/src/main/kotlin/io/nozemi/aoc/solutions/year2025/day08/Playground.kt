package io.nozemi.aoc.solutions.year2025.day08

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import io.nozemi.aoc.library.puzzle.parsers.Vector3Parser
import io.nozemi.aoc.library.types.disjointset.ForestDisjointSet
import io.nozemi.aoc.library.types.vector.Vector3

/**
 * So after spending a significant amount of time; probably like 6+ hours trying to figure out a solution to the problem,
 * I ended up looking at [Graham's solution](https://github.com/grahamedgecombe/advent-2025/blob/main/src/main/kotlin/com/grahamedgecombe/advent2025/day8/Day8.kt).
 *
 * I do remember seeing the Forest Disjoint-Set structure in a previous year's challenge as well,
 * so perhaps at some point I will be able to implement and fully understand what it does in the future as well.
 *
 * For now, I've concluded that part 1 defeated me, so I will attempt part 2 without looking at the solution for that.
 * Perhaps I can manage to solve part 2 with what I've learned/got from part 1.
 *
 * ========================================================================
 *
 * So I did adjust the code for the 2nd part myself, although it was really simple to do it.
 */
class Playground(
    override val parser: AbstractPuzzleParser<List<Vector3>> = Vector3Parser()
) : AbstractPuzzle<List<Vector3>>() {

    override val solutions: PuzzleSolutions<List<Vector3>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(points: List<Vector3>): Int {
        val disjointSet = ForestDisjointSet<Vector3>()
        for (v in points) {
            disjointSet.add(v)
        }

        val edges = buildList {
            for ((idx, a) in points.withIndex()) {
                for (b in points.subList(0, idx)) {
                    add(Edge(a, b, a.euclideanDistanceTo(b)))
                }
            }

            sortBy(Edge::distance)
        }.take(points.connections)

        for (connection in edges) {
            val a = disjointSet[connection.a]
            val b = disjointSet[connection.b]

            if (a == null || b == null)
                continue

            disjointSet.union(a, b)
        }

        return disjointSet.map { it.size }
            .sortedDescending()
            .take(3)
            .fold(1, Int::times)
    }

    private fun part2(points: List<Vector3>): Long {
        val disjointSet = ForestDisjointSet<Vector3>()
        disjointSet.addAll(points)

        val edges = buildList {
            for ((idx, a) in points.withIndex()) {
                for (b in points.subList(0, idx)) {
                    add(Edge(a, b, a.euclideanDistanceTo(b)))
                }
            }

            sortBy(Edge::distance)
        }

        var lastConnection = Pair(Vector3(0, 0, 0), Vector3(0, 0, 0))
        for (connection in edges) {
            val a = disjointSet[connection.a]!!
            val b = disjointSet[connection.b]!!

            disjointSet.union(a, b)

            lastConnection = Pair(connection.a, connection.b)

            if (disjointSet.partitions == 1)
                break
        }

        val (a, b) = lastConnection
        return a.x * b.x
    }

    private val List<Vector3>.connections
        get() = when (this.size) {
            20 -> 10
            else -> 1000
        }

    data class Edge(val a: Vector3, val b: Vector3, val distance: Long)
}

fun main() {
    Playground().solve(exampleOnly = false)
}