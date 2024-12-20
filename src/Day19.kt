import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {

        val input1 = input.first().split(", ")
        val maxLength = input1.maxOf { it.length }
        val towels = input1.toSet()
        val designs = input.takeLastWhile { it != "" }

        fun isPossible(s: String): Boolean {

            if (s.length <= maxLength && s in towels)
                return true

            for (i in 1..maxLength) {

                if (s.take(i) in towels && isPossible(s.drop(i)))
                    return true
            }
            return false
        }

        return designs.count { isPossible(it) }
    }

    fun part2(input: List<String>): Long {

        val input1 = input.first().split(", ")
        val maxLength = input1.maxOf { it.length }
        val towels = input1.toSet()
        val designs = input.takeLastWhile { it.isNotEmpty() }

        val cache = mutableMapOf<String, Long>()

        fun countPossible(s: String): Long {

            if (s.isEmpty())
                return 1L
            else {
                var sum = 0L
                for (i in 1..min(s.length, maxLength))
                    if (s.take(i) in towels) {
                        val rest = s.drop(i)
                        sum += cache.getOrPut(rest) { countPossible(rest) }
                    }
                return sum
            }
        }

        return designs.sumOf { design ->
            countPossible(design).also { println("$design $it") }
        }
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day19_test")
    try {
        //check(part1(testInput) == 6)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    check(part2(testInput).println() == 16L)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day19")
    //part1(input).println()  // 269
    part2(input).println()  // 758839075658876
}
