import kotlin.math.abs

fun main() {

    data class Pos(val r: Int, val c: Int)

    val VOID = '.'
    val WALL = '#'
    val PATH = 'O'

    fun part1(input: List<String>, threshold: Int): Int {

        val (rows, cols) = input.size to input[0].length
        val lastRow = rows - 1
        val lastCol = cols - 1
        var start = Pos(-1, -1)
        var exit = Pos(-1, -1)
        val board = Array(rows) { Array(cols) { VOID } }
        val distance = Array(rows) { Array(cols) { -1 } }  // distance from start
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == 'S') {
                    start = Pos(r, c)
                } else if (char == 'E') {
                    exit = Pos(r, c)
                } else
                    board[r][c] = char
            }
        }

        fun nextMove(pos: Pos): Pos {
            var found = false
            var result = Pos(-1, -1)
            with(pos) {
                for ((dr, dc) in setOf(0 to 1, 0 to -1, 1 to 0, -1 to 0))
                    if (r + dr in 0..lastRow && c + dc in 0..lastCol)
                        if (board[r + dr][c + dc] == VOID)
                            if (found)
                                error("fork $r $c")
                            else {
                                found = true
                                result = Pos(r + dr, c + dc)
                            }
            }
            return result
        }

        var pos = start
        var dist = 0
        board[pos.r][pos.c] = PATH
        distance[pos.r][pos.c] = dist
        while (pos != exit) {
            pos = nextMove(pos)
            dist++
            board[pos.r][pos.c] = PATH
            distance[pos.r][pos.c] = dist
        }

        fun canCheat(r1: Int, c1: Int, r2: Int, c2: Int): Boolean =
            (distance[r1][c1] != -1 && distance[r2][c2] != -1) &&
                    (abs(distance[r1][c1] - distance[r2][c2]) - 2 >= threshold) // 2 picoseconds for cheat itself

        fun tryToCheat(r: Int, c: Int): Int =
            if (canCheat(r - 1, c, r + 1, c)) 1 else 0 +
            if (canCheat(r, c - 1, r, c + 1)) 1 else 0

        var cheats = 0
        for (r in 1..<lastRow)
            for (c in 1..<lastCol)
                if (board[r][c] == WALL)
                    cheats += tryToCheat(r, c)

        return cheats
    }

    fun part2(input: List<String>, threshold: Int): Int {

        val (rows, cols) = input.size to input[0].length
        val lastRow = rows - 1
        val lastCol = cols - 1
        var start = Pos(-1, -1)
        var exit = Pos(-1, -1)
        val board = Array(rows) { Array(cols) { VOID } }
        val distance = Array(rows) { Array(cols) { -1 } }  // distance from start
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == 'S') {
                    start = Pos(r, c)
                } else if (char == 'E') {
                    exit = Pos(r, c)
                } else
                    board[r][c] = char
            }
        }

        fun nextMove(pos: Pos): Pos {
            with(pos) {
                for ((dr, dc) in setOf(0 to 1, 0 to -1, 1 to 0, -1 to 0))
                    if (r + dr in 0..lastRow && c + dc in 0..lastCol)
                        if (board[r + dr][c + dc] == VOID)
                            return Pos(r + dr, c + dc)
                error("deadend $r $c")
            }
        }

        var pos = start
        var dist = 0
        board[pos.r][pos.c] = PATH
        distance[pos.r][pos.c] = dist
        while (pos != exit) {
            pos = nextMove(pos)
            dist++
            board[pos.r][pos.c] = PATH
            distance[pos.r][pos.c] = dist
        }

        fun countCheats(r: Int, c: Int, fromDist: Int): Int {
            var counter = 0
            for (dr in -20..20)
                for (dc in -20 + abs(dr)..20 - abs(dr))
                    if (r + dr in 0..lastRow && c + dc in 0..lastCol)
                        if (distance[r + dr][c + dc] - fromDist - abs(dr) - abs(dc) >= threshold)
                            counter++
            return counter
        }

        var cheats = 0
        for (r in 1..<lastRow)
            for (c in 1..<lastCol)
                if (distance[r][c] != -1)
                    cheats += countCheats(r, c, distance[r][c])

        return cheats
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day20_test")
    try {
        //check(part1(testInput, 8) == 14)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput, 8))
        throw e
    }
    check(part2(testInput, 72).println() == 29)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day20")
    //part1(input, 100).println()
    part2(input, 100).println()
}
