package io.nozemi.aoc2025

import java.nio.file.Files
import java.util.stream.Stream
import kotlin.io.path.Path
import kotlin.math.abs

data class DialTurn(
    val direction: Char,
    val clicks: Int
)

fun parseInput(day: Int, example: Boolean = true): List<DialTurn> {
    var file = "day${day}"
    if (example) {
        file += ".example"
    }
    file += ".txt"

    val input: Stream<String> = Files.lines(Path("./data").resolve(file))

    val turns = mutableListOf<DialTurn>()

    input.forEach {
        val direction = it[0]
        val clicks = it.substring(1).toInt()

        turns.add(DialTurn(direction, clicks))
    }

    return turns
}

fun part1(input: List<DialTurn>): Int {
    var current = 50
    var stopsAt0 = 0

    input.forEach {
        if (it.direction == 'R')
            current += it.clicks
        else if (it.direction == 'L')
            current -= it.clicks

        while (current < 0) {
            current = 100 - abs(current)
        }

        while (current > 99) {
            current -= 100
        }

        if (current == 0)
            stopsAt0++
    }

    return stopsAt0
}

fun part2(input: List<DialTurn>): Int {
    var current = 50
    var stopsAt0 = 0

    input.forEach { r ->
        repeat(r.clicks) {
            if (current > 99)
                current -= 100
            if (current < 0)
                current = 100 - abs(current)

            if (current == 0)
                stopsAt0++

            if (r.direction == 'L')
                current--
            if (r.direction == 'R')
                current++
        }
    }

    return stopsAt0
}

fun main() {
    val inputExample = parseInput(1, true)
    println("Solving Day 1 Example:")
    println("Part 1: ${part1(inputExample)}")
    println("Part 2: ${part2(inputExample)}")
    println("=======================")
    println("")

    val inputActual = parseInput(1, false)
    println("Solving Day 1:")
    println("Part 1: ${part1(inputActual)}")
    println("Part 2: ${part2(inputActual)}")
    println("=======================")
    println("")
}