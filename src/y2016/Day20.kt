package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(blacklist: List<Pair<Long, Long>> = getLines()) =
		blacklist.first().second + 1

private fun second(blacklist: List<Pair<Long, Long>> = getLines()) =
		(1L shl 32) - blacklist.map { it.second - it.first + 1 }.sum()

private fun getLines(day: Int = 20) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.map { it.split('-') }
		.map { it.map(String::toLong) }
		.map { it.sorted() }
		.map { it[0] to it[1] }
		.sortedBy { it.first }
		.fold(emptyList<Pair<Long, Long>>()) { ranges, range ->
			if (ranges.isEmpty() || (ranges.last().second < (range.first - 1))) {
				ranges + range
			} else {
				ranges.last().let { lastRange ->
					ranges.dropLast(1) + (lastRange.first to Math.max(lastRange.second, range.second))
				}
			}
		}
