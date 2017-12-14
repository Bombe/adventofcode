package y2017

import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() =
		readInput(2)
				.map { it.split(Regex("[\t ]+")) }
				.map { it.map { it.toInt() } }

private fun part1() = getInput()
		.map { it.max()!! - it.min()!! }
		.sum()

private fun part2() = getInput()
		.map { numbers ->
			numbers
					.findPairs { first, second -> min(first, second) * (max(first, second) / min(first, second)) == max(first, second) }
					.first()
					.let { max(it.first, it.second) / min(it.first, it.second) }
		}
		.sum()

private fun <T> List<T>.findPairs(pairDetector: (T, T) -> Boolean) =
		dropLast(1)
				.mapIndexed { index, value ->
					drop(index + 1).map { value to it }
				}
				.flatMap { it }
				.filter { pairDetector(it.first, it.second) }
