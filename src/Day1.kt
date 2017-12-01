fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() =
		readInput(1)
				.first()
				.map { it.toInt() - 48 }
				.solve(1)

private fun part2() =
		readInput(1)
				.first()
				.map { it.toInt() - 48 }
				.let { it.solve(it.size / 2) }

private fun List<Int>.solve(rotate: Int) =
		rotate(rotate).let { rotated ->
			filterIndexed { index, element ->
				rotated[index] == element
			}
		}.sum()

private fun <T> List<T>.rotate(steps: Int) =
		subList(steps, size) + subList(0, steps)
