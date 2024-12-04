fun main() {

    class Letter(val x: Int, val y: Int, val c: Char)


    fun find(startLetter: Letter, xIncrement: Int, yIncrement: Int, letters: List<Letter>): Int {
        val wordPresent =
                letters.any { innerLetter -> innerLetter.x == startLetter.x + xIncrement && innerLetter.y == startLetter.y + yIncrement && innerLetter.c == 'M' } &&
                        letters.any { innerLetter -> innerLetter.x == startLetter.x + (xIncrement * 2) && innerLetter.y == startLetter.y + (yIncrement * 2) && innerLetter.c == 'A' } &&
                        letters.any { innerLetter -> innerLetter.x == startLetter.x + (xIncrement * 3) && innerLetter.y == startLetter.y + (yIncrement * 3) && innerLetter.c == 'S' }
        if (wordPresent) return 1
        return 0
    }

    fun findAll(startLetter: Letter, letters: List<Letter>): Int {
        return find(startLetter, 1, 0, letters) +
                find(startLetter, -1, 0, letters) +
                find(startLetter, 0, 1, letters) +
                find(startLetter, 0, -1, letters) +
                find(startLetter, 1, 1, letters) +
                find(startLetter, -1, -1, letters) +
                find(startLetter, 1, -1, letters) +
                find(startLetter, -1, 1, letters)
    }

    fun findAll2(startLetter: Letter, letters: List<Letter>): Int {
        val m1 = letters.any { innerLetter -> innerLetter.x == startLetter.x - 1 && innerLetter.y == startLetter.y - 1 && innerLetter.c == 'M' }
        val s1 = letters.any { innerLetter -> innerLetter.x == startLetter.x + 1 && innerLetter.y == startLetter.y + 1 && innerLetter.c == 'S' }
        val m2 = letters.any { innerLetter -> innerLetter.x == startLetter.x + 1 && innerLetter.y == startLetter.y + 1 && innerLetter.c == 'M' }
        val s2 = letters.any { innerLetter -> innerLetter.x == startLetter.x - 1 && innerLetter.y == startLetter.y - 1 && innerLetter.c == 'S' }
        val m3 = letters.any { innerLetter -> innerLetter.x == startLetter.x + 1 && innerLetter.y == startLetter.y - 1 && innerLetter.c == 'M' }
        val s3 = letters.any { innerLetter -> innerLetter.x == startLetter.x - 1 && innerLetter.y == startLetter.y + 1 && innerLetter.c == 'S' }
        val s4 = letters.any { innerLetter -> innerLetter.x == startLetter.x + 1 && innerLetter.y == startLetter.y - 1 && innerLetter.c == 'S' }
        val m4 = letters.any { innerLetter -> innerLetter.x == startLetter.x - 1 && innerLetter.y == startLetter.y + 1 && innerLetter.c == 'M' }

        val mas1 = (m1 && s1) || (s2 && m2)
        val mas2 = (m3 && s3) || (s4 && m4)

        if (mas1 && mas2) return 1
        return 0
    }

    fun part1(input: List<String>): Int {
        val letters = ArrayList<Letter>()
        input.forEachIndexed { index, s -> s.forEachIndexed { charIndex, c -> letters.add(Letter(charIndex, index, c)) } }
        return letters
                .filter { letter -> letter.c == 'X' }
                .sumOf { letter -> findAll(letter, letters) }
    }

    fun part2(input: List<String>): Int {
        val letters = ArrayList<Letter>()
        input.forEachIndexed { index, s -> s.forEachIndexed { charIndex, c -> letters.add(Letter(charIndex, index, c)) } }
        return letters
                .filter { letter -> letter.c == 'A' }
                .sumOf { letter -> findAll2(letter, letters) }
    }

    val testInput = readInput("Day04_test")
    val resultp1 = part1(testInput)
    println(resultp1)
    check(resultp1 == 18)
    val resultp2 = part2(testInput)
    println(resultp2)
    check(resultp2 == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
