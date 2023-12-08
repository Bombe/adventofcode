package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput

fun main() {
	println(solveDay8Part1(Day8Example::class.readInput(8)))
}

private fun solveDay8Part1(input: List<String>) =
	input.filter(String::isNotEmpty)
		.let { it[0] to it.drop(1) }
		.let { (instructions, nodes) ->
			instructions to nodes.associate(String::parseNodes)
		}.let { (instructions, nodes) ->
			var currentNode = "AAA"
			var currentIndex = 0
			while (currentNode != "ZZZ") {
				currentNode = nodes.get(currentNode)!!.let { if (instructions[currentIndex % instructions.length] == 'L') it.first else it.second }
				currentIndex++
			}
			currentIndex
		}

private fun String.parseNodes() = Regex("([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)").matchEntire(this)!!.groups.let { groups -> groups.get(1)!!.value to (groups[2]!!.value to groups[3]!!.value) }

private class Day8Example {

	@Test
	fun `part 1 can be solved`() {
		assertThat(solveDay8Part1(partInput1.split("\n")), equalTo(2))
		assertThat(solveDay8Part1(partInput2.split("\n")), equalTo(6))
	}

	private val partInput1 = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
	""".trimIndent()

	private val partInput2 = """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)"""

}
