package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = recombineLetters { it.maxBy { it.value }!! }
private fun second() = recombineLetters { it.minBy { it.value }!! }

private fun recombineLetters(sorter: (Map<Char, Int>) -> Map.Entry<Char, Int>) = getInput()
		.fold(mapOf<Int, List<Char>>()) { map, current ->
			map + current.toCharArray().mapIndexed { index, char ->
				index to map.getOrElse(index, { emptyList() }) + char
			}
		}
		.toSortedMap()
		.map {
			it.value.groupBy { it }
					.mapValues { it.value.size }
					.let(sorter).key
		}
		.joinToString("")

private fun getInput(day: Int = 6) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
