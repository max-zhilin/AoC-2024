
fun main() {

    fun part1(input: List<String>, nodes: Int): Int {

        return 0
    }

    fun part2(input: List<String>, nodes: Int): Int {

        return 0
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day23_test")
    try {
        check(part1(testInput, 3) == 7)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput, 3))
        throw e
    }

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day23")
    part1(input, 3).println()
    //part2(input, 2000).println()
}
