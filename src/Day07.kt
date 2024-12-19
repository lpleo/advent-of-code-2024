import kotlin.collections.ArrayList

fun main() {

    class Operation(val result: Long, val numbers: List<Long>, val permutations: MutableSet<String>) {
        fun calculateResult(): Long {
            val correctOperation = permutations.map { permutation -> calculate(permutation) }.any { it == result }
            if (correctOperation) return result
            return 0
        }

        fun calculate(permutation: String): Long {
            var accumulator = numbers[0]
            permutation.forEachIndexed { index, operand ->
                run {
                    if (operand == '*') {
                        accumulator *= numbers[index + 1]
                    }
                    if (operand == '+') {
                        accumulator += numbers[index + 1]
                    }
                    if (operand == '|') {
                        accumulator = (accumulator.toString() + numbers[index + 1].toString()).toLong()
                    }
                }
            }
            return accumulator
        }
    }

    fun calculatePermutation(size: Int, symbols: CharArray): MutableSet<String> {
        val realSize = size - 1;
        val result: MutableList<String> = ArrayList()
        symbols.forEach { symbol -> result.add(symbol + "") }
        for (i in 0 until realSize) {
            for (i1 in 0 until result.size) {
                symbols.forEach { _ -> result.add(result[i1]) }
            }
            var counter = 0;
            for (i1 in 0 until result.size) {
                result[i1] = result[i1] + symbols[counter]
                counter++
                if (counter == symbols.size) counter = 0
            }
        }
        return result.toHashSet();
    }

    fun part1(input: List<String>): Long {
        return input.map { row -> row.split(":") }
                .map { splitted ->
                    run {
                        val operands = splitted[1].trim().split(" ").map { it.toLong() }
                        val permutations = calculatePermutation(operands.size - 1, charArrayOf('*', '+'))
                        return@run Operation(splitted[0].toLong(), operands, permutations)
                    }
                }
                .map { operation -> operation.calculateResult() }
                .reduce { acc, result -> acc + result }
    }

    fun part2(input: List<String>): Long {
        return input.map { row -> row.split(":") }
                .map { splitted ->
                    run {
                        val operands = splitted[1].trim().split(" ").map { it.toLong() }
                        val permutations = calculatePermutation(operands.size - 1, charArrayOf('*', '+', '|'))
                        return@run Operation(splitted[0].toLong(), operands, permutations)
                    }
                }
                .map { operation -> operation.calculateResult() }
                .reduce { acc, result -> acc + result }
    }

    val testInput = readInput("Day07_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 3749L)
    val input = readInput("Day07")
    part1(input).println()

    val part2 = part2(testInput)
    println(part2)
    check(part2 == 11387L)
    part2(input).println()
}
