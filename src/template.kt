fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day0_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 1)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 1)

    val input = readInput("Day0")
    part1(input).println()
    part2(input).println()
}
