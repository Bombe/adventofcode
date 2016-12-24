package y2016

import y2015.Permutations.permutate
import java.util.Comparator
import kotlin.comparisons.naturalOrder

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private val cache = mutableMapOf<Pair<Int, Int>, Int>()

private fun first(maze: Map<Pair<Int, Int>, Int> = getMaze()) = calculateShortestPath(maze)
private fun second(maze: Map<Pair<Int, Int>, Int> = getMaze()) = calculateShortestPath(maze, 0)

private fun calculateShortestPath(maze: Map<Pair<Int, Int>, Int>, lastPoint: Int? = null) = (maze.numbers().sorted() - 0)
			.permutate()
			.map { listOf(0, *it.toTypedArray(), lastPoint).filterNotNull() }
			.map {
				it.fold(Pair(-1, 0)) { previousSteps, target ->
					if (previousSteps.first == -1) {
						0 to target
					} else {
						(previousSteps.first + cache.getOrPut(previousSteps.second to target, { maze.distance(maze.getFieldForNumber(previousSteps.second)!!, maze.getFieldForNumber(target)!!) })) to target
					}
				}.first
			}
			.min()!!

private fun <T : Comparable<T>> Collection<T>.permutate(comparator: Comparator<T> = naturalOrder<T>()) = listOf(this, *permutate(this, comparator).toTypedArray())

private fun Map<Pair<Int, Int>, Int>.distance(start: Pair<Int, Int>, goal: Pair<Int, Int>) =
		generateSequence(wallsOnly() + (start to 1)) { it.expandSearchRadius() }
				.map { it.filterKeys { it == goal } }
				.map { it.filterValues { it > 0 } }
				.filter { it.isNotEmpty() }
				.first().entries.first().value - 1

private fun Map<Pair<Int, Int>, Int>.getFieldForNumber(number: Int) = filterValues { it == number }.keys.firstOrNull()
private fun Map<Pair<Int, Int>, Int>.numbers() = filterValues { it >= 0 }.map { it.value }.distinct()
private fun Map<Pair<Int, Int>, Int>.width() = map { it.key.first }.max()!! + 1
private fun Map<Pair<Int, Int>, Int>.height() = map { it.key.second }.max()!! + 1
private fun Map<Pair<Int, Int>, Int>.expandSearchRadius() =
		(entries.sortedByDescending { it.value }.first().value + 1).let { nextStep ->
			this + entries.filter { it.value == nextStep - 1 }
					.map { it.key }
					.flatMap {
						listOf(
								Pair(it.first - 1, it.second),
								Pair(it.first + 1, it.second),
								Pair(it.first, it.second - 1),
								Pair(it.first, it.second + 1)
						)
								.filter { (it.first >= 0) && (it.second >= 0) && (it.first < width()) && (it.second < height()) }
					}.filter { this[it] == 0 }
					.distinct()
					.map { it to nextStep }
		}

private fun Map<Pair<Int, Int>, Int>.wallsOnly() = (0 until height()).flatMap { y -> (0 until width()).map { x -> Pair(x, y) } }
		.associate { Pair(it, if (this[it] == -2) -1 else 0) }

private fun getMaze(day: Int = 24) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.mapIndexed { row, mazeRow ->
			mazeRow.toCharArray().mapIndexed { column, field ->
				Pair(Pair(column, row), when (field) {
					'#' -> -2
					'.' -> -1
					else -> field.toInt() - 48
				})
			}
		}
		.flatMap { it }
		.toMap()
