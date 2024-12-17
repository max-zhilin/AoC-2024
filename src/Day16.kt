import java.util.PriorityQueue

enum class Dir { RIGHT, DOWN, LEFT, UP }
data class Node(val r: Int, val c: Int, val dir: Dir)

fun main() {

    val RIGHT = Dir.RIGHT
    val DOWN = Dir.DOWN
    val LEFT = Dir.LEFT
    val UP = Dir.UP

    val VOID = '.'
    val WALL = '#'
    val PATH = 'O'

    fun part1(input: List<String>): Int {

        val (rows, cols) = input.size to input[0].length
        var start = Node(-1, -1, RIGHT)
        var exit = Pair(-1, -1)
        val board = Array(rows) { Array(cols) { VOID } }
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == 'S') {
                    start = Node(r, c, RIGHT)
                } else if (char == 'E') {
                    exit = Pair(r, c)
                } else
                    board[r][c] = char
            }
        }

        fun Node.step(): Node = when (dir) {
            RIGHT -> Node(r, c + 2, dir)
            DOWN -> Node(r + 2, c, dir)
            LEFT -> Node(r, c - 2, dir)
            UP -> Node(r - 2, c, dir)
            else -> error("never be here")
        }
        fun Node.turnLeft(): Node = when (dir) {
            RIGHT -> Node(r, c, UP)
            DOWN -> Node(r, c, RIGHT)
            LEFT -> Node(r, c, DOWN)
            UP -> Node(r, c, LEFT)
            else -> error("never be here")
        }
        fun Node.turnRight(): Node = when (dir) {
            RIGHT -> Node(r, c, DOWN)
            DOWN -> Node(r, c, LEFT)
            LEFT -> Node(r, c, UP)
            UP -> Node(r, c, RIGHT)
            else -> error("never be here")
        }
        fun Node.canGo(): Boolean = when (dir) {
            RIGHT -> board[r][c + 1] == VOID
            DOWN -> board[r + 1][c] == VOID
            LEFT -> board[r][c - 1] == VOID
            UP -> board[r - 1][c] == VOID
            else -> error("never be here")
        }
        fun possibleMoves(node: Node): List<Pair<Node, Int>> {
            return buildList { with(node) {
                if (canGo())
                    add(step() to 2)    // one step is actually two cells
                if (turnLeft().canGo())
                    add(turnLeft().step() to 1002)
                if (turnRight().canGo())
                    add(turnRight().step() to 1002)
            }}
        }

        // dijkstra with loops
        val visited = mutableSetOf<Node>()
        val minScores = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }

        val priorityQueue = PriorityQueue<Pair<Node, Int>>(compareBy { it.second }).apply { add(start to 0) }
        while (priorityQueue.isNotEmpty()) {
            val (node, currentScore) = priorityQueue.poll()
            if (visited.add(node)) {    // in not visited, also mark visited
                for((adjacent, points) in possibleMoves(node)) {
                    val newScore = currentScore + points
                    if (newScore < minScores.getValue(adjacent)) {
                        minScores[adjacent] = newScore
                        priorityQueue.add(adjacent to newScore)
                    }
                }
            }
        }

        return minScores.filter { it.key.r == exit.first && it.key.c == exit.second }.minOf { it.value }
    }

    fun part2(input: List<String>): Int {

        val (rows, cols) = input.size to input[0].length
        var start = Node(-1, -1, RIGHT)
        var exit = Pair(-1, -1)
        val board = Array(rows) { Array(cols) { VOID } }
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char == 'S')
                    start = Node(r, c, RIGHT)
                else if (char == 'E')
                    exit = Pair(r, c)
                else
                    board[r][c] = char
            }
        }

        fun Node.step(): Node = when (dir) {
            RIGHT -> Node(r, c + 2, dir)
            DOWN -> Node(r + 2, c, dir)
            LEFT -> Node(r, c - 2, dir)
            UP -> Node(r - 2, c, dir)
            else -> error("never be here")
        }
        fun Node.turnLeft(): Node = when (dir) {
            RIGHT -> Node(r, c, UP)
            DOWN -> Node(r, c, RIGHT)
            LEFT -> Node(r, c, DOWN)
            UP -> Node(r, c, LEFT)
            else -> error("never be here")
        }
        fun Node.turnRight(): Node = when (dir) {
            RIGHT -> Node(r, c, DOWN)
            DOWN -> Node(r, c, LEFT)
            LEFT -> Node(r, c, UP)
            UP -> Node(r, c, RIGHT)
            else -> error("never be here")
        }
        fun Node.canGo(): Boolean = when (dir) {
            RIGHT -> board[r][c + 1] == VOID
            DOWN -> board[r + 1][c] == VOID
            LEFT -> board[r][c - 1] == VOID
            UP -> board[r - 1][c] == VOID
            else -> error("never be here")
        }
        fun possibleMoves(node: Node): List<Pair<Node, Int>> {
            return buildList { with(node) {
                if (canGo())
                    add(step() to 2)    // one step is actually two cells
                if (turnLeft().canGo())
                    add(turnLeft().step() to 1002)
                if (turnRight().canGo())
                    add(turnRight().step() to 1002)
            }}
        }

        // dijkstra with loops
        val visited = mutableSetOf<Node>()
        val minScores = mutableMapOf<Node, Pair<Int, List<Node>>>().withDefault { Int.MAX_VALUE to listOf() }

        val priorityQueue = PriorityQueue<Pair<Node, Int>>(compareBy { it.second }).apply { add(start to 0) }
        while (priorityQueue.isNotEmpty()) {
            val (node, currentScore) = priorityQueue.poll()
            if (visited.add(node)) {    // in not visited, also mark visited
                for((adjacent, points) in possibleMoves(node)) {
                    val newScore = currentScore + points
                    if (newScore < minScores.getValue(adjacent).first) {
                        minScores[adjacent] = newScore to listOf(node)
                        priorityQueue.add(adjacent to newScore)
                    } else if (newScore == minScores.getValue(adjacent).first) {
                        minScores[adjacent] = newScore to minScores.getValue(adjacent).second + node
                        priorityQueue.add(adjacent to newScore)
                    }
                }
            }
        }

        fun paintBackDoor(node: Node) = with(node) { when (dir) {
            RIGHT -> board[r][c - 1] = PATH
            DOWN -> board[r - 1][c] = PATH
            LEFT -> board[r][c + 1] = PATH
            UP -> board[r + 1][c] = PATH
            else -> error("never be here")
        }}
        fun paintBestPaths(node: Node) {
            board[node.r][node.c] = PATH
            if (node != start)
                paintBackDoor(node)
            minScores[node]?.second?.forEach {
                paintBestPaths(it)
            }
        }

        val exitNodes = minScores.filter { it.key.r == exit.first && it.key.c == exit.second }
        val minScoreNodes = exitNodes.filter { it.value.first == exitNodes.minOf { it.value.first } }
        minScoreNodes.forEach {
            paintBestPaths(it.key)
        }

        return board.flatten().count { it == PATH }
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day16_test")
    try {
        //check(part1(testInput) == 7036)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    check(part2(testInput).println() == 45)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day16")
    //part1(input).println()    // 134588
    part2(input).println()  // 631  // 627 is too low
}
