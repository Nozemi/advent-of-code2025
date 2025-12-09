package io.nozemi.aoc.library.puzzle.parsers

import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.types.vector.Vector3
import java.util.stream.Stream

class Vector3Parser : AbstractPuzzleParser<List<Vector3>>() {

    override fun parse(input: Stream<String>): List<Vector3> =
        input.map { raw ->
            val (x, y, z) = raw.split(",").map { it.toLong() }

            Vector3(x, y, z)
        }.toList()
}