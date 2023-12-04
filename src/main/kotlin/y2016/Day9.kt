package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = getLines()
		.map { compressed ->
			var result = ""
			var startIndex = 0
			val regex = "\\(([\\d]+)x([\\d]+)\\)".toRegex()
			var matchResult = regex.find(compressed, startIndex)
			while (matchResult != null) {
				result += compressed.substring(startIndex, matchResult.range.start)
				val nextChars = matchResult.groupValues[1].toInt()
				val repititions = matchResult.groupValues[2].toInt()
				(1..repititions).forEach { result += compressed.substring(matchResult!!.range.let { (it.endInclusive + 1)..(it.endInclusive + nextChars) }) }
				startIndex = matchResult.range.endInclusive + nextChars + 1
//				println("$compressed, $result, $startIndex")
				matchResult = regex.find(compressed, startIndex)
			}
			result + compressed.substring(startIndex)
		}
		.joinToString("")
		.length

private fun second() = getDecompressedLength(getLines().joinToString(""))
//		.take(10)
//		.toList()
//		.last()
//		.length

val regex = "\\(([\\d]+)x([\\d]+)\\)".toRegex()

private fun getDecompressedLength(input: String, startIndex: Int = 0): Int =
		input.indexOf('(', startIndex).let {
			when (it) {
				-1 -> input.length
//				startIndex -> {
//					regex.find(input, startIndex)!!.let {
//						val nextChars = matchResult.groupValues[1].toInt()
//						val repititions = matchResult.groupValues[2].toInt()
//
//					}
//				}
				else -> {
					it + getDecompressedLength(input, it)
				}
			}
		}

var first = System.currentTimeMillis()
var i = 0


private fun decompressOnce(input: String): String =
		input.indexOf("(").let { index ->
			if (index == -1) {
				input
			} else {
				regex.find(input, index)?.let { matchResult ->
					val nextChars = matchResult.groupValues[1].toInt()
					val repititions = matchResult.groupValues[2].toInt()
					input.substring(0, matchResult.range.start) + input.substring(matchResult.range.let { (it.endInclusive + 1)..(it.endInclusive + nextChars) }) * repititions + input.substring(matchResult.range.endInclusive + nextChars + 1)
							.apply {
								if (++i % 10000 == 0) {
									println("${input.length - matchResult.range.start}, ${i.toDouble() / ((System.currentTimeMillis() - first) / 1000)}/s")
								}
							}
				} ?: input
			}
		}

private operator fun String.times(times: Int) = (1..times).map { this }.joinToString("")

//private fun getLines() = listOf("A(1x5)BC", "(3x3)XYZ", "A(2x2)BCD(2x2)EFG", "(6x1)(1x3)A", "X(8x2)(3x3)ABCY")
private fun getLines() = listOf("A(1x5)BC", "(3x3)XYZ", "A(2x2)BCD(2x2)EFG", "(6x2)(1x3)A", "X(8x2)(3x3)ABCY")
//private fun getLines(day: Int = 9) = AllDays().javaClass.getResourceAsStream("day$day.txt")
//		.reader()
//		.readLines()
