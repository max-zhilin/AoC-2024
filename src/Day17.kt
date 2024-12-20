import Instruction.*

enum class Instruction(val opcode: Long) {
    ADV(0L),
    BXL(1L),
    BST(2L),
    JNZ(3L),
    BXC(4L),
    OUT(5L),
    BDV(6L),
    CDV(7L);

    companion object {
        infix fun by(opcode: Long): Instruction = entries.firstOrNull { it.opcode == opcode } ?: error("wrong opcode $opcode")
    }
}

fun main() {

    fun part1(input: String): String {

        val (input1, input2) = input.split("\r\n\r\n")
        var (a, b, c) = input1.lines().map { it.replace("""Register .: """.toRegex(), "").toLong() }
        val program = input2.substringAfter("Program: ").split(",").map { it.toLong() }
            .chunked(2) { it[0] to it[1] }

        var ip = 0L
        val output = mutableListOf<Long>()

        fun combo(operand: Long): Long {
            return when (operand) {
                in 0L..3L -> operand
                4L -> a
                5L -> b
                6L -> c
                else -> error("wrong operand")
            }
        }

        fun tick(instr: Instruction, operand: Long) {
            ip++
            when(instr) {
                ADV -> a /= (1L shl combo(operand).toInt())
                BXL -> b = b xor operand
                BST -> b = combo(operand) % 8L
                JNZ -> if (a != 0L) ip = operand
                BXC -> b = b xor c
                OUT -> output.add(combo(operand) % 8L)
                BDV -> b = a / (1L shl combo(operand).toInt())
                CDV -> c = a / (1L shl combo(operand).toInt())
            }
        }
        a = 8177L
        while (ip in program.indices) {
            with(program[ip.toInt()]) {
                val instr = Instruction by first
                tick(instr, second)
            }
        }

        return output.joinToString(",")
    }

    fun part2(input: String): Long {

        val sProgram = input.substringAfter("Program: ")
        val program = sProgram.split(",").map { it.toInt() }

        fun mapOfDigits(): Map<Int, List<Long>> {
            val a = (0..127).toList()
            val b = a.map { it % 8 xor 2 }
            val b1 = a.zip(b).map { (it.first shr it.second) % 8 }
            val b2 = b.zip(b1).map { it.first xor it.second }
            val b3 = b2.map { it xor 7 }
            val out = b3.zip(a).groupBy( { it.first }, { it.second.toLong() } )
            return out
        }

        val mapD = mapOfDigits()
        var init = mapD[program.first()]!!.toMutableList()

        for (i in 1..program.lastIndex) {
            val digit = program[i]
            val out = mapD[digit]!!
            val res = init.flatMap { a -> out.map { b -> a to b } }
                .filter { (a, b) -> b % 16L == a / (1L shl 3 * i) }
            init = res.map { it.second / 16L * 16 * (1L shl 3 * i) + it.first }.toMutableList()
            println("$i ${init.size} ${init.sorted()}")
        }
        return init.min()
    }

    // Read a small test input from the `src/Day??_test.txt` file:
    val testInput = readWholeInput("Day17_test")
    try {
        //check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    } catch (e: IllegalStateException) {
        println("Wrong test: " + part1(testInput))
        throw e
    }
    val test2Input = readWholeInput("Day17_test2")
    //check(part2(test2Input) == 117440)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day17")
    part1(input).println()  // 4,3,7,1,5,3,0,5,4
    part2(input).println()  // 2503166063361039 is too high
}
