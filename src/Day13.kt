
fun main() {

    fun calc(ax: Int, ay: Int, bx: Int, by: Int, px: Int, py: Int): Int {
        val list = mutableListOf<Int>()
        for (a in 0..100)
            for (b in 0..100)
                if (a * ax + b * bx == px && a * ay + b * by == py)
                    list += a * 3 + b

        return list.minOrNull() ?: 0
    }

    fun part1(input: List<String>): Int {

        var sum = 0
        val regex = """Button .: X\+(\d+), Y\+(\d+)|Prize: X=(\d+), Y=(\d+)""".toRegex()
        input.forEach { machine ->
            val (sa, sb, sprize) = regex.findAll(machine).toList()
            val (ax, ay) = sa.destructured.toList().take(2).map { it.toInt() }
            val (bx, by) = sb.destructured.toList().take(2).map { it.toInt() }
            val (px, py) = sprize.destructured.toList().drop(2).map { it.toInt() }
            sum += calc(ax, ay, bx, by, px, py)
        }
        return sum
    }

    fun part2(input: List<String>): Int {

        var sum = 0
        val regex = """Button .: X\+(\d+), Y\+(\d+)|Prize: X=(\d+), Y=(\d+)""".toRegex()
        input.forEach { machine ->
            val (sa, sb, sprize) = regex.findAll(machine).toList()
            val (ax, ay) = sa.destructured.toList().take(2).map { it.toInt() }
            val (bx, by) = sb.destructured.toList().take(2).map { it.toInt() }
            val (px, py) = sprize.destructured.toList().drop(2).map { it.toInt() }


            sum += calc(ax, ay, bx, by, px, py)
        }
        return sum
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readWholeInput("Day13_test").split("\r\n\r\n")
    try {
        //check(part1(testInput) == 480)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    //check(part2(testInput) == 1206)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day13").split("\r\n\r\n")
    //part1(input).println()  // 29201
    part2(input).println()  //
}
