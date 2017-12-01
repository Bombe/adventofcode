fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() =
		readInput(1)
				.first()
				.map { it.toInt() - 48 }
				.let { it + it[0] }
				.let { input ->
					input.drop(1).fold(0 to input[0]) { (sum, last), current ->
						if (current == last) {
							(sum + current) to current
						} else {
							sum to current
						}
					}.first
				}

private fun part2() =
		readInput(1)
				.first()
				.map { it.toInt() - 48 }
				.let {
					it.subList(0, it.size / 2).filterIndexed { index, digit ->
						it.subList(it.size / 2, it.size)[index] == digit
					}.sum() * 2
				}
