
fun main() {

    fun part1(input: List<String>, width: Int, height: Int): Int {

        val (maxRow, maxCol) = height - 1 to width - 1

        data class Robot(var r: Int, var c: Int, val rv: Int, val cv: Int) {
            fun move() {
                r = (r + rv) % height
                c = (c + cv) % width
                if (r < 0) r += height
                if (c < 0) c += width
            }
        }

        val regex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()
        val robots = input.map {
            val (xs, ys, xvs, yvs) = regex.find(it)!!.destructured
            Robot(ys.toInt(), xs.toInt(), yvs.toInt(), xvs.toInt())
        }

        repeat(100) {
            robots.forEach { it.move() }
        }
        val quadrants = mutableListOf(0, 0, 0 ,0)
        robots.forEach { robot ->
            if (robot.r < maxRow / 2) {
                if (robot.c < maxCol / 2)
                    quadrants[0]++
                else if (robot.c > maxCol / 2)
                    quadrants[1]++
            } else {
                if (robot.r > maxRow / 2)
                    if (robot.c < maxCol / 2)
                        quadrants[2]++
                    else if (robot.c > maxCol / 2)
                        quadrants[3]++
            }
        }

        return quadrants.fold(1) { acc, e -> acc * e}
    }

    fun part2(input: List<String>, width: Int, height: Int): Int {

        val (maxRow, maxCol) = height - 1 to width - 1

        data class Robot(var r: Int, var c: Int, val rv: Int, val cv: Int) {
            fun move() {
                r = (r + rv) % height
                c = (c + cv) % width
                if (r < 0) r += height
                if (c < 0) c += width
            }
        }

        val regex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()
        val robots = input.map {
            val (xs, ys, xvs, yvs) = regex.find(it)!!.destructured
            Robot(ys.toInt(), xs.toInt(), yvs.toInt(), xvs.toInt())
        }

        val board = Array(height) { Array(width) { '.' } }
        var h = 0
        var v = 0
        repeat(10000) { time ->
            robots.forEach { it.move() }

            board.forEach { it.fill('.') }
            robots.forEach { board[it.r][it.c] = '#' }

            h = board.maxOf { it.count { it == '#' } }
            v = (0..maxCol).maxOf { c -> (0..maxRow).count { r -> board[r][c] == '#' } }
            if (h > 30 && v > 30) {
                println("${time + 1} h $h v $v")
                board.forEach { println(it.joinToString("")) }
            }

        }
        val quadrants = mutableListOf(0, 0, 0 ,0)
        robots.forEach { robot ->
            if (robot.r < maxRow / 2) {
                if (robot.c < maxCol / 2)
                    quadrants[0]++
                else if (robot.c > maxCol / 2)
                    quadrants[1]++
            } else {
                if (robot.r > maxRow / 2)
                    if (robot.c < maxCol / 2)
                        quadrants[2]++
                    else if (robot.c > maxCol / 2)
                        quadrants[3]++
            }
        }

        return quadrants.fold(1) { acc, e -> acc * e}
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day14_test")
    try {
        //check(part1(testInput, 11, 7) == 12)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput, 11, 7))
        throw e
    }
    //check(part2(testInput, 11, 7) == 12)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day14")
    //part1(input, 101, 103).println()  // 219150360
    part2(input, 101, 103).println()  // 121, 174 too low, 18h 73v 224? 275
}
