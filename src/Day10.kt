fun main() {

    fun part1(input: List<String>): Int {

        val (maxRow, maxCol) = listOf(input.lastIndex, input[0].lastIndex)

        val visited = mutableSetOf<Pair<Int,Int>>()

        fun countScore(r: Int, c: Int, height: Char): Int {
            if (r !in 0..maxRow || c !in 0..maxCol) return 0
            if (height != input[r][c]) return 0
            if (input[r][c] == '9')
                return if (visited.add(r to c)) 1 else 0    // add is true when it was contained in set already
            return countScore(r, c + 1, height + 1) +
                    countScore(r, c - 1, height + 1) +
                    countScore(r + 1, c, height + 1) +
                    countScore(r - 1, c, height + 1)
        }

        var sum = 0
        for (r in 0..maxRow)
            for (c in 0..maxCol)
                if (input[r][c] == '0') {
                    visited.clear()
                    sum += countScore(r, c, '0')
                }

        return sum
    }

    fun part2(input: List<String>): Int {

        val (maxRow, maxCol) = listOf(input.lastIndex, input[0].lastIndex)

        fun countScore(r: Int, c: Int, height: Char): Int? {
            if (r !in 0..maxRow || c !in 0..maxCol) return null
            if (height != input[r][c]) return null
            if (input[r][c] == '9') return 1
            return (countScore(r, c + 1, height + 1) ?: 0) +
                    (countScore(r, c - 1, height + 1) ?: 0) +
                    (countScore(r + 1, c, height + 1) ?: 0) +
                    (countScore(r - 1, c, height + 1) ?: 0)
        }

        var sum = 0
        for (r in 0..maxRow)
            for (c in 0..maxCol)
                if (input[r][c] == '0')
                    sum += countScore(r, c, '0') ?: 0

        return sum
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day10")
    //part1(input).println()
    part2(input).println()
}
