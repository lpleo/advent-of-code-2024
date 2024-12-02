import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val l1 = ArrayList<Int>()
        val l2 = ArrayList<Int>()
        input.forEach { l1.add(it.split("   ")[0].toInt()); l2.add(it.split("   ")[1].toInt()) }
        l1.sort()
        l2.sort()
        return List(l1.size) { index -> abs(l1[index]-l2[index]) }.sum()
    }

    fun part2(input: List<String>): Int {
        val l1 = ArrayList<Int>()
        val l2 = ArrayList<Int>()
        input.forEach { l1.add(it.split("   ")[0].toInt()); l2.add(it.split("   ")[1].toInt()) }
        return l1.sumOf { i -> i * l2.count { i == it } }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
