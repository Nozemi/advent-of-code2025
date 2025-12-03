package io.nozemi.aoc.solutions.year2025.day01

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream
import kotlin.math.floor
import kotlin.math.max

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
            when (it.direction) {
                'R' -> current += it.clicks
                'L' -> current -= it.clicks
            }

            current %= 100

            if (current == 0L)
                stopsAt0++
        }

        return stopsAt0
    }

    private fun part2(input: List<DialTurn>): Long {
        var current = 50L
        var stopsAt0 = 0L

        input.forEach { r ->
            var clicks = r.clicks
            while (clicks - 100 > 100) {
                clicks -= 100
                stopsAt0++
            }

            while (clicks-- > 0) {
                current %= 100

                if (current == 0L)
                    stopsAt0++

                when (r.direction) {
                    'R' -> current++
                    'L' -> current--
                }
            }
        }

        return stopsAt0
    }
}

data class DialTurn(
    val direction: Char,
    val clicks: Long
)

fun main() {
    SecretEntrance().solve(
        listOf(
            "suic",
            //"nozemi"
        )
    )

    //println(30_000_000 / 299_999)
}

/*
Revs: 299999 | 30000000
Revs: 479999 | 48000000
Revs: 49999 | 5000000
Revs: 599999 | 60000000
Revs: 549999 | 55000000
Revs: 9999 | 1000000
Revs: 989999 | 99000000
Revs: 139999 | 14000000
Revs: 819999 | 82000000
 */