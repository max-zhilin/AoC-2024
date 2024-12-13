
fun main() {

    fun part1(input: List<String>): Int {

        val (maxRow, maxCol) = listOf(input.lastIndex, input[0].lastIndex)
        val visited = Array(maxRow + 1) { Array(maxCol + 1) { false } }

        fun countAndMark(rCurr: Int, cCurr: Int, plant: Char): Pair<Int, Int> {

            fun fence(r: Int, c: Int): Boolean =
                r !in 0..maxRow || c !in 0..maxCol ||
                        input[r][c] != plant

            visited[rCurr][cCurr] = true
            var area = 1
            var perimeter = 0
            val coords = listOf(rCurr to cCurr + 1,
                rCurr to cCurr - 1,
                rCurr + 1 to cCurr,
                rCurr - 1 to cCurr, )
            for ((r, c) in coords)
                if (fence(r, c))
                    perimeter++
                else if (!visited[r][c]) {
                        val (a, p) = countAndMark(r, c, plant)
                        area += a
                        perimeter += p
                }

            return area to perimeter
        }

        var sum = 0
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, plant ->
                if (!visited[r][c]) {
                    val (area, perimeter) = countAndMark(r, c, plant)
                    //println("$r $c $plant $area $perimeter")
                    sum += area * perimeter
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {

        val (maxRow, maxCol) = listOf(input.lastIndex, input[0].lastIndex)
        val visited = Array(maxRow + 1) { Array(maxCol + 1) { false } }

        fun countAndMark(r: Int, c: Int, plant: Char): Pair<Int, Int> {

            fun fence(r: Int, c: Int): Boolean =
                r !in 0..maxRow || c !in 0..maxCol ||
                        input[r][c] != plant

            visited[r][c] = true
            var area = 1
            var corners = 0
            val dirs = listOf(0 to 1,
                0 to - 1,
                1 to 0,
                - 1 to 0, )
            for ((dr, dc) in dirs)
                if (fence(r + dr, c + dc)) {
                    for (sideStep in -1..1 step 2)
                        if (dr == 0) {
                            if (fence(r + sideStep, c))
                                corners++
                            else
                                if (!fence(r + sideStep, c + dc))
                                    corners++
                        } else {
                            if (fence(r, c + sideStep))
                                corners++
                            else
                                if (!fence(r + dr, c + sideStep))
                                    corners++
                        }

                } else if (!visited[r + dr][c + dc]) {
                    val (a, p) = countAndMark(r + dr, c + dc, plant)
                    area += a
                    corners += p
                }

            return area to corners
        }

        var sum = 0
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, plant ->
                if (!visited[r][c]) {
                    val (area, corners) = countAndMark(r, c, plant)
                    //println("$r $c $plant $area $corners")
                    sum += area * corners / 2  // because each corner counts 2 times
                }
            }
        }
        return sum
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day12_test")
    try {
        check(part1(testInput) == 1930)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    check(part2(testInput) == 1206)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day12")
    part1(input).println()  // 1396562
    part2(input).println()  // 844132
}
