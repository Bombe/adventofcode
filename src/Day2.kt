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

private fun <T> List<T>.findPairs(pairDetector: (T, T) -> Boolean): List<Pair<T, T>> {
	val pairs = mutableListOf<Pair<T, T>>()
	for ((index, value) in dropLast(1).withIndex()) {
		drop(index + 1)
				.filter { pairDetector(value, it) }
				.map { value to it }
				.forEach { pairs.add(it) }
	}
	return pairs
}
