package io.nozemi.aoc.library.puzzle

import java.util.stream.Stream

abstract class AbstractPuzzleParser<T> {
    abstract fun parse(input: Stream<String>): T
}