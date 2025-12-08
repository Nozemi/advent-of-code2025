package io.nozemi.aoc.library.puzzle.parsers

import io.nozemi.aoc.library.puzzle.AbstractPuzzleParser
import io.nozemi.aoc.library.types.matrix.MatrixDynamicChar
import io.nozemi.aoc.library.types.matrix.MatrixDynamicInt
import java.util.stream.Stream


class CharMatrixParser : AbstractPuzzleParser<MatrixDynamicChar>() {

    override fun parse(input: Stream<String>) = MatrixDynamicChar.from(
        input.toList().filterNot { it.isBlank() }
    )
}

class IntMatrixParser : AbstractPuzzleParser<MatrixDynamicInt>() {

    override fun parse(input: Stream<String>) = MatrixDynamicInt.from(
        input.toList().filterNot { it.isBlank() }
    )
}