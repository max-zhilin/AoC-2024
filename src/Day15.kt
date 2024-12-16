
fun main() {

    val VOID = '.'
    val WALL = '#'
    val BOX = 'O'

    fun part1(input: String): Int {

        val (input1, input2) = input.split("\r\n\r\n")
        val map = input1.lines()
        val moves = input2.lines().joinToString("")
        val (maxRow, maxCol) = map.lastIndex to map[0].lastIndex
        var row = -1
        var col = -1
        val board = Array(maxRow + 1) { Array(maxCol + 1) { VOID } }
        map.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == '@') {
                    row = r
                    col = c
                } else
                    board[r][c] = char
            }
        }

        fun newCoord(r: Int, c: Int, dir: Char) = when (dir) {
            '>' -> Pair(r, c + 1)
            'v' -> Pair(r + 1, c)
            '<' -> Pair(r, c - 1)
            '^' -> Pair(r - 1, c)
            else -> error("dir $dir")
        }

        fun tryMove(r: Int, c: Int, dir: Char): Boolean {
            val (nr, nc) = newCoord(r, c, dir)

            if (board[nr][nc] == WALL) return false
            if (board[nr][nc] == VOID) {
                board[nr][nc] = board[r][c]
                return true
            }
            if (board[nr][nc] == BOX) {
                if (tryMove(nr, nc, dir)) {
                    board[nr][nc] = board[r][c]
                    return true
                } else
                    return false
            } else error("wrong state")
        }

        moves.forEach { moveDir ->
            if (tryMove(row, col, moveDir)) {
                board[row][col] = VOID
                val newCoords = newCoord(row, col, moveDir)
                row = newCoords.first
                col = newCoords.second
            }
        }

        return board.foldIndexed(0) { r, acc, line ->
            acc + line.foldIndexed(0) { c, acc, char ->
                acc + if (char == BOX) 100 * r + c else 0 }
        }
    }

    fun part2(input: String): Int {

        val BOXLEFT = '['
        val BOXRIGHT = ']'

        val (input1, input2) = input.split("\r\n\r\n")
        val map = input1.lines()
        val moves = input2.lines().joinToString("")
        val (rows, cols) = map.size to 2 * map[0].length
        var row = -1
        var col = -1
        val board = Array(rows) { Array(2 * cols) { VOID } }
        map.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == '@') {
                    row = r
                    col = 2 * c
                } else if (char == BOX) {
                    board[r][2 * c] = BOXLEFT
                    board[r][2 * c + 1] = BOXRIGHT
                } else if (char == WALL) {
                    board[r][2 * c] = WALL
                    board[r][2 * c + 1] = WALL
                }
            }
        }

        fun newCoord(r: Int, c: Int, dir: Char) = when (dir) {
            '>' -> Pair(r, c + 1)
            'v' -> Pair(r + 1, c)
            '<' -> Pair(r, c - 1)
            '^' -> Pair(r - 1, c)
            else -> error("dir $dir")
        }

        fun canMove(r: Int, c: Int, dir: Char): Boolean {

            if (board[r][c] == WALL) return false
            if (board[r][c] == VOID) return true

            val (nr, nc) = newCoord(r, c, dir)
            if (board[r][c] == BOXLEFT)
                return (dir == '>' || canMove(nr, nc, dir)) && (dir == '<' || canMove(nr, nc + 1, dir))
            if (board[r][c] == BOXRIGHT)
                return (dir == '>' || canMove(nr, nc - 1, dir)) && (dir == '<' || canMove(nr, nc, dir))
            error("wrong state ${board[r][c]}")
        }

        fun move(r: Int, c: Int, dir: Char) {

            if (board[r][c] == VOID) return

            val (nr, nc) = newCoord(r, c, dir)
            if (board[r][c] == BOXLEFT) {
                if (dir == '>') {
                    move(nr, nc + 1, dir)
                    board[nr][nc + 1] = BOXRIGHT
                    board[nr][nc] = BOXLEFT
                    board[r][c] = VOID
                } else if (dir == '<') { error("BOXLEFT $dir")
                } else {    // we can't get here from the right, because of ]
                    move(nr, nc, dir)
                    board[nr][nc] = BOXLEFT
                    board[r][c] = VOID

                    move(nr, nc + 1, dir)
                    board[nr][nc + 1] = BOXRIGHT
                    board[r][c + 1] = VOID
                }
            }
            if (board[r][c] == BOXRIGHT) {
                if (dir == '<') {
                    move(nr, nc - 1, dir)
                    board[nr][nc - 1] = BOXLEFT
                    board[nr][nc] = BOXRIGHT
                    board[r][c] = VOID
                } else if (dir == '>') { error("BOXRIGHT $dir")
                } else {
                    move(nr, nc, dir)
                    board[nr][nc] = BOXRIGHT
                    board[r][c] = VOID

                    move(nr, nc - 1, dir)
                    board[nr][nc - 1] = BOXLEFT
                    board[r][c - 1] = VOID
                }
            }
        }

        moves.forEachIndexed { index, moveDir ->
            val (newRow, newCol) = newCoord(row, col, moveDir)
            if (canMove(newRow, newCol, moveDir)) {
                move(newRow, newCol, moveDir)
                row = newRow
                col = newCol
            }
        }

        return board.foldIndexed(0) { r, acc, line ->
            acc + line.foldIndexed(0) { c, acc, char ->
                acc + if (char == BOXLEFT) 100 * r + c else 0 }
        }
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readWholeInput("Day15_test")
    try {
        check(part1(testInput) == 10092)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    check(part2(testInput).println() == 9021)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day15")
    //part1(input).println()
    part2(input).println()
}
