package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = countValidTriangles()

private fun second() = countValidTriangles {
	it.fold(FoldResult(emptyList(), emptyList(), emptyList(), emptyList())) { oldFoldResult, columns ->
		getNewFoldResult(oldFoldResult, columns[0], columns[1], columns[2])
	}.results
}

private fun countValidTriangles(triangleProcessor: (List<List<Int>>) -> List<List<Int>> = { it }): Int = getInput(3)
		.map(String::trim)
		.map { it.replace("  *".toRegex(), " ") }
		.map { it.split(" ") }
		.map { it.map(String::toInt) }
		.let { triangleProcessor.invoke(it) }
		.map { it.sorted() }
		.filter { it[0] + it[1] > it[2] }
		.count()

private fun getNewFoldResult(oldFoldResult: FoldResult, first: Int, second: Int, third: Int): FoldResult =
		oldFoldResult.run {
			if (firstColumn.size < 2) {
				FoldResult(results, firstColumn + first, secondColumn + second, thirdColumn + third)
			} else {
				FoldResult(results + listOf(firstColumn + first, secondColumn + second, thirdColumn + third), emptyList(), emptyList(), emptyList())
			}
		}

data class FoldResult(val results: List<List<Int>>, val firstColumn: List<Int>, val secondColumn: List<Int>, val thirdColumn: List<Int>)
