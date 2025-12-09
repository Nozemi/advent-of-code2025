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
        val disjointSet = ForestDisjointSet(points)

        points.edges.take(points.connections)
            .merge(disjointSet)

        return disjointSet.map { it.size }
            .sortedDescending()
            .take(3)
            .fold(1, Int::times)
    }

    private fun part2(points: List<Vector3>): Long {
        val disjointSet = ForestDisjointSet(points)
        val (a, b) = points.edges.merge(disjointSet).last()

        return a.x * b.x
    }

    fun List<Edge>.merge(disjointSet: ForestDisjointSet<Vector3>): List<Pair<Vector3, Vector3>> {
        val connectedEdges = mutableSetOf<Pair<Vector3, Vector3>>()

        this.forEach { edge ->
            val a = disjointSet[edge.a]
            val b = disjointSet[edge.b]

            if (a == null || b == null)
                return@forEach

            disjointSet.union(a, b)
            connectedEdges.add(Pair(edge.a, edge.b))

            if (disjointSet.partitions == 1)
                return connectedEdges.toList()
        }

        return connectedEdges.toList()
    }

    private val List<Vector3>.edges get() = buildList {
        for ((idx, a) in this@edges.withIndex()) {
            for (b in this@edges.subList(0, idx)) {
                add(Edge(a, b, a.euclideanDistanceTo(b)))
            }
        }

        sortBy(Edge::distance)
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