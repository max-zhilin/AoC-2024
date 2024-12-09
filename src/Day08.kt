
fun main() {

    val VOID = '.'
    val MARK = '#'

    fun part1(input: List<String>): Int {

        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        val maxRow = input.size - 1
        val maxCol = input[0].length  - 1
        val board = Array(maxRow + 1) { Array(maxCol + 1) { VOID } }
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char != VOID)
                    if (antennas[char] == null)
                        antennas[char] =  mutableListOf(Pair(r, c))
                    else
                        antennas[char]?.add(Pair(r, c))
            }
        }

        fun mark(ar: Int, ac: Int, br: Int, bc:Int) {
            val rDif = ar - br
            val cDif = ac - bc
            if (br - rDif in 0..maxRow && bc - cDif in 0..maxCol)
                board[br - rDif][bc - cDif] = MARK
            if (ar + rDif in 0..maxRow && ac + cDif in 0..maxCol)
                board[ar + rDif][ac + cDif] = MARK
        }

        antennas.values.forEach { list ->
            list.forEach { a ->
                list.forEach { b ->
                    if (a != b) {
                        mark(a.first, a.second, b.first, b.second)
                    }
                }
            }

        }

        return board.flatten().count { it == MARK }
    }

    fun part2(input: List<String>): Int {

        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        val maxRow = input.size - 1
        val maxCol = input[0].length  - 1
        val board = Array(maxRow + 1) { Array(maxCol + 1) { VOID } }
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                if (char != VOID)
                    if (antennas[char] == null)
                        antennas[char] =  mutableListOf(Pair(r, c))
                    else
                        antennas[char]?.add(Pair(r, c))
            }
        }

        fun mark(ar: Int, ac: Int, br: Int, bc:Int) {
            val rDif = ar - br
            val cDif = ac - bc

            board[ar][ac] = MARK
            board[br][bc] = MARK

            var r = br - rDif
            var c = bc - cDif
            while (r in 0..maxRow && c in 0..maxCol) {
                board[r][c] = MARK
                r -= rDif
                c -= cDif
            }

            r = ar + rDif
            c = ac + cDif
            while (r in 0..maxRow && c in 0..maxCol) {
                board[r][c] = MARK
                r += rDif
                c += cDif
            }
        }

        antennas.values.forEach { list ->
            list.forEach { a ->
                list.forEach { b ->
                    if (a != b) {
                        mark(a.first, a.second, b.first, b.second)
                    }
                }
            }

        }

        return board.flatten().count { it == MARK }
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day08")
    //part1(input).println()
    part2(input).println()
}
