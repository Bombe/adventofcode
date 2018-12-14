package y2018

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() = readInput(14).single()
		.let(Integer::parseInt)
		.let { iterations ->
			createRecipes({ it.size >= iterations + 10 }, { it.subList(iterations, iterations + 10).joinToString("") })
		}

private fun part2() = readInput(14).single()
		.let { it.toCharArray().map(Char::toInt).map { it - 48 } }
		.let { digits ->
			createRecipes({ it.endsWith(digits) }, { it.size - digits.size })
		}

private fun <R> createRecipes(finished: (List<Int>) -> Boolean, result: (List<Int>) -> R): R =
		mutableListOf(3, 7).also { recipes ->
			var firstElf = 0
			var secondElf = 1
			while (true) {
				val nextRecipe = recipes[firstElf] + recipes[secondElf]
				if (nextRecipe >= 10) {
					recipes += nextRecipe / 10
					if (finished(recipes)) {
						break
					}
				}
				recipes += nextRecipe % 10
				if (finished(recipes)) {
					break
				}
				firstElf = (firstElf + 1 + recipes[firstElf]) % recipes.size
				secondElf = (secondElf + 1 + recipes[secondElf]) % recipes.size
			}
		}.let(result)
