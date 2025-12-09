package io.nozemi.aoc.library.helpers

fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque<T>().also {
    it.addAll(elements)
}