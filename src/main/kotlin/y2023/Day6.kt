package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.math.max

fun main() {
	println(solveDay6Part1(Day6Example::class.readInput(6)))
	println(solveDay6Part2(Day6Example::class.readInput(6)))
}

private fun solveDay6Part1(input: List<String>) =
	input.let {
		it[0].split(":")[1].split(Regex(" +")).filterNot(String::isBlank).map(String::toLong).let { times ->
			it[1].split(":")[1].split(Regex(" +")).filterNot(String::isBlank).map(String::toLong).let { distances ->
				times.zip(distances)
			}
		}
	}.let { races ->
		races
			.map { it.findWaysToWin() }
			.reduce { acc, waysToWin -> acc * waysToWin }
	}

private fun solveDay6Part2(input: List<String>) =
	input.let {
		it[0].split(":")[1].replace(Regex(" +"), "").toLong().let { time ->
			it[1].split(":")[1].replace(Regex(" +"), "").toLong().let { distance ->
				(time to distance).findWaysToWin()
			}
		}
	}

private fun Pair<Long, Long>.findWaysToWin() = (1..first).count { distanceRaced(it, first) > second }.toLong()

private fun distanceRaced(holdTime: Long, raceTime: Long) = max(raceTime - holdTime, 0) * holdTime

private class Day6Example {

	@Test
	fun `part 1 can be solved`() {
		assertThat(solveDay6Part1(part1Input.split("\n")), equalTo(288L))
	}

	@Test
	fun `part 2 can be solved`() {
		assertThat(solveDay6Part2(part1Input.split("\n")), equalTo(71503L))
	}

	private val part1Input = """Time:      7  15   30
Distance:  9  40  200"""

}
