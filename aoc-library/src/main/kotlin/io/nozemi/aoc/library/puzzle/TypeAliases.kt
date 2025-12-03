package io.nozemi.aoc.library.puzzle

import kotlin.reflect.KFunction1

typealias PuzzleSolutions<T> = List<KFunction1<T, Long>>