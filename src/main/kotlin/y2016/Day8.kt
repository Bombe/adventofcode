package y2016

fun main(args: Array<String>) {
	println(first(BooleanArray(300, { false })))
	println(second(BooleanArray(300, { false })))
}

private val width = 50
private val height = 6
private val letterWidth = 5

private fun first(screen: BooleanArray) = processScreen(screen)
		.filter { it }
		.count()

private fun second(screen: BooleanArray) = processScreen(screen)
		.display()

private fun processScreen(screen: BooleanArray) = getLines()
		.map { "(rect|rotate row|rotate column)[^\\d]+([\\d]+)(?:x| by )([\\d]+)".toRegex().find(it)!!.groupValues.drop(1).let { Pair(it[0], Pair(it[1].toInt(), it[2].toInt())) } }
		.fold(screen.toList()) { screen, instruction ->
			screen.let {
				when (instruction.first) {
					"rect" -> screen.rect(instruction.second.first, instruction.second.second)
					"rotate row" -> screen.rotateRow(instruction.second.first, instruction.second.second)
					"rotate column" -> screen.rotateColumn(instruction.second.first, instruction.second.second)
					else -> throw IllegalArgumentException()
				}
			}
		}

private fun List<Boolean>.rect(a: Int, b: Int) = mapIndexed { index, value ->
	if ((index % width < a) && (index / width < b)) true else value
}

private fun List<Boolean>.rotateColumn(column: Int, offset: Int) = let {
	it.filterIndexed { index, value -> (index % width) == column }.let { oldColumn ->
		it.mapIndexed { index, value ->
			if ((index % width) != column) {
				value
			} else {
				oldColumn[(index / width + height - offset) % height]
			}
		}
	}
}

private fun List<Boolean>.rotateRow(row: Int, offset: Int) = let {
	it.filterIndexed { index, value -> (index / width) == row }.let { oldRow ->
		it.mapIndexed { index, value ->
			if ((index / width) != row) {
				value
			} else {
				oldRow[(index % width + width - offset) % width]
			}
		}
	}
}

private fun List<Boolean>.display() =
		mapIndexed { index, value ->
			(if (value) "\u2588" else " ").let {
				it + (if (index % width == (width - 1)) "\n" else if (index % letterWidth == (letterWidth - 1)) " " else "")
			}
		}.joinToString("")

private fun getLines(day: Int = 8) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
