package y2017

fun main(args: Array<String>) {
	part1(mapOf(0 to 3, 1 to 2, 4 to 4, 6 to 4)).println()
	part1().println()
	part2(mapOf(0 to 3, 1 to 2, 4 to 4, 6 to 4)).println()
	part2().println()
}

private fun getInput() = readInput(13)
		.map { it.split(':') }
		.map { it.map(String::trim) }
		.map { it.map(String::toInt) }
		.map { it[0] to it[1] }
		.toMap()

private fun part1(firewall: Map<Int, Int> = getInput()) =
		firewall.entries
				.filter { (layer, _) -> firewall.hitScanner(layer) }
				.map { (layer, depth) -> layer * depth }
				.sum()

private fun part2(firewall: Map<Int, Int> = getInput()) =
		generateSequence(0 to isDetected(firewall, 0)) { (delay, _) ->
			(delay + 1) to isDetected(firewall, delay + 1)
		}
				.first { (_, detected) -> !detected }
				.first

private fun Map<Int, Int>.hitScanner(layer: Int, delay: Int = 0)
		= (layer + delay) % ((get(layer)!! - 1) * 2) == 0

private fun isDetected(firewall: Map<Int, Int>, delay: Int = 0) =
		firewall.keys
				.any { layer -> firewall.hitScanner(layer, delay) }
