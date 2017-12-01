fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(1)
		.first()
		.map { it.toInt() - 48 }

private fun part1() =
		getInput()
				.solve(1)

private fun part2() =
		getInput()
				.let { it.solve(it.size / 2) }

private fun List<Int>.solve(rotate: Int) =
		rotate(rotate).let { rotated ->
			filterIndexed { index, element ->
				rotated[index] == element
			}
		}.sum()

private fun <T> List<T>.rotate(steps: Int) =
		subList(steps, size) + subList(0, steps)
