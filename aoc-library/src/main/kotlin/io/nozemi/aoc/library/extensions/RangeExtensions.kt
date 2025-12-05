package io.nozemi.aoc.library.extensions

val List<LongRange>.merged: List<LongRange>
    get() {
        if (isEmpty())
            return emptyList()

        val sorted = this.sortedBy { it.first }

        val result = mutableListOf<LongRange>()

        var current = sorted.first()
        for (range in sorted.drop(1)) {
            if (range.first > current.last + 1) {
                result += current
                current = range
            } else current = current.first..maxOf(current.last, range.last)
        }

        result += current

        return result
    }

