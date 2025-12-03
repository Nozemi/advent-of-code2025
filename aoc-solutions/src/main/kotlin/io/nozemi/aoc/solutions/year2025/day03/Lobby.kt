package io.nozemi.aoc.solutions.year2025.day03

import io.nozemi.aoc.library.puzzle.AbstractPuzzle
import io.nozemi.aoc.library.puzzle.PuzzleSolutions
import java.util.stream.Stream

class Lobby : AbstractPuzzle<List<List<Int>>>() {

    override val parser: (input: Stream<String>) -> List<List<Int>> = { input ->
        input.map { batteryBank ->
            batteryBank.map { battery -> battery.digitToInt() }.toList()
        }.toList()
    }

    override val solutions: PuzzleSolutions<List<List<Int>>> = listOf(
        ::part1,
        ::part2
    )

    private fun List<Int>.highestJoltage(): Int {
        var highestJoltage = 0
        this.forEachIndexed { i1, b1 ->
            this.subList(i1 + 1, this.size).forEach { b2 ->
                val current = "$b1$b2".toInt()
                if (current > highestJoltage)
                    highestJoltage = current
            }
        }

        return highestJoltage
    }

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