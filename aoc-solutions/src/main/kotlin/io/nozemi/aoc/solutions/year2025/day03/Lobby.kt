package io.nozemi.aoc.solutions.year2025.day03

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class Lobby(
    override val parser: AbstractPuzzleParser<List<List<Int>>> = LobbyParser()
) : AbstractPuzzle<List<List<Int>>>() {

    private class LobbyParser : AbstractPuzzleParser<List<List<Int>>>() {
        override fun parse(input: Stream<String>): List<List<Int>> =
            input.map { batteryBank ->
                batteryBank.map { battery -> battery.digitToInt() }.toList()
            }.toList()
    }

    override val solutions: PuzzleSolutions<List<List<Int>>> = listOf(
        ::part1,
        ::part2
    )

    private fun List<Int>.highestJoltage(batteries: Int): List<Int> {
        val joltage = mutableListOf<Int>()
        var removeCount = this.size - batteries

        this.forEach {
            while (joltage.isNotEmpty() && joltage.last() < it && removeCount > 0) {
                joltage.removeAt(joltage.size - 1)
                removeCount--
            }
            joltage.add(it)
        }

        return joltage.take(batteries)
    }

    private fun part1(batteryBanks: List<List<Int>>): Long =
        batteryBanks.map { it.highestJoltage(2) }.sumOf { it.joinToString("").toLong() }

    private fun part2(batteryBanks: List<List<Int>>): Long =
        batteryBanks.map { it.highestJoltage(12) }.sumOf { it.joinToString("").toLong() }
}

fun main() {
    Lobby().solve()
}