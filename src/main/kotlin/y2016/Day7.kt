package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = getLines()
		.map { it.split('[', ']') }
		.fold(emptyList<Pair<List<String>, List<String>>>()) { list, address ->
			list + Pair(address.filterIndexed { index, part -> index % 2 == 0 }, address.filterIndexed { index, part -> index % 2 != 0 })
		}
		.filter { it.first.any(String::autonomousBridgeBypassAnnotation) }
		.filterNot { it.second.any(String::autonomousBridgeBypassAnnotation) }
		.count()

private val String.autonomousBridgeBypassAnnotation: Boolean
	get() = matchRegexWithDifferentLetters("(.)(.)\\2\\1".toRegex()).isNotEmpty()

private fun String.matchRegexWithDifferentLetters(regex: Regex) = (0..length).flatMap {
	regex.findAll(this, it).filter {
		it.groupValues[1] != it.groupValues[2]
	}.toList()
}.distinct()

private fun second() = getLines()
		.map { it.split('[', ']') }
		.filter {
			it.byteAllocationBlocks().map(String::reverse).intersect(it.areaBroadcastAccessors()).isNotEmpty()
		}
		.count()

private fun List<String>.areaBroadcastAccessors() = this.findThreeLetterBlocks { (it % 2) == 0 }
private fun List<String>.byteAllocationBlocks() = this.findThreeLetterBlocks { (it % 2) != 0 }

private fun List<String>.findThreeLetterBlocks(byIndex: (Int) -> Boolean) =
		filterIndexed { index, supernet -> byIndex(index) }
				.flatMap { it.matchRegexWithDifferentLetters("(.)(.)\\1".toRegex()).map { it.value } }

private fun String.reverse() = "${this[1]}${this[0]}${this[1]}"

private fun getLines(day: Int = 7) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
