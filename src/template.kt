fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("%DAY%_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 1)
    val input = readInput("%DAY%")
    part1(input).println()

    val part2 = part2(testInput)
    println(part2)
    check(part2 == 1)
    part2(input).println()
}
