
fun main() {

    fun part1(input: List<String>): Int {

        val stones = input[0].split(" ").map { it.toLong() }.toMutableList()
        repeat(25) {
            for (i in stones.lastIndex downTo 0) {
                when {
                    stones[i] == 0L -> stones[i] = 1
                    stones[i].toString().length % 2 == 0 -> {
                        val str = stones[i].toString()
                        stones[i] = str.take(str.length / 2).toLong()
                        stones.add(i + 1, str.takeLast(str.length / 2).toLong())
                    }
                    else -> stones[i] *= 2024L
                }
            }
            println(stones.size)
        }
        return stones.size
    }

    fun part2(input: List<String>, blinks: Int): Long {
        val cache = mutableMapOf<Pair<Long, Int>, Long>()

        fun calc(a: Long, depth: Int): Long {

            if (depth == 0) return 1

            return cache[Pair(a, depth)] ?: when {
                a == 0L -> calc(1, depth - 1)
                a.toString().length % 2 == 0 -> {
                    val str = a.toString()
                    calc(str.take(str.length / 2).toLong(), depth - 1) +
                    calc(str.takeLast(str.length / 2).toLong(), depth - 1)
                }
                else -> calc(a * 2024L, depth - 1)
            }.also { cache[Pair(a, depth)] = it }
        }

        val stones = input[0].split(" ").map { it.toLong() }

//        for(i in 1..blinks) {
//            stones.sorted().sumOf { calc(it, i) }.also { if(it < 0 || i == 47) println("$i $it") }
//        }
        return stones.sumOf { calc(it, blinks) }//.also { println(cache.size) }
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day11_test")
    //check(part1(testInput) == 55312)
    check(part2(testInput, 25) == 55312L)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day11")
    //part1(input).println()  // 199753
    part2(input, 75).println()  // 239413123020116
}
