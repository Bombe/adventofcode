fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(9).first()

private fun String.cleanGarbage() =
		fold(GarbageCleaner()) { (noneGarbage, garbage, inGarbage, ignoreNext), current ->
			when {
				ignoreNext -> GarbageCleaner(noneGarbage, garbage, inGarbage, false)
				inGarbage and (current == '!') -> GarbageCleaner(noneGarbage, garbage, inGarbage, true)
				inGarbage and (current == '>') -> GarbageCleaner(noneGarbage, garbage, false, false)
				inGarbage -> GarbageCleaner(noneGarbage, garbage.apply { add(current) }, inGarbage, ignoreNext)
				current == '<' -> GarbageCleaner(noneGarbage, garbage, true, false)
				else -> GarbageCleaner(noneGarbage.apply { add(current) }, garbage, false, false)
			}
		}

private data class GarbageCleaner(val noneGarbage: MutableList<Char> = mutableListOf(), val garbage: MutableList<Char> = mutableListOf(), val inGargabe: Boolean = false, val ignoreNext: Boolean = false)

private fun part1() =
		getInput()
				.cleanGarbage()
				.noneGarbage.joinToString("")
				.fold(0 to 0) { (currentLevel, sum), current ->
					when (current) {
						'{' -> currentLevel + 1 to sum
						'}' -> currentLevel - 1 to (sum + currentLevel)
						else -> currentLevel to sum
					}
				}.second

private fun part2() =
		getInput()
				.cleanGarbage()
				.garbage.size
