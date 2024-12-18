fun main() {

    fun <T> copyArray(d: ArrayList<T>): ArrayList<T> {
        val r = ArrayList<T>()
        for (item in d) {
            r.add(item)
        }
        return r
    }

    class Point(val x: Int, val y: Int, var c: Char, var visited: Int, var directions: ArrayList<Char>) : Cloneable {
        public override fun clone() = Point(x, y, c, visited, copyArray(directions))
        public fun same(other: Point): Boolean {
            return other.x == this.x && other.y == this.y
        }
    }

    class Hit(val point: Point, val direction: Char) {
        public fun same(other: Hit): Boolean {
            return other.point.same(this.point) && other.direction == this.direction
        }
    }

    fun isDirection(p: Point): Boolean {
        return p.c == '^' || p.c == 'v' || p.c == '>' || p.c == '<'
    }

    fun nextPoint(direction: Char, x: Int, y: Int, pointList: List<Point>): Point? {
        if (direction == '^') return pointList.find { innerPoint -> innerPoint.x == x && innerPoint.y == y - 1 }
        if (direction == 'v') return pointList.find { innerPoint -> innerPoint.x == x && innerPoint.y == y + 1 }
        if (direction == '>') return pointList.find { innerPoint -> innerPoint.x == x + 1 && innerPoint.y == y }
        return pointList.find { innerPoint -> innerPoint.x == x - 1 && innerPoint.y == y }
    }

    fun nextValue(direction: Char): Char {
        if (direction == '^') return '>'
        if (direction == 'v') return '<'
        if (direction == '>') return 'v'
        return '^'
    }

    fun navigate(points: List<Point>) {
        var actualPoint = points
                .find { p -> p.c == '^' || p.c == 'v' || p.c == '>' || p.c == '<' }
                .takeIf { it != null }
                ?: throw RuntimeException("No start point found")
        var counter = 0;
        do {
            actualPoint.visited += 1
            actualPoint.directions.add(actualPoint.c)
            val nextPoint = nextPoint(actualPoint.c, actualPoint.x, actualPoint.y, points)
            if (nextPoint != null) {
                if (nextPoint.c == '#') {
                    actualPoint.c = nextValue(actualPoint.c)
                    actualPoint.visited -= 1;
                }
                if (nextPoint.c == '.') {
                    nextPoint.c = actualPoint.c
                    actualPoint.c = '.'
                    actualPoint = nextPoint
                }
            }
            counter++;
        } while (nextPoint != null)
        println("total steps $counter")
    }

    fun canBeBlocked(startPoint: Point, points: List<Point>): Int {
        val pointsCopy = points.map { point -> point.clone() }.toList()

        var actualPoint = pointsCopy.find { it.x == startPoint.x && it.y == startPoint.y }
                ?: Point(-1, -1, '?', 0, ArrayList())

        var nextPoint = nextPoint(startPoint.c, actualPoint.x, actualPoint.y, pointsCopy)
        nextPoint?.c = '#'

        val hits = ArrayList<Hit>()
        val subHits = ArrayList<Hit>()

        while (true) {
            actualPoint.visited += 1
            actualPoint.directions.add(actualPoint.c)
            nextPoint = nextPoint(actualPoint.c, actualPoint.x, actualPoint.y, pointsCopy)
            if (nextPoint == null) return 0;
            if (nextPoint.c == '#') {
                val alreadyHit = hits.find { it.same(Hit(nextPoint, actualPoint.c)) }
                if (alreadyHit != null) {
                    subHits.add(alreadyHit)
                    if (subHits.size >= 4) {
                        subHits.forEachIndexed { subHitIndex, subHit ->
                            hits.forEachIndexed { hitIndex, hit ->
                                if (subHit.same(hit) && subHitIndex + 3 < subHits.size && hitIndex + 3 < hits.size) {
                                    if (subHits[subHitIndex + 1].same(hits[hitIndex + 1]) &&
                                            subHits[subHitIndex + 2].same(hits[hitIndex + 2]) &&
                                            subHits[subHitIndex + 3].same(hits[hitIndex + 3])) {
                                        return 1
                                    }
                                }
                            }
                        }
                    }
                }
                hits.add(Hit(nextPoint, actualPoint.c))
                actualPoint.c = nextValue(actualPoint.c)
                actualPoint.visited -= 1
            }
            if (nextPoint.c == '.') {
                nextPoint.c = actualPoint.c
                actualPoint.c = '.'
                actualPoint = nextPoint
            }

        }
    }

    fun part1(input: List<String>): Int {
        val points = input.flatMapIndexed { lineIndex, line -> line.mapIndexed { charIndex, c -> Point(charIndex, lineIndex, c, 0, ArrayList()) }.toList() }.toList()
        navigate(points)

        return points.count { it.visited > 0 }
    }

    fun part2(input: List<String>): Int {
        val points = input.flatMapIndexed { lineIndex, line -> line.mapIndexed { charIndex, c -> Point(charIndex, lineIndex, c, 0, ArrayList()) }.toList() }.toList()
        val emptyPoints = input.flatMapIndexed { lineIndex, line -> line.mapIndexed { charIndex, c -> Point(charIndex, lineIndex, c, 0, ArrayList()) }.toList() }.toList()

        var counter = 0
        var stepper = 0

        val availablePoint = emptyPoints.filter { it.c == '.' }
        val total = availablePoint.size
        println("Total: " + total);

        availablePoint.forEach { pointToChange ->
            run {
                val pointListClone = points.map { it.clone() }.toList()
                pointListClone.find { it.same(pointToChange) }?.c = '#'
                var actualPoint = pointListClone
                        .find { isDirection(it) }
                        .takeIf { it != null }
                        ?: throw RuntimeException("No start point found")
                do {
                    actualPoint.visited += 1
                    actualPoint.directions.add(actualPoint.c)
                    val nextPoint = nextPoint(actualPoint.c, actualPoint.x, actualPoint.y, pointListClone)
                    if (nextPoint != null && nextPoint.directions.contains(actualPoint.c)) {
                        counter++
                        return@run
                    }
                    if (nextPoint != null) {
                        if (nextPoint.c == '#') {
                            actualPoint.c = nextValue(actualPoint.c)
                            actualPoint.visited -= 1;
                        }
                        if (nextPoint.c == '.') {
                            nextPoint.c = actualPoint.c
                            actualPoint.c = '.'
                            actualPoint = nextPoint
                        }
                    }
                } while (nextPoint != null)
                stepper++
                if (stepper % 100 == 0) {
                    println("Step $stepper/$total")
                }
            }
        }

        return counter
    }

    val testInput = readInput("Day06_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 41)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
