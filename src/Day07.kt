fun main() {

    fun part1(input: List<String>): Long {

        fun suits(test: Long, acc: Long, list: List<Long>): Boolean {

            val first = list.first()

            if (list.size == 1) {
                return test == acc + first || test == acc * first
            }

            if (test < acc) return false

            if (suits(test, acc + first, list.drop(1))) return true

            if (suits(test, acc * first, list.drop(1))) return true

            return false
        }

        return input.map { line ->
            val (test, eq) = line.split(": ")
            Pair(test.toLong(), eq.split(" ").map { it.toLong() })
        }.filter { suits(it.first, 0L, it.second) }.sumOf { it.first }
    }

    fun part2(input: List<String>): Long {

        fun Long.concat(a: Long): Long = "$this$a".toLong()

        fun suits0(test: Long, acc: Long, operand: Long, list: List<Long>): Boolean {

            val first = list.first()

            if (list.size == 1) {
                return test == acc + operand + first ||
                        test == acc + operand * first ||
                        test == acc * operand + first ||
                        test == acc * operand * first ||
                        test == acc + operand * first ||
                        test == acc + operand.concat(first) ||
                        test == acc * operand.concat(first)
            }

            if (test < acc + operand) return false

            // next + or *
            if (suits0(test, acc + operand + first, 0L, list.drop(1))) return true
            if (suits0(test, acc + operand * first, 0L, list.drop(1))) return true
            if (suits0(test, acc * operand + first, 0L, list.drop(1))) return true
            if (suits0(test, acc * operand * first, 0L, list.drop(1))) return true

            // next ||
            if (suits0(test, acc, operand.concat(first), list.drop(1))) return true

            return false
        }

        fun suits(test: Long, acc: Long, list: List<Long>): Boolean {

            val first = list.first()

            if (list.size == 1) {
                return test == acc + first || test == acc * first || test == acc.concat(first)
            }

            if (test < acc) return false

            if (suits(test, acc + first, list.drop(1))) return true

            if (suits(test, acc * first, list.drop(1))) return true

            if (suits(test, acc.concat(first), list.drop(1))) return true

            return false
        }

        return input.map { line ->
            val (test, eq) = line.split(": ")
            Pair(test.toLong(), eq.split(" ").map { it.toLong() })
        }
            .filter { suits(it.first, 0L, it.second) }
            //.println()
            .sumOf { it.first }
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
