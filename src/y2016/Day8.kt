package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = getLines()
		.map { "(rect|rotate row|rotate column)[^\\d]+([\\d]+)(?:x| by )([\\d]+)".toRegex().find(it)!!.groupValues.drop(1).let { Pair(it[0], Pair(it[1].toInt(), it[2].toInt())) } }
		.fold(Screen()) { screen, instruction ->
			screen.apply {
				when (instruction.first) {
					"rect" -> rect(instruction.second.first, instruction.second.second)
					"rotate row" -> rotateRow(instruction.second.first, instruction.second.second)
					"rotate column" -> rotateColumn(instruction.second.first, instruction.second.second)
				}
			}
		}
		.count()

class Screen {
	private val width = 50
	private val height = 6
	private val letterWidth = 5
	private val pixels = BooleanArray(width * height)
	fun rect(a: Int, b: Int) {
		(0..(a - 1)).forEach { column ->
			(0..(b - 1)).forEach { row ->
				pixels[row * width + column] = true
			}
		}
	}

	fun rotateColumn(column: Int, offset: Int) {
		pixels
				.filterIndexed { index, value -> (index % width) == column }
				.forEachIndexed { index, value -> pixels[((index + offset) * width + column) % (width * height)] = value }
	}

	fun rotateRow(row: Int, offset: Int) {
		pixels
				.filterIndexed { index, value -> (index / width) == row }
				.forEachIndexed { index, value -> pixels[row * width + ((index + offset) % width)] = value }
	}

	fun count() = pixels.count { it }

	override fun toString() =
			pixels.mapIndexed { index, value ->
				(if (value) "\u2588" else " ").let {
					it + (if (index % width == (width - 1)) "\n" else if (index % letterWidth == (letterWidth - 1)) " " else "")
				}
			}.joinToString("")
}

private fun second() = getLines()
		.map { "(rect|rotate row|rotate column)[^\\d]+([\\d]+)(?:x| by )([\\d]+)".toRegex().find(it)!!.groupValues.drop(1).let { Pair(it[0], Pair(it[1].toInt(), it[2].toInt())) } }
		.fold(Screen()) { screen, instruction ->
			screen.apply {
				when (instruction.first) {
					"rect" -> rect(instruction.second.first, instruction.second.second)
					"rotate row" -> rotateRow(instruction.second.first, instruction.second.second)
					"rotate column" -> rotateColumn(instruction.second.first, instruction.second.second)
				}
			}
		}

private fun getLines(day: Int = 8) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
