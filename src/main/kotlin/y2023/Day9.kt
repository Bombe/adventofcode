package y2023

import kotlin.math.max
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput

fun main() {
	println(solveDay9Part1(Day9Example::class.readInput(9)))
	println(solveDay9Part2(Day9Example::class.readInput(9)))
}

private fun solveDay9Part1(input: List<String>) =
	input.map { it.split(" ").map(String::toInt) }
		.sumOf { it.predictNextValue() }

private fun solveDay9Part2(input: List<String>) =
	input.map { it.split(" ").map(String::toInt) }
		.sumOf { it.predictPreviousValue() }

private fun List<Int>.predictNextValue(): Int {
	val allDifferences = findAllDifferences()
	var lastDifference = 0
	allDifferences.indices.reversed().drop(1).forEach { row ->
		allDifferences[row] += allDifferences[row].last() + lastDifference
		lastDifference = allDifferences[row].last()
	}
	return allDifferences[0].last()
}

private fun List<Int>.predictPreviousValue(): Int {
	val allDifferences = findAllDifferences()
	var lastDifference = 0
	allDifferences.indices.reversed().drop(1).forEach { row ->
		allDifferences[row].add(0, allDifferences[row].first() - lastDifference)
		lastDifference = allDifferences[row].first()
	}
	return allDifferences[0].first()
}

private fun List<Int>.findAllDifferences(): MutableList<MutableList<Int>> {
	val allDifferences = mutableListOf(this.toMutableList())
	var differences = allDifferences.first()
	while (differences.any { it != 0 }) {
		differences = differences.findDifferences().toMutableList()
		allDifferences += differences
	}
	return allDifferences
}

private fun List<Int>.findDifferences() =
	mapIndexed { index, currentValue -> currentValue - this[max(0, index - 1)] }
		.drop(1)

class Day9Example {

	@Test
	fun `part 1 can be solved`() {
		assertThat(solveDay9Part1(partInput.split("\n")), equalTo(114))
	}

	@Test
	fun `part 2 can be solved`() {
		assertThat(solveDay9Part2(partInput.split("\n")), equalTo(2))
	}

	private val partInput = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""

}
