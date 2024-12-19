import Dir.*
import java.nio.file.FileVisitOption
import java.util.PriorityQueue


fun main() {

    data class Pos(val r: Int, val c: Int)

    val VOID = '.'
    val WALL = '#'
    val PATH = 'O'

    fun part1(input: List<String>, rows: Int, cols: Int, bytes: Int): Boolean {

        val maxRow = rows - 1
        val maxCol = cols - 1
        val board = Array(rows) { Array(cols) { VOID } }
        repeat(bytes) {
            val xy = input[it].split(",").map { it.toInt()}
            // input has column first
            val (r, c) = xy.last() to xy.first()
            board[r][c] = WALL
        }

        fun possibleMoves(pos: Pos): List<Pos> {
            return buildList { with(pos) {
                for ((dr, dc) in setOf(0 to 1, 0 to -1, 1 to 0, -1 to 0))
                    if (r + dr in 0..maxRow && c + dc in 0..maxCol)
                        if (board[r + dr][c + dc] == VOID)
                            add(Pos(r + dr, c + dc))
            }}
        }

        val start = Pos(0, 0)
        var positions = mutableListOf(start)
        val exit = Pos(maxRow, maxCol)

        //val visited = Array(rows) { Array(cols) { VOID } }
        var exitFound = false
        var counter = 0
        while (positions.isNotEmpty()) {
            val newPos = mutableListOf<Pos>()
            positions.forEach { pos ->
                if (pos == exit || exitFound) exitFound = true
                else
                    possibleMoves(pos).forEach { (r, c) ->
                        board[r][c] = PATH
                        newPos.add(Pos(r, c))
                    }
            }
            if (exitFound) break
            else counter++
            positions = newPos
        }

        return exitFound    //counter
    }

    fun part2(input: List<String>, rows: Int, cols: Int, bytes: Int): String {

        for (i in bytes..input.lastIndex)
            if (!part1(input, rows, cols, i + 1)) {
                return input[i]
            }
        return "no solution"
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day18_test")
    try {
        //check(part1(testInput, 7, 7, 12) == 22)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput, 7, 7, 12))
        throw e
    }
    check(part2(testInput, 7, 7, 12).println() == "6,1")

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day18")
    //part1(input, 71, 71, 1024).println()
    part2(input, 71, 71, 1024).println()
}
