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

    val sumOfGroupsLeftToRight: List<Long>
        get() = numberGroups.toList().reversed()
            .toMap()
            .map { (column, group) ->
                //println(group)

                val operator = operators[column]
                val padding = when (operator) {
                    Operator.PLUS, Operator.MINUS -> '0'
                    Operator.MULTIPLY, Operator.DIVIDE -> '0'
                    else -> error("Unexpected operator '$operator'")
                }
                val groupAsStrings = group.map { it.toString() }
                val digits = groupAsStrings.maxBy { a -> a.length }.length

                val newGroups = groupAsStrings.map {
                    it.padEnd(
                        digits,
                        padChar = padding
                    )
                }.map { it.reversed() }

                val finalNumbers = mutableListOf<Long>()
                for (i in 0..<digits) {
                    finalNumbers.add(newGroups.map { it[i] }.joinToString("").toLong())
                }

                val sum = when (operator) {
                    Operator.PLUS -> finalNumbers.sum()
                    Operator.MINUS -> finalNumbers.reduce { a, b -> a - b }
                    Operator.MULTIPLY -> finalNumbers.reduce { a, b -> a * b }
                    Operator.DIVIDE -> finalNumbers.reduce { a, b -> a / b }
                }

                println("$newGroups -> $finalNumbers -> $sum")

                sum
            }.toList()

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