
val nkPaths = mapOf(
    'A' to '0' to "<",
    'A' to '1' to "^<<",
    'A' to '2' to "<^", // ^8 <9 A8 = 25, <10 ^7 A4 = 21
    'A' to '3' to "^",
    'A' to '4' to "^^<<",
    'A' to '5' to "<^^",
    'A' to '6' to "^^",
    'A' to '7' to "^^^<<",
    'A' to '8' to "<^^^",
    'A' to '9' to "^^^",
    '0' to 'A' to ">",
    '0' to '1' to "^<",
    '0' to '2' to "^",
    '0' to '3' to "^>", // ^8 >7 A4 = 19, >6 ^9 A4 = 19
    '0' to '4' to "^^<",
    '0' to '5' to "^^",
    '0' to '6' to "^^>",
    '0' to '7' to "^^^<",
    '0' to '8' to "^^^",
    '0' to '9' to "^^^>",
    '1' to '0' to ">v",
    '1' to 'A' to ">>v",
    '1' to '2' to ">",
    '1' to '3' to ">>",
    '1' to '4' to "^",
    '1' to '5' to "^>",
    '1' to '6' to "^>>",
    '1' to '7' to "^^",
    '1' to '8' to "^^>",
    '1' to '9' to "^^>>", // ^8+1 >7+1 A4 = 21, >6+1 ^9+1 A4 = 21
    '2' to '0' to "v",
    '2' to 'A' to "v>", // v9 >4 A4 = 17, >6 v8 A7 = 21
    '2' to '1' to "<",
    '2' to '3' to ">",
    '2' to '4' to "<^",
    '2' to '5' to "^",
    '2' to '6' to "^>",
    '2' to '7' to "<^^",
    '2' to '8' to "^^",
    '2' to '9' to "^^>",
    '3' to '0' to "<v", // <10 v4 A7 = 21, v9 <8 A8 = 25
    '3' to 'A' to "v",
    '3' to '1' to "<<",
    '3' to '2' to "<",
    '3' to '4' to "<<^",
    '3' to '5' to "<^",
    '3' to '6' to "^",
    '3' to '7' to "<<^^", // ^8+1 <9+1 A8 = 27, <10+1 ^7+1 A4 = 23
    '3' to '8' to "<^^",
    '3' to '9' to "^^",
    '4' to '0' to ">vv",
    '4' to 'A' to ">>vv",
    '4' to '1' to "v",
    '4' to '2' to "v>",
    '4' to '3' to "v>>",
    '4' to '5' to ">",
    '4' to '6' to ">>",
    '4' to '7' to "^",
    '4' to '8' to "^>",
    '4' to '9' to "^>>",
    '5' to '0' to "vv",
    '5' to 'A' to "vv>",
    '5' to '1' to "<v",
    '5' to '2' to "v",
    '5' to '3' to "v>",
    '5' to '4' to "<",
    '5' to '6' to ">",
    '5' to '7' to "<^",
    '5' to '8' to "^",
    '5' to '9' to "^>",
    '6' to '0' to "<vv",
    '6' to 'A' to "vv",
    '6' to '1' to "<<v",
    '6' to '2' to "<v",
    '6' to '3' to "v",
    '6' to '4' to "<<",
    '6' to '5' to "<",
    '6' to '7' to "<<^",
    '6' to '8' to "<^",
    '6' to '9' to "^",
    '7' to '0' to ">vvv",
    '7' to 'A' to ">>vvv",
    '7' to '1' to "vv",
    '7' to '2' to "vv>",
    '7' to '3' to "vv>>",
    '7' to '4' to "v",
    '7' to '5' to "v>",
    '7' to '6' to "v>>",
    '7' to '8' to ">",
    '7' to '9' to ">>",
    '8' to '0' to "vvv",
    '8' to 'A' to "vvv>",
    '8' to '1' to "<vv",
    '8' to '2' to "vv",
    '8' to '3' to "vv>",
    '8' to '4' to "<v",
    '8' to '5' to "v",
    '8' to '6' to "v>",
    '8' to '7' to "<",
    '8' to '9' to ">",
    '9' to '0' to "<vvv",
    '9' to 'A' to "vvv",
    '9' to '1' to "<<vv",
    '9' to '2' to "<vv",
    '9' to '3' to "vv",
    '9' to '4' to "<<v",
    '9' to '5' to "<v",
    '9' to '6' to "v",
    '9' to '7' to "<<",
    '9' to '8' to "<",
)
val dkAimAndPush = mapOf(
    'A' to 'A' to ("" to 0),
    'A' to '^' to ("<" to 8),
    'A' to '<' to ("v<<" to 10),
    'A' to 'v' to ("<v" to 9),
    'A' to '>' to ("v" to 6),
    '^' to '^' to ("" to 1),
    '^' to 'A' to (">" to 4),
    '^' to '<' to ("v<" to 9),
    '^' to 'v' to ("v" to 6),
    '^' to '>' to ("v>" to 7),
    '<' to '<' to ("" to 1),
    '<' to '^' to (">^" to 7),
    '<' to 'A' to (">>^" to 8),
    '<' to 'v' to (">"  to 4),
    '<' to '>' to (">>" to 5),
    'v' to 'v' to ("" to 1),
    'v' to '^' to ("^" to 4),
    'v' to 'A' to ("^>" to 7),
    'v' to '<' to ("<" to 8),
    'v' to '>' to (">" to 4),
    '>' to '>' to ("" to 1),
    '>' to '^' to ("^<" to 9),
    '>' to 'A' to ("^" to 4),
    '>' to '<' to ("<<" to 9),
    '>' to 'v' to ("<" to 8),
)
fun main() {

    fun part1(input: List<String>): Int {

        return input.map { line ->
            line.trimStart('0').dropLast(1).toInt() *
                    ("A" + line).windowed(2) {
                ("A" + nkPaths[it.first() to it.last()]!! + "A").windowed(2) {
                    dkAimAndPush[it.first() to it.last()]!!.second
                }.sum()
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {

        return input.map { line ->
            line.trimStart('0').dropLast(1).toInt() *
                    ("A" + line).windowed(2) {
                        ("A" + nkPaths[it.first() to it.last()]!! + "A").windowed(2) {
                            ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                    ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                        ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                            ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                    ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                        ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                            ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                    ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                        ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                            ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                    ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                        ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                            ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                    ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                        ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                            ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                                ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                                    ("A" + dkAimAndPush[it.first() to it.last()]!!.first + "A").windowed(2) {
                                                                                                                        dkAimAndPush[it.first() to it.last()]!!.second
                                                                                                                    }.sum()
                                                                                                                }.sum()
                                                                                                            }.sum()
                                                                                                        }.sum()
                                                                                                    }.sum()
                                                                                                }.sum()
                                                                                            }.sum()
                                                                                        }.sum()
                                                                                    }.sum()
                                                                                }.sum()
                                                                            }.sum()
                                                                        }.sum()
                                                                    }.sum()
                                                                }.sum()
                                                            }.sum()
                                                        }.sum()
                                                    }.sum()
                                                }.sum()
                                            }.sum()
                                        }.sum()
                                    }.sum()
                                }.sum()
                            }.sum()
                        }.sum()
                    }.sum()
        }.sum()
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day21_test")
    try {
        //check(part1(testInput) == 126384)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    //check(part2(testInput)() == 29)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day21")
    //part1(input).println()  // 202274 // 203670 was too high
    part2(input).println()
}
