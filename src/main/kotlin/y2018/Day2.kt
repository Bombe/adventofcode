package y2018

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(2)
		.map { it.toCharArray() }
		.map { it.groupBy { it } }
		.map { it.entries.map { (key, value) -> key to value.size } }
		.map { it.any { it.second == 2 } to it.any { it.second == 3 } }
		.fold(0 to 0) { (countTwos, countThrees), (foundTwo, foundThree) ->
			(countTwos + if (foundTwo) 1 else 0) to (countThrees + if (foundThree) 1 else 0)
		}
		.let { it.first * it.second }


private fun part2() = readInput(2).toList()
		.let { input ->
			input.flatMap { it.matchesClosely(input) }
		}
		.distinct()

private fun String.matchesClosely(codes: List<String>) =
		codes.map { findCommonLetters(it, this) }
				.filter { it.size == (this.length - 1) }
				.map { it.joinToString("") }

private fun findCommonLetters(code: String, wantedCode: String) =
		code.mapIndexedNotNull { index: Int, c: Char ->
			c.takeIf { code[index] == wantedCode[index] }
		}
