package io.nozemi.aoc.solutions.year2025.day01

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream
import kotlin.math.abs

class SecretEntrance(
    override val parser: AbstractPuzzleParser<List<DialTurn>> = SecretEntranceParser()
) : AbstractPuzzle<List<DialTurn>>() {

    private class SecretEntranceParser : AbstractPuzzleParser<List<DialTurn>>() {

        override fun parse(input: Stream<String>): List<DialTurn> = input.map {
            DialTurn(it[0], it.substring(1).toLong())
        }.toList()
    }

    override val solutions: PuzzleSolutions<List<DialTurn>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(input: List<DialTurn>): Long {
        var current = 50L
        var stopsAt0 = 0L

        input.forEach {
            current += it.distance
            current %= 100

            if (current == 0L)
                stopsAt0++
        }

        return stopsAt0
    }

    private fun part2(input: List<DialTurn>): Long {
        var current = 50L
        var stopsAt0 = input.sumOf { it.rotations }

        input.forEach {
            val original = current

            current += it.distance

            if (original != 0L && current !in 0..100)
                stopsAt0++

            if (current < 0)
                current += 100

            current %= 100

            if (current == 0L)
                stopsAt0++
        }

        return stopsAt0
    }
}

data class DialTurn(
    val direction: Char,
    val clicks: Long
) {
    val distance get() = with(clicks % 100) {
        when (direction) {
            'L' -> -this
            else -> this
        }
    }
    val rotations get(): Long = with(clicks - abs(distance)) {
        this / 100
    }
}

fun main() {
    SecretEntrance().solve()
}