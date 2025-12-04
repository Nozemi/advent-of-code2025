package io.nozemi.aoc.library.puzzle.parsers

import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.types.matrix.VariableCharMatrix
import io.nozemi.aoc.library.types.matrix.VariableIntMatrix
import java.util.stream.Stream


class CharMatrixParser : AbstractPuzzleParser<VariableCharMatrix>() {

    override fun parse(input: Stream<String>) = VariableCharMatrix.from(
        input.toList().filterNot { it.isBlank() }
    )
}

class IntMatrixParser : AbstractPuzzleParser<VariableIntMatrix>() {

    override fun parse(input: Stream<String>) = VariableIntMatrix.from(
        input.toList().filterNot { it.isBlank() }
    )
}