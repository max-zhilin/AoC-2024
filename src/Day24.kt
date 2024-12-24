import Operation.*

enum class Operation {
    AND,
    OR,
    XOR,
}

fun main() {

    data class Gate(val first: String, val op: Operation, val second: String) {
        var value: Int? = null
    }

    fun part1(input: List<String>): Long {

        val wires = input.takeWhile { it.isNotEmpty() }.associateBy({ it.take(3) }, { it.last().digitToInt() })
        val gates = input.takeLastWhile { it.isNotEmpty() }.associateBy({ it.takeLast(3) }, {
            // tnw OR pbm -> gnj
            val (first, op, second) = it.substringBefore(" ->").split(" ")
            Gate(first, Operation.valueOf(op), second)
        })

        fun Gate.getValue(): Int {
            if (value == null) {
                val firstValue = wires[first] ?: gates[first]!!.getValue()
                val secondValue = wires[second] ?: gates[second]!!.getValue()
                value = when(op) {
                    AND -> firstValue and secondValue
                    OR -> firstValue or secondValue
                    XOR -> firstValue xor secondValue
                }
            }
            return value!!
        }

        var i = 0
        var result = 0L
        while (gates.containsKey(String.format("z%02d", i))) {
            result += gates[String.format("z%02d", i)]!!.getValue().toLong() shl i
            i++
        }

        return result
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day24_test")
    try {
        check(part1(testInput) == 2024L)
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    //check(part2(testInput) == 2024)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day24")
    part1(input).println()  // 56729630917616 // 1702885256(Int) too low
    //part2(input).println()
}
