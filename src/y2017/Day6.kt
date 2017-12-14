package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(6).first().split("\t").map(String::toInt)

private fun part1(memoryBanks: List<Int> = getInput()) =
		getFirstKnownMemoryState(memoryBanks).first

private fun part2(memoryBanks: List<Int> = getInput()) =
		getFirstKnownMemoryState(memoryBanks).let { (index, memoryBank, knownMemoryBanks) ->
			index - knownMemoryBanks.indexOf(memoryBank)
		}

private fun getFirstKnownMemoryState(memoryBanks: List<Int>) =
		generateSequence(Triple(0, memoryBanks.toMutableList(), mutableListOf<List<Int>>())) { (index, memoryBanks, knownMemoryBanks) ->
			if (memoryBanks in knownMemoryBanks) {
				null
			} else {
				knownMemoryBanks += memoryBanks.toList()
				val highestIndex = memoryBanks.withIndex().maxBy { it.value }!!.index
				val highestValue = memoryBanks[highestIndex]
				Triple(index + 1, memoryBanks.apply {
					memoryBanks[highestIndex] = 0
					(0 until memoryBanks.size)
							.forEachIndexed { index, item ->
								((highestIndex + index + 1) % memoryBanks.size).let { newIndex ->
									memoryBanks[newIndex] = memoryBanks[newIndex] + (highestValue / memoryBanks.size) + if (index < (highestValue % memoryBanks.size)) 1 else 0
								}
							}
				}, knownMemoryBanks)
			}
		}.last()
