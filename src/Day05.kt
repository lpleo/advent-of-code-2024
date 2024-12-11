fun main() {

    class Rule(val from: Int, val to: Int)

    fun checkNumber(subList: List<Int>, rules: List<Rule>, number: Int): Boolean {
        val numberRules = rules.filter { it.from == number }
        if (subList.isEmpty()) return true
        return subList
                .map { element -> numberRules.any { it.to == element } }
                .reduce { subListValid, elementValid -> subListValid && elementValid }
    }

    fun isValidSequence(sequence: List<Int>, rules: List<Rule>) =
            sequence.mapIndexed { index, number -> checkNumber(sequence.subList(index + 1, sequence.size), rules, number) }
                    .reduce { sequenceValid, letterValid -> sequenceValid && letterValid }

    fun part1(input: List<String>): Int {
        val rules: List<Rule> = input.filter { it.contains("|") }.map { Rule(it.split("|")[0].toInt(), it.split("|")[1].toInt()) }
        val sequences: List<List<Int>> = input.filter { it.contains(",") }.map { it.split(",").map { number -> number.toInt() } }

        return sequences.filter { sequence -> isValidSequence(sequence, rules) }.sumOf { it[(it.size / 2)] }
    }

    fun part2(input: List<String>): Int {
        val rules: List<Rule> = input.filter { it.contains("|") }.map { Rule(it.split("|")[0].toInt(), it.split("|")[1].toInt()) }
        val sequences: List<MutableList<Int>> = input.filter { it.contains(",") }.map { it.split(",").map { number -> number.toInt() }.toMutableList() }

        val wrongSequences = sequences.filter { sequence -> !isValidSequence(sequence, rules) }.toMutableList()
        for (wrongSequence in wrongSequences) {
            val numberComparator = object : Comparator<Int> {
                override fun compare(n1: Int, n2: Int): Int {
                    if (rules.any { it.from == n1 && it.to == n2 }) return 1
                    else if (rules.any { it.to == n1 && it.from == n2 }) return -1
                    return 0
                }
            }
            wrongSequence.sortWith(numberComparator)
        }
        return wrongSequences.sumOf { it[(it.size / 2)] }
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    val part1 = part1(testInput)
    println("T1: $part1")
    check(part1 == 143)
    println("R1: " + part1(input))
    println()

    val part2 = part2(testInput)
    println("T2: $part2")
    check(part2 == 123)
    println("R2: " + part2(input))
}
