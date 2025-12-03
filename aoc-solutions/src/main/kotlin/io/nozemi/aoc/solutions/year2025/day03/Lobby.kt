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
        ::part1
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

    private fun part1(batteryBanks: List<List<Int>>): Long {
        val highestJoltages = batteryBanks.map { it.highestJoltage() }

        return highestJoltages.sum().toLong()
    }
}

fun main() {
    Lobby().solve()
}