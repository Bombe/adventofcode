fun main(args: Array<String>) {
	part1(mapOf(0 to listOf(2), 1 to listOf(1), 2 to listOf(0, 3, 4), 3 to listOf(2, 4), 4 to listOf(2, 3, 6), 5 to listOf(6), 6 to listOf(4, 5))).println()
	part1().println()
	part2(mapOf(0 to listOf(2), 1 to listOf(1), 2 to listOf(0, 3, 4), 3 to listOf(2, 4), 4 to listOf(2, 3, 6), 5 to listOf(6), 6 to listOf(4, 5))).println()
	part2().println()
}

private fun getInput() = readInput(12)
		.mapNotNull { Regex("(.*) <-> (.*)").matchEntire(it) }
		.map { it.groupValues[1].toInt() to it.groupValues[2].split(",").map { it.trim() }.map { it.toInt() } }
		.toMap()

private fun part1(input: Map<Int, List<Int>> = getInput()) =
		generateSequence(listOf<Int>(0) to 0) { (nodes , nextNode) ->
			if ((nextNode >= nodes.size) || nodes[nextNode] !in input) {
				null
			} else {
				(nodes + (input[nodes[nextNode]]!!.subtract(nodes))) to (nextNode +1)
			}
		}.last().first.size

private fun part2(input: Map<Int, List<Int>> = getInput()): Int =
		if (input.isNotEmpty()) {
			generateSequence(listOf(input.keys.first()) to 0) { (nodes, nextNode) ->
				if ((nextNode >= nodes.size) || nodes[nextNode] !in input) {
					null
				} else {
					(nodes + (input[nodes[nextNode]]!!.subtract(nodes))) to (nextNode + 1)
				}
			}.last().first.let { group ->
				1 + part2(input.minus(group))
			}
		} else {
			0
		}
