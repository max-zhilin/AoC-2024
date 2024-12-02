import kotlin.math.abs


fun main() {
    fun safe(levels: List<Int>): Boolean {
        var prev: Int? = null
        var rise: Boolean? = null
        for (level in levels) {
            if (prev != null) {
                if (rise == null)
                    rise = level > prev
                else if (rise != level > prev)
                    return false

                if (abs(level - prev) !in 1..3) {
                    return false
                }
            }
            prev = level
        }
        return true
    }

    fun part1(input: List<String>): Int {

        return input.count {
            safe(it.split(' ').map { it.toInt() })
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val levels = it.split(' ').map { it.toInt() }
            val minusOnes = List(levels.size) { index ->  levels.filterIndexed { filterIndex, _ -> filterIndex != index } }
            safe(levels) || minusOnes.fold(false){ acc, minusOne -> acc || safe(minusOne) }
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
