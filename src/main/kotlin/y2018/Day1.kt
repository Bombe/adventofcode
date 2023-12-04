package y2018

fun main(args: Array<String>) {
	part1().println()
	part2()?.println()
}

private fun part1() = readInput(1)
		.map(Integer::parseInt)
		.sum()

private fun part2() = readInput(1).toList()
		.loop()
		.map(Integer::parseInt)
		.foldWhile(0 to mutableSetOf<Int>()) { change, (currentFrequency, frequencies) ->
			when {
				currentFrequency + change in frequencies -> false to (currentFrequency + change to mutableSetOf())
				else -> true to (currentFrequency + change to frequencies.apply { add(currentFrequency + change) })
			}
		}
		?.first
