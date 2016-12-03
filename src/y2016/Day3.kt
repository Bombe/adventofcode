package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

fun first() = getInput(3)
		.map(String::trim)
		.map { it.replace("  *".toRegex(), " ") }
		.map { it.split(" ") }
		.map { it.map(String::toInt) }
		.map { it.sorted() }
		.filter { it[0] + it[1] > it[2] }
		.count()

fun second() = getInput(3)
		.map(String::trim)
		.map { it.replace("  *".toRegex(), " ") }
		.map { it.split(" ") }
		.map { it.map(String::toInt) }
		.fold(FoldResult(emptyList(), Triplet<List<Int>>(emptyList(), emptyList(), emptyList()))) { oldFoldResult, columns ->
			getNewFoldResult(oldFoldResult, columns[0], columns[1], columns[2])
		}.results
		.map { it.sorted() }
		.filter { it[0] + it[1] > it[2] }
		.count()

fun getNewFoldResult(oldFoldResult: FoldResult, first: Int, second: Int, third: Int): FoldResult =
		oldFoldResult.run {
			if (currentTriangle.first.size < 2) {
				FoldResult(results, Triplet(currentTriangle.first + first, currentTriangle.second + second, currentTriangle.third + third))
			} else {
				FoldResult(results + listOf(currentTriangle.first + first, currentTriangle.second + second, currentTriangle.third + third), Triplet(emptyList(), emptyList(), emptyList()))
			}
		}

data class FoldResult(val results: List<List<Int>>, val currentTriangle: Triplet<List<Int>>)

data class Triplet<out T>(val first: T, val second: T, val third: T)
