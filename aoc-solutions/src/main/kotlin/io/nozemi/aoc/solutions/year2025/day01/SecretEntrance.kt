package io.nozemi.aoc.solutions.year2025.day01

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class SecretEntrance(
    override val parser: AbstractPuzzleParser<List<DialTurn>> = SecretEntranceParser()
) : AbstractPuzzle<List<DialTurn>>() {

    private class SecretEntranceParser : AbstractPuzzleParser<List<DialTurn>>() {

        override fun parse(input: Stream<String>): List<DialTurn> = input.map {
            DialTurn(it[0], it.substring(1).toInt())
        }.toList()
    }

    override val solutions: PuzzleSolutions<List<DialTurn>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(input: List<DialTurn>): Long {
        var current = 50
        var stopsAt0 = 0

        input.forEach {
            when (it.direction) {
                'R' -> current += it.clicks
                'L' -> current -= it.clicks
            }

            current %= 100

            if (current == 0)
                stopsAt0++
        }

        return stopsAt0.toLong()
    }

    private fun part2(input: List<DialTurn>): Long {
        var current = 50
        var stopsAt0 = 0

        input.forEach { r ->
            repeat(r.clicks) {
                current %= 100

                if (current == 0)
                    stopsAt0++

                when (r.direction) {
                    'R' -> current++
                    'L' -> current--
                }
            }
        }

        return stopsAt0.toLong()
    }
}

data class DialTurn(
    val direction: Char,
    val clicks: Int
)

fun main() {
    SecretEntrance().solve()
}