import kotlin.math.abs

fun main() {
    fun isInRange(line: List<Int>) =
            line.mapIndexed { index, number -> line.size == index + 1 || (abs(number - line[index + 1]) in 1..3) }
                    .reduce { acc, b -> acc && b }

    fun isValid(line: List<Int>): Boolean {
        return (line.sorted() == line || line.sortedDescending() == line) && isInRange(line)
    }

    fun part1(input: List<String>): Int {
        return input.map { line -> line.split(" ").map { it.toInt() } }
                .map { line -> isValid(line) }
                .count { it }
    }

    fun part2(input: List<String>): Int {
        val lists: List<List<Int>> = input.map { line -> line.split(" ").map { it.toInt() } }
        return lists.map { list ->
            if (isValid(list)) {
                return@map true
            }
            for ((index, _) in list.withIndex()) {
                if (isValid(list.filterIndexed { indexInternal, _ -> indexInternal != index })) {
                    return@map true
                }
            }
            return@map false
        }.count { it }
    }

// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)


// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
