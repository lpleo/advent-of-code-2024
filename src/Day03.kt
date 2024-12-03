import jdk.jfr.Enabled
import kotlin.Int.Companion.MAX_VALUE

fun main() {

    class LexicalElementResult(val result: Int, val stop: Boolean)
    abstract class LexicalElement {
        lateinit var matchResult: MatchResult;
        fun shouldHandle(description: String): Int? {
            val find = getRegex().find(description)
            if (find != null && find.groups.isNotEmpty()) {
                matchResult = find;
                return find.groups[0]?.range?.last
            }
            return null
        }

        abstract fun handle(stop: Boolean): LexicalElementResult
        abstract fun getRegex(): Regex
    }

    class MulElement : LexicalElement() {
        override fun handle(stop: Boolean): LexicalElementResult {
            var result = 0;
            if (!stop) {
                val replace = matchResult.groups[0]?.value?.replace("mul(", "")?.replace(")", "")
                if (replace != null) {
                    result = replace.split(",")[0].toInt() * replace.split(",")[1].toInt()
                }
            }
            return LexicalElementResult(result, stop)
        }

        override fun getRegex(): Regex {
            return Regex("mul\\([0-9]+,[0-9]+\\)")
        }
    }

    class DoElement : LexicalElement() {
        override fun handle(stop: Boolean): LexicalElementResult {
            return LexicalElementResult(0, false)
        }

        override fun getRegex(): Regex {
            return Regex("do\\(\\)")
        }
    }

    class DontElement : LexicalElement() {
        override fun handle(stop: Boolean): LexicalElementResult {
            return LexicalElementResult(0, true)
        }

        override fun getRegex(): Regex {
            return Regex("don't\\(\\)")
        }
    }

    fun part1(input: List<String>): Int {
        val matches = Regex("mul\\([0-9]+,[0-9]+\\)").findAll(input.joinToString(""))
        return matches
                .map { it.groupValues[0] }
                .map { it.replace("mul(", "").replace(")", "") }
                .map { it.split(",")[0].toInt() * it.split(",")[1].toInt() }
                .sum()
    }

    fun part2(input: List<String>): Int {
        var string = input.joinToString("")
        var stop = false
        var accumulator = 0

        while (true) {
            val matcher =
                    arrayOf(MulElement(), DoElement(), DontElement())
                            .filter { matcher -> matcher.shouldHandle(string) != null }
                            .minByOrNull { matcher -> matcher.shouldHandle(string) ?: MAX_VALUE }

            if (matcher?.matchResult == null) return accumulator

            val result = matcher.handle(stop)
            accumulator += result.result
            stop = result.stop

            string = string.substring(matcher.matchResult.groups[0]?.range?.last ?: 0)
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
