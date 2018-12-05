package y2018

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(5).single()
		.toCharArray()
		.toList()
		.react()
		.size

private fun part2() = readInput(5).single()
		.toCharArray()
		.toList()
		.react()
		.let { polymer ->
			polymer
					.map(Char::toLowerCase)
					.distinct()
					.map { it to polymer.remove(it).react() }
					.minBy { it.second.size }!!
					.second
		}
		.size

private fun List<Char>.react() =
		fold(emptyList<Char>()) { polymer, unit ->
			if (polymer.lastOrNull() reactsWith unit) {
				polymer.dropLast(1)
			} else {
				polymer + unit
			}
		}

private infix fun Char?.reactsWith(other: Char) =
		(this != null) &&
				(this != other) &&
				((this.toInt() and 32.inv()) == (other.toInt() and 32.inv()))

private fun List<Char>.remove(unit: Char) =
		filterNot { it.toLowerCase() reactsWith unit.toUpperCase() }
