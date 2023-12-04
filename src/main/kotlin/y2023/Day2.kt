package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput
import y2021.readInput
import kotlin.math.max

fun solveDay2Part1(input: List<String>) =
	input
		.map(String::toGame)
		.filter { game -> game.cubeSets.none { cubeSet -> (cubeSet.red > 12) || (cubeSet.green > 13) || (cubeSet.blue > 14) } }
		.map(Game::id)
		.sum()

fun solveDay2Part2(input: List<String>) =
	input
		.map(String::toGame)
		.map { game -> game.cubeSets.reduce { minimumCubeSet, cubeSet -> CubeSet(max(minimumCubeSet.red, cubeSet.red), max(minimumCubeSet.green, cubeSet.green), max(minimumCubeSet.blue, cubeSet.blue)) } }
		.map(CubeSet::power)
		.sum()

fun main() {
	println(solveDay2Part1(Day2Test::class.readInput(2)))
	println(solveDay2Part2(Day2Test::class.readInput(2)))
}

private fun String.toGame() = split(':')
	.let { sets ->
		sets[1].split(';')
			.map { set ->
				set.split(',')
					.map(String::trim)
					.map { cubeCount ->
						cubeCount.split(' ').let {
							it[1] to it[0].toInt()
						}
					}.toCubeSet()
			}.let {
				Game(sets[0].split(' ')[1].toInt(), it)
			}
	}

private fun Collection<Pair<String, Int>>.toCubeSet() = toMap()
	.let { cubeCountMap ->
		CubeSet(cubeCountMap["red"] ?: 0, cubeCountMap["green"] ?: 0, cubeCountMap["blue"] ?: 0)
	}

private fun CubeSet.power() = red * green * blue

private data class Game(val id: Int, val cubeSets: List<CubeSet>)
private data class CubeSet(val red: Int, val green: Int, val blue: Int)

private class Day2Test {

	@Test
	fun `part 1 can be solved`() {
		val part1Input = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"""
		assertThat(solveDay2Part1(part1Input.split("\n")), equalTo(8))
	}

	@Test
	fun `part 2 can be solved`() {
		val part2Input = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"""
		assertThat(solveDay2Part2(part2Input.split("\n")), equalTo(2286))
	}

}
