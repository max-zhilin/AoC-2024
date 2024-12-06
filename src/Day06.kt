fun main() {

    val RIGHT = 0
    val DOWN = 1
    val LEFT = 2
    val UP = 3

    val VOID = '.'
    val MARK = 'X'
    val WALL = '#'

    fun part1(input: List<String>): Int {

        var row = -1
        var col = -1
        var facing = UP
        val maxRow = input.size - 1
        val maxCol = input[0].length  - 1
        val board = Array(maxRow + 1) { Array(maxCol + 1) { VOID } }
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == '^') {
                    row = r
                    col = c
                    board[r][c] = MARK
                } else
                    board[r][c] = char
            }
        }

        fun newCoord(r: Int, c: Int) = when (facing) {
            RIGHT -> Pair(r, c + 1)
            DOWN -> Pair(r + 1, c)
            LEFT -> Pair(r, c - 1)
            UP -> Pair(r - 1, c)
            else -> error("facing $facing")
        }

        while (true) {
            val (rowNew, colNew) = newCoord(row, col)

            if (rowNew in 0..maxRow && colNew in 0..maxCol) {
                if (board[rowNew][colNew] != WALL) {
                    row = rowNew
                    col = colNew
                    board[row][col] = MARK
                } else
                    facing = (4 + facing + 1) % 4
            } else
                break
        }

        return board.flatten().count { it == MARK }
    }

    fun part2(input: List<String>): Int {
        var row = -1
        var col = -1
        var facing = UP
        val maxRow = input.size - 1
        val maxCol = input[0].length  - 1
        val board = Array(maxRow + 1) { Array(maxCol + 1) { VOID } }
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == '^') {
                    row = r
                    col = c
                    board[r][c] = MARK
                } else
                    board[r][c] = char
            }
        }
        val (rowStart, colStart) = row to col

        fun newCoord(r: Int, c: Int) = when (facing) {
            RIGHT -> Pair(r, c + 1)
            DOWN -> Pair(r + 1, c)
            LEFT -> Pair(r, c - 1)
            UP -> Pair(r - 1, c)
            else -> error("facing $facing")
        }

        while (true) {
            val (rowNew, colNew) = newCoord(row, col)

            if (rowNew in 0..maxRow && colNew in 0..maxCol) {
                if (board[rowNew][colNew] != WALL) {
                    row = rowNew
                    col = colNew
                    board[row][col] = MARK
                } else
                    facing = (4 + facing + 1) % 4
            } else
                break
        }

        var sum = 0
        for (r in 0..maxRow)
            for (c in 0..maxCol)
                if (board[r][c] == MARK && c != rowStart && c != colStart) {
                    board[r][c] = WALL
                    row = rowStart
                    col = colStart
                    facing = UP
                    val moves = Array(maxRow + 1) { Array(maxCol + 1) { mutableSetOf<Int>() } }

                    while (true) {
                        val (rowNew, colNew) = newCoord(row, col)

                        if (rowNew in 0..maxRow && colNew in 0..maxCol) {
                            if (board[rowNew][colNew] != WALL) {
                                row = rowNew
                                col = colNew
                            } else {
                                facing = (4 + facing + 1) % 4
                            }
                            if (moves[row][col].contains(facing)) {
                                println("$row $col")
                                sum++
                                break
                            } else {
                                moves[row][col].add(facing)
                            }
                        } else {
                            println("$row $col before break")
                            if (true) {
                                break

                            }
                            val b = 2
                        }
                        val a = 1
                    }
                    println("$row $col after break")
                    board[r][c] = MARK
                }

        return sum
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day06_test")
    //check(part1(testInput) == 41)
    check(part2(testInput).println() == 6)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day06")
    //part1(input).println()
    //part2(input).println()
}
