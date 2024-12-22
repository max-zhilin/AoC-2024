
fun main() {

    fun part1(input: List<String>, times: Int): Long {

        fun generate(prev: Long): Long {

            var result = prev
            repeat(times) {
                result = (result shl 6 xor result) % 16_777_216
                result = result shr 5 xor result
                result = (result shl 11 xor result) % 16_777_216
            }

            return result
        }

        return input.map { line -> line.toLong() }
            .sumOf { generate(it) }
    }

    fun part2(input: List<String>, times: Int): Long {

        fun generate(init: Long): MutableMap<String, Int> {

            val top = mutableMapOf<String, Int>()

            val digits = MutableList(5) { 0 }
            digits[4] = (init % 10).toInt()

            val diff = MutableList(4) { 0 }

            var result = init
            repeat(times) { time ->
                for (i in 1..4) digits[i - 1] = digits[i]

                result = (result shl 6 xor result) % 16_777_216
                result = result shr 5 xor result
                result = (result shl 11 xor result) % 16_777_216

                digits[4] = (result % 10).toInt()

                if (time >= 4) {
                    for (i in 0..3) diff[i] = digits[i + 1] - digits[i]
                    val key = diff.joinToString("") { ('j' + it).toString() }   // convert diffs to letters
                    top.getOrPut(key) { digits[4] } // only first occurrence mean
                }
            }

            return top
        }

        return input.flatMap { line -> generate(line.toLong()).entries }
            .groupingBy { it.key }.fold(0L) { acc, s -> acc + s.value }
            .maxOf { it.value }
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day22_test")
    try {
        //check(part1(testInput, 2000) == 37327623L)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput, 2000))
        throw e
    }
    val testInput2 = readInput("Day22_test2")
    check(part2(testInput2, 2000).println() == 23L)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day22")
    //part1(input, 2000).println()    // 14726157693
    part2(input, 2000).println()    // 1614
}
