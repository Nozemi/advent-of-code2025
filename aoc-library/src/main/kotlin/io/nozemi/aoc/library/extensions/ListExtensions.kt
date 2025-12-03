package io.nozemi.aoc.library.extensions

fun List<Int>.largest(count: Int): List<Int> =
    this.sortedDescending().subList(0, count)
        .sortedBy { this.indexOf(it) }