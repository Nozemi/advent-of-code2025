package io.nozemi.aoc.solutions.year2025.day03

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

typealias BatteryBank = List<Int>

class Lobby(
    override val parser: AbstractPuzzleParser<List<BatteryBank>> = LobbyParser()
) : AbstractPuzzle<List<BatteryBank>>() {

    private class LobbyParser : AbstractPuzzleParser<List<BatteryBank>>() {
        override fun parse(input: Stream<String>): List<BatteryBank> =
            input.map { batteryBank ->
                batteryBank.map { battery -> battery.digitToInt() }.toList()
            }.toList()
    }

    override val solutions: PuzzleSolutions<List<BatteryBank>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(batteryBanks: List<BatteryBank>): Long =
        batteryBanks.totalJoltage(2)

    private fun part2(batteryBanks: List<BatteryBank>): Long =
        batteryBanks.totalJoltage(12)

    private fun BatteryBank.highestJoltage(batteries: Int): Long {
        val joltage = mutableListOf<Int>()
        var removeCount = this.size - batteries

        this.forEach {
            while (joltage.isNotEmpty() && joltage.last() < it && removeCount > 0) {
                joltage.removeAt(joltage.size - 1)
                removeCount--
            }
            joltage.add(it)
        }

        return joltage.take(batteries).joinToString("").toLong()
    }

    private fun List<BatteryBank>.totalJoltage(batteries: Int): Long =
        this.sumOf { it.highestJoltage(batteries) }
}

fun main() {
    Lobby().solve()
}