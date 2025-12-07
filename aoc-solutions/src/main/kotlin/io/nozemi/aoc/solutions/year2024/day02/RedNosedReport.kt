package io.nozemi.aoc.solutions.year2024.day02

import kotlin.math.max
import kotlin.math.min

class RedNosedReport(val levels: List<Int>) {

    private fun direction(levels: List<Int>): ReportDirection {
        if (levels[0] < levels[1]) {
            return ReportDirection.INCREASING
        }

        return ReportDirection.DECREASING
    }

    private fun validate(levels: List<Int>, maxLevelDifference: Int = 3): Boolean {
        levels.forEachIndexed { index, level ->
            val next = levels.getOrNull(index + 1)
                ?: return@forEachIndexed

            var isValid = true

            if (direction(levels) == ReportDirection.INCREASING && next <= level
                || direction(levels) == ReportDirection.DECREASING && next >= level
            ) isValid = false

            val left = levels.getOrNull(index - 1)
            val right = levels.getOrNull(index + 1)

            if (left != null && max(left, level) - min(left, level) > maxLevelDifference)
                isValid = false

            if (right != null && max(right, level) - min(right, level) > maxLevelDifference)
                isValid = false

            if (!isValid)
                return false
        }

        return true
    }

    fun isValid(maxLevelDifference: Int = 3, removeInvalid: Boolean = false): Boolean {
        if (validate(levels, maxLevelDifference))
            return true

        if (removeInvalid) {
            for (index in levels.indices) {
                val alternative = levels.subList(0, index) + levels.subList(index + 1, levels.size)

                if (validate(alternative, maxLevelDifference))
                    return true
            }
        }

        return false
    }

    private enum class ReportDirection {
        INCREASING,
        DECREASING
    }
}