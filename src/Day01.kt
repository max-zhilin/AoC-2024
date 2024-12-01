import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val left = mutableListOf<String>()
        val right = mutableListOf<String>()
        input.forEach {
            val pair = it.split(' ')
            left.add(pair.first())
            right.add(pair.last())
        }
        left.sort()
        right.sort()

        var sum = 0
        for (i in 0 until left.size) {
            sum += abs(left[i].toInt() - right[i].toInt())
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val left = mutableListOf<String>()
        val right = mutableListOf<String>()
        input.forEach {
            val pair = it.split(' ')
            left.add(pair.first())
            right.add(pair.last())
        }

        var sum = 0
        for (i in 0 until left.size) {

            sum += left[i].toInt() * right.count { it == left[i] }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
