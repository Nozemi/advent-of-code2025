package io.nozemi.aoc.solutions.year2025.day06

class MathGrid(
    private val operators: Map<Int, Operator>,
    private val numberGroups: Map<Int, List<Long>>
) {

    val sumOfGroups: List<Long>
        get() = numberGroups.map { (column, group) ->
            val operator = operators[column]

            when (operator) {
                Operator.PLUS -> group.sum()
                Operator.MINUS -> group.reduce { a, b -> a - b }
                Operator.MULTIPLY -> group.reduce { a, b -> a * b }
                Operator.DIVIDE -> group.reduce { a, b -> a / b }
                else -> 0L
            }
        }.toList()

    enum class Operator(val symbol: Char) {
        PLUS('+'),
        MINUS('-'),
        MULTIPLY('*'),
        DIVIDE('/')
    }

    companion object {

        fun from(lines: Iterable<String>): MathGrid {
            val operators = mutableMapOf<Int, Operator>()
            val numberGroups = mutableMapOf<Int, MutableList<Long>>()

            val rows = lines.toMutableList().map { it.trim().split("\\s+".toRegex()) }

            rows.last().map { it[0] }
                .forEachIndexed { index, operator ->
                    operators[index] = Operator.entries.single { it.symbol == operator }
                }

            rows.subList(0, rows.size - 1).forEach { row ->
                row.forEachIndexed { column, number ->
                    numberGroups.getOrPut(column) { mutableListOf() }
                        .add(number.toLong())
                }
            }

            return MathGrid(operators, numberGroups)
        }
    }
}