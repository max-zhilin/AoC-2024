
fun main() {

    val VOID = -1

    fun part1(input: List<String>): Long {

        val layout = input[0].map { it.digitToInt() }
        val disk = Array(layout.size * 9) { VOID }
        var right = 0
        var id = 0
        layout.chunked(2).forEach { pair ->
            repeat(pair.first()) {
                disk[right++] = id
            }
            repeat(pair.last()) {
                right++
            }
            id++
        }

        var left = 0
        do {
            while (disk[left] != VOID) left++
            while (disk[right] == VOID) right--
            while (left < right && disk[left] == VOID && disk[right] != VOID) {
                disk[left] = disk[right]
                disk[right] = VOID
                left++
                right--
            }
        } while(left < right)

        var sum = 0L
        disk.forEachIndexed { index, i ->
            sum += if (i != VOID) index * i else 0
        }
        return sum
    }

    fun part2(input: List<String>): Long {

        var id = 0
        val layout = input[0].mapIndexed { index, c ->
            if (index % 2 == 0)
                id++ to c.digitToInt()
            else
                VOID to c.digitToInt()
        }.toMutableList()

        while(id-- > 0) {
            val right = layout.indexOfFirst { it.first == id }
            val fileSize = layout[right].second
            val left = layout.indexOfFirst { it.first == VOID && it.second >= fileSize }
            if (left != -1 && left < right) {
                val freeSize = layout[left].second
                layout[left] = layout[right]
                layout[right] = VOID to fileSize
                if (freeSize > fileSize)
                    layout.add(left + 1, VOID to freeSize - fileSize)
            }
        }

        var sum = 0L
        var pos = 0L    // this must be Long too
        layout.forEach {
            sum += if (it.first != VOID) it.first * (2 * pos + it.second - 1) * it.second / 2 else 0
            pos += it.second
        }
        return sum
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day09")
    //part1(input).println()  // 554328789 is bad
    part2(input).println()  // 1584780768280 too low (must use Long in sum operands)
}
