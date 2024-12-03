fun main() {

    fun sumMuls(line: String): Int {

        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

        return regex.findAll(line)
            .map { it.destructured.toList() }
            .sumOf { it.first().toInt() * it.last().toInt() }

    }

    fun part1(input: String): Int {
        return sumMuls(input)
    }

    fun part2(input: String): Int {

        val firstLine = input.substringBefore("don't()")
        val nextLines = input.split("don't()").drop(1)
            .map { it.substringAfter("do()", "") }

        return sumMuls(firstLine) + nextLines.sumOf { sumMuls(it) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readWholeInput("Day03_test")
    check(part1(testInput) == 161)
    val testInput2 = readWholeInput("Day03_test2")
    //println(part2(testInput2))
    check(part2(testInput2) == 48)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day03")
    part1(input).println()
    val input2 = readWholeInput("Day03")
    part2(input2).println()
}
