fun main() {
    fun List<String>.hasLetter(letter: Char, row: Int, col: Int): Boolean {
        val maxRow = size - 1
        val maxCol = this[0].length - 1

        return if (row in 0..maxRow && col in 0..maxCol)
                this[row][col] == letter
            else
                false
    }

    fun List<String>.countWord(word: String, row: Int, col: Int): Int {

        var sum = 0
        for (i in -1..1)
            for (j in -1..1) {
                var found = true
                for (pos in 0..word.lastIndex)
                    if (!hasLetter(word[pos], row + pos * i, col + pos * j)) found = false
                if (found) sum++
            }
        return sum
    }

    fun List<String>.hasX(word: String, row: Int, col: Int): Boolean {

        var firstForward = true
        var firstBackward = true
        var secondForward = true
        var secondBackward = true
        for (pos in 0..word.lastIndex) {
            if (!hasLetter(word[pos], row + pos - 1, col + pos - 1)) firstForward = false
            if (!hasLetter(word[pos], row - pos + 1, col - pos + 1)) firstBackward = false
            if (!hasLetter(word[pos], row - pos + 1, col + pos - 1)) secondForward = false
            if (!hasLetter(word[pos], row + pos - 1, col - pos + 1)) secondBackward = false
        }
        return (firstForward || firstBackward) && (secondForward || secondBackward)
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (row in input.indices)
            for (col in 0 until input[0].length)
                sum += input.countWord("XMAS", row, col)

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (row in input.indices)
            for (col in 0 until input[0].length)
                if (input.hasX("MAS", row, col)) sum++

        return sum
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
