fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(5).map(String::toInt).toList()

private fun part1(jumps: List<Int> = getInput()) =
		processList(jumps, { it + 1 })

private fun part2(jumps: List<Int> = getInput()) =
		processList(jumps, { if (it >= 3) it - 1 else it + 1 })

private fun processList(jumps: List<Int>, jumpModify: (Int) -> Int) =
		generateSequence(0 to jumps.toMutableList()) { (position, jumps) ->
			if ((position < 0) || (position >= jumps.size)) {
				null
			} else {
				(position + jumps[position]) to jumps.apply { set(position, get(position).let(jumpModify)) }
			}
		}.toList().size - 1
