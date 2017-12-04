fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(4).toList()

private fun part1(passphrases: List<String> = getInput()) =
		countValidPassphrases(passphrases)

private fun part2(passphrases: List<String> = getInput()) =
		countValidPassphrases(passphrases, ::sortLetters)

private fun countValidPassphrases(passphrases: List<String>, wordOperation: (String) -> String = { it }) =
		passphrases
				.count {
					it.split(" ")
							.map { wordOperation(it) }
							.let {
								it.count() == it.distinct().count()
							}
				}

private fun sortLetters(string: String) = String(string.toCharArray().sorted().toCharArray())
