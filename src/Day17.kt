
enum class Instruction(val c: Char) {
    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/');
}

fun main() {

    fun part1(input: String): String {

        val (input1, input2) = input.split("\r\n\r\n")
        var (a, b, c) = input1.lines().map { it.replace("""Register .: """.toRegex(), "").toInt() }
        var ip = 0
        val program = input2.substringAfter("Program: ").split(",").map { it.toInt() }
            .chunked(2) { it[0] to it[1] }

        while (true) {
            val itr = program.iterator()

//            while (itr.hasNext()) {
//                with(itr.next()) {
//                    if (first == null) {
//                        first = numbers[firstName]
//                    }
//                    if (second == null) {
//                        second = numbers[secondName]
//                    }
//                    if (first != null && second != null) {
//                        val solvedNumber = solve(first!!, op, second!!)
//                        numbers[name] = solvedNumber
//                        if (name == "root") return numbers[name]!!
//                        else itr.remove()
//                    }
//                }
//            }
        }

        return "0"
    }

    fun part2(input: String): String {

        return "0"
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readWholeInput("Day17_test")
    try {
        check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    //check(part2(testInput) == 0)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day17")
    part1(input).println()
    //part2(input).println()
}
