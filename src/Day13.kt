
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

    fun part2(input: List<String>): Long {

        fun linearCount(a: Long, b: Long, p: Long): Long {
            for (i in 0..99) {
                if ((p % a + i * a) % b == 0L) {
                    val ac = p / a - i
                    val bc = (p % a + i * a) / b
                    return ac * 3 + bc
                }
            }
            return 0
        }

        var sum = 0L
        val regex = """Button .: X\+(\d+), Y\+(\d+)|Prize: X=(\d+), Y=(\d+)""".toRegex()
        input.forEach { machine ->
            val (sa, sb, sprize) = regex.findAll(machine).toList()
            val (ax, ay) = sa.destructured.toList().take(2).map { it.toLong() }
            val (bx, by) = sb.destructured.toList().take(2).map { it.toLong() }
            val (px, py) = sprize.destructured.toList().drop(2).map { it.toLong() + 10000000000000 }

            if (ay * bx == by * ax) {   // a is collinear with b
                if (ay * px == py * ax) { // p is collinear with a (and b together)
                    sum += if (ax > 3 * bx)   // solve in either x or y
                        linearCount(ax, bx, px)
                    else
                        linearCount(bx, ax, px)
                } else {
                    // don't match
                }
            } else {    // ca != cb
                val ca = ay.toDouble() / ax
                val cb = by.toDouble() / bx
                val cx = (py.toDouble() - cb * px) / (ca - cb)
                //println("$px $cx")
                val a = (cx / ax).toLong()
                //println("a $a, ${cx / ax}")
                val b = ((px - cx) / bx).toLong()
                //println("b $b, ${(px - cx) / bx}")
                if (a * ax + b * bx == px && a * ay + b * by == py) {
                    //println("counted")
                    sum += a * 3 + b
                } else if ((a + 1) * ax + b * bx == px && (a + 1) * ay + b * by == py) {
                    //println("counted a + 1")
                    sum += (a + 1) * 3 + b
                } else if (a * ax + (b + 1) * bx == px && a * ay + (b + 1) * by == py) {
                    //println("counted b + 1")
                    sum += a * 3 + b + 1
                }
            }
        }
        return sum
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readWholeInput("Day13_test").split("\r\n\r\n")
    try {
        check(part1(testInput) == 480)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    check(part2(testInput) == 875318608908L)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day13").split("\r\n\r\n")
    //part1(input).println()  // 29201
    part2(input).println()  // 104140871044942  // 69406943888728 too low, 105840545855512 too high
}
