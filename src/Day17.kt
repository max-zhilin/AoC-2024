import Instruction.*

enum class Instruction(val opcode: Int) {
    ADV(0),
    BXL(1),
    BST(2),
    JNZ(3),
    BXC(4),
    OUT(5),
    BDV(6),
    CDV(7);

    companion object {
        infix fun by(opcode: Int): Instruction = entries.firstOrNull { it.opcode == opcode } ?: error("wrong opcode $opcode")
    }
}

fun main() {

    fun part1(input: String): String {

        val (input1, input2) = input.split("\r\n\r\n")
        var (a, b, c) = input1.lines().map { it.replace("""Register .: """.toRegex(), "").toInt() }
        val program = input2.substringAfter("Program: ").split(",").map { it.toInt() }
            .chunked(2) { it[0] to it[1] }

        var ip = 0
        var output = mutableListOf<Int>()

        fun combo(operand: Int): Int {
            return when (operand) {
                in 0..3 -> operand
                4 -> a
                5 -> b
                6 -> c
                else -> error("wrong operand")
            }
        }

        fun tick(instr: Instruction, operand: Int) {
            ip++
            when(instr) {
                ADV -> a /= (1 shl combo(operand))
                BXL -> b = b xor operand
                BST -> b = combo(operand) % 8
                JNZ -> if (a != 0) ip = operand
                BXC -> b = b xor c
                OUT -> output.add(combo(operand) % 8)
                BDV -> b = a / (1 shl combo(operand))
                CDV -> c = a / (1 shl combo(operand))
            }
        }

        while (ip in program.indices) {
            with(program[ip]) {
                val instr = Instruction by first
                tick(instr, second)
            }
        }

        return output.joinToString(",")
    }

    fun part2(input: String): Int {

        val (input1, input2) = input.split("\r\n\r\n")
        var (a, b, c) = input1.lines().map { it.replace("""Register .: """.toRegex(), "").toInt() }
        val sProgram = input2.substringAfter("Program: ")
        val program = sProgram.split(",").map { it.toInt() }
            .chunked(2) { it[0] to it[1] }

        var ip = 0
        var output = mutableListOf<Int>()

        fun combo(operand: Int): Int {
            return when (operand) {
                in 0..3 -> operand
                4 -> a
                5 -> b
                6 -> c
                else -> error("wrong operand")
            }
        }

        fun tick(instr: Instruction, operand: Int) {
            ip++
            when(instr) {
                ADV -> a /= (1 shl combo(operand))
                BXL -> b = b xor operand
                BST -> b = combo(operand) % 8
                JNZ -> if (a != 0) ip = operand
                BXC -> b = b xor c
                OUT -> output.add(combo(operand) % 8)
                BDV -> b = a / (1 shl combo(operand))
                CDV -> c = a / (1 shl combo(operand))
            }
        }

        for (i in 0..Int.MAX_VALUE) {
            a = i
            b = 0
            c = 0
            ip = 0
            output = mutableListOf()
            var counter = 0
            while (ip in program.indices) {
                counter++
                with(program[ip]) {
                    val instr = Instruction by first
                    tick(instr, second)
                }
            }
            if (i % 100_000 == 0) println("$i $counter")
            if (output.joinToString(",") == sProgram)
                return i
        }
        return -1
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
    check(part2(test2Input) == 117440)

    // Read the input from the `src/Day??.txt` file.
    val input = readWholeInput("Day17")
    //part1(input).println()
    part2(input).println()
}
