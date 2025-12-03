package io.nozemi.aoc.library.puzzle.parsers

import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import java.util.stream.Stream

class HorizontalIntArrayParser : AbstractPuzzleParser<IntArray>() {
    override fun parse(input: Stream<String>) =
        input.toList().joinToString("")
            .map { it.digitToInt() }.toIntArray()
}

class VerticalIntArrayParser : AbstractPuzzleParser<IntArray>() {
    override fun parse(input: Stream<String>) =
        input.map { it.toInt() }.toList().toIntArray()
}