fun main() {

    fun precedes(a: Int, b: Int, rules: Map<Int, Set<Int>>): Boolean {
        return if (b in rules)
            if (rules[b]!!.contains(a))
                true
            else
                rules[b]!!.any { precedes(a, it, rules) }
        else
            a !in rules //false
    }

    fun rightOrder(pages: List<Int>, rules: Map<Int, Set<Int>>): Boolean {
        return pages.zipWithNext().all { (a, b) ->
            precedes(a, b, rules)
        }
    }

    // Wrong solution
    fun part0(input: List<String>): Int {
        var sum = 0
        val breakLine = input.indexOf("")
        val rulesList = input.subList(0, breakLine).map { it.split("|").let { it.first().toInt() to it.last().toInt() } }
        val rules: Map<Int, Set<Int>> = rulesList.groupingBy { it.second }.aggregate<Pair<Int, Int>, Int, Set<Int>> { _, accumulator, element, first ->
            if (first)
                setOf(element.first)
            else
                accumulator!!.plus(element.first)
        }
        val updates = input.subList(breakLine + 1, input.lastIndex + 1).map { it.split(",").map { it.toInt() } }
        updates.forEach { pages: List<Int> ->
            if (rightOrder(pages, rules)) {
                println(pages)
                sum += pages[pages.lastIndex / 2]
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val breakLine = input.indexOf("")
        val rulesList = input.subList(0, breakLine).map { it.split("|").let { it.first().toInt() to it.last().toInt() } }
        val rules: Map<Int, Set<Int>> = rulesList.groupingBy { it.second }.aggregate<Pair<Int, Int>, Int, Set<Int>> { _, accumulator, element, first ->
            if (first)
                setOf(element.first)
            else
                accumulator!!.plus(element.first)
        }
        val updates = input.subList(breakLine + 1, input.lastIndex + 1).map { it.split(",").map { it.toInt() } }

        updates.forEach { pages: List<Int> ->
            var broken = false
            for (i in 0 until pages.lastIndex)
                for (j in i + 1..pages.lastIndex) {
                    val a = pages[i]
                    val b: Int = pages[j]
                    if (rules[b] == null) {
                        //println(b)
                        broken = true
                    } else
                        if (!rules[b]!!.contains(a)) broken = true
                }
            if (!broken) sum += pages[pages.lastIndex / 2]
        }

        return sum
    }

    fun part11(input: List<String>): Int {
        var sum = 0
        val breakLine = input.indexOf("")
        val rulesList = input.subList(0, breakLine).map { it.split("|").let { it.first().toInt() to it.last().toInt() } }
        val rules: Map<Int, Set<Int>> = rulesList.groupingBy { it.second }.aggregate<Pair<Int, Int>, Int, Set<Int>> { _, accumulator, element, first ->
            if (first)
                setOf(element.first)
            else
                accumulator!!.plus(element.first)
        }
        val updates = input.subList(breakLine + 1, input.lastIndex + 1).map { it.split(",").map { it.toInt() } }

        updates.forEach { pages: List<Int> ->
            if (pages.sortedWith { a, b -> if (rules[b]?.contains(a) == true) -1 else 1 } == pages)
                sum += pages[pages.lastIndex / 2]
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val breakLine = input.indexOf("")
        val rulesList = input.subList(0, breakLine)
        val updates = input.subList(breakLine + 1, input.lastIndex + 1).map { it.split(",").map { it.toInt() } }

        val withSorted = updates.map { pages: List<Int> ->
            pages to pages.sortedWith { a, b -> if (rulesList.contains("$a|$b")) -1 else 1 }
        }
        return withSorted.filterNot { it.first == it.second }.sumOf { it.second[it.second.lastIndex / 2]}
    }

    // Or read a large test input from the `src/Day??_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part11(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day??.txt` file.
    val input = readInput("Day05")
    part1(input).println() //10885 too high, 4569 is right
    part11(input).println()
    part2(input).println()
}
