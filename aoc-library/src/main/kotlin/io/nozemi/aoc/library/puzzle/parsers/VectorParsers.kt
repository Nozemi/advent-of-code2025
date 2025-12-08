package io.nozemi.aoc.library.puzzle.parsers

import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.types.vector.IVector3
import io.nozemi.aoc.library.types.vector.Vector3Int
import java.util.stream.Stream

class Vector3IntParser : AbstractPuzzleParser<List<IVector3<Int>>>() {

    override fun parse(input: Stream<String>): List<IVector3<Int>> =
        input.map { raw ->
            val (x, y, z) = raw.split(",").map { it.toInt() }

            Vector3Int(x, y, z)
        }.toList()
}