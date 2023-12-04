package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min

fun main() {
	println(solveDay3Part1(Example::class.readInput(3)))
	println(solveDay3Part2(Example::class.readInput(3)))
}

private fun solveDay3Part1(input: List<String>) =
	input.toGrid().let { grid ->
		grid.findAllPartNumbers()
			.filter { (coordinate, number) ->
				grid.getArea(coordinate.copy(x = coordinate.x - 1, y = coordinate.y - 1), coordinate.copy(x = coordinate.x + number.digits(), y = coordinate.y + 1))
					.dataItems
					.any { it.isSymbol() }
			}
	}
		.sumOf { it.second }

private fun solveDay3Part2(input: List<String>) =
	input.toGrid()
		.findAllGears()
		.sumOf { it.first * it.second }

private fun Grid.findAllPartNumbers() = dataItems.foldIndexed(listOf<Pair<Coordinate, Int>?>(null)) { index, foundNumbers, item ->
	if (!item.isDigit()) {
		if (foundNumbers.lastOrNull() != null) {
			foundNumbers + null
		} else {
			foundNumbers
		}
	} else {
		if (foundNumbers.lastOrNull() == null) {
			foundNumbers + (Coordinate(index % width, index / width) to item.toDigit())
		} else {
			foundNumbers.subList(0, foundNumbers.size - 1) + foundNumbers.last()!!.let { (coordinate, number) -> coordinate to number * 10 + item.toDigit() }
		}
	}
}.filterNotNull()

private fun Grid.findAllGears(): List<Pair<Int, Int>> = findAllPartNumbers().let { allPartNumbers ->
	dataItems.mapIndexedNotNull { index, item ->
		if (!item.isStar()) {
			null
		} else {
			allPartNumbers
				.filter { partLocationAndNumber -> partLocationAndNumber.isAdjacentTo(coordinate(index)) }
				.takeIf { it.size == 2 }
				?.let { it[0].second to it[1].second }
		}
	}
}

private fun List<String>.toGrid() = Grid(joinToString("").toList(), first().length)

private data class Grid(val dataItems: List<Char>, val width: Int)

private val Grid.height get() = dataItems.size / width
private fun Grid.rows() = dataItems.chunked(width)
private fun Grid.getArea(firstCorner: Coordinate, secondCorner: Coordinate): Grid {
	val left = max(min(firstCorner.x, secondCorner.x), 0)
	val right = min(max(firstCorner.x, secondCorner.x), width - 1)
	val top = max(min(firstCorner.y, secondCorner.y), 0)
	val bottom = min(max(firstCorner.y, secondCorner.y), height - 1)
	return rows().filterIndexed { index, _ -> ((index >= top) && (index <= bottom)) }
		.map { row -> row.subList(left, right + 1).joinToString("") }
		.toGrid()
}
private fun Grid.coordinate(position: Int) = Coordinate(position % width, position / width)

private fun Grid.print() {
	println("Grid is ${width}x${height}")
	rows().forEach { println(it.joinToString("")) }
}

private fun Pair<Coordinate, Int>.isAdjacentTo(coordinate: Coordinate) =
	(coordinate.x >= max(first.x - 1, 0)) && (coordinate.x <= (first.x + second.digits())) && (coordinate.y >= max(first.y - 1, 0)) && (coordinate.y <= first.y + 1)

private fun Char.isStar() = this == '*'
private fun Char.isSymbol() = !isDigit() && (this != '.')
private fun Char.toDigit() = code - 48

private fun Int.digits() = floor(ln(this.toDouble()) / ln(10.0)).toInt() + 1

private data class Coordinate(val x: Int, val y: Int)

private class Example {

	@Test
	fun `part 1 can be solved`() {
		assertThat(solveDay3Part1(exampleInput.split("\n")), equalTo(4361))
	}

	@Test
	fun `part 2 can be solved`() {
		assertThat(solveDay3Part2(exampleInput.split("\n")), equalTo(467835))
	}

	private val exampleInput = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598.."""

}
