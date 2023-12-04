package y2016


fun main(args: Array<String>) {
	println(solve(targetLength = getFirstTargetLength()))
	println(solve(targetLength = getSecondTargetLength()))
}

private fun solve(input: String = getInput(), targetLength: Int) =
		getDiskData(input, targetLength).getChecksum()

private tailrec fun getDiskData(data: String, targetLength: Int): String =
		if (data.length >= targetLength)
			data.substring(0 until targetLength)
		else
			getDiskData(data + "0" + data.reversed().inverse(), targetLength)

private tailrec fun String.getChecksum(): String =
		if (length % 2 == 1)
			this
		else
			checksum().getChecksum()

private fun String.inverse() = toCharArray().map {
	when (it) {
		'0' -> '1'
		'1' -> '0'
		else -> it
	}
}.joinToString("")

private fun String.checksum() = toCharArray()
		.fold(Pair<MutableList<Char>, Char?>(mutableListOf(), null)) { checksum, digit ->
			if (checksum.second == null) {
				Pair(checksum.first, digit)
			} else {
				Pair(checksum.first.apply { plusAssign(if (digit == checksum.second) '1' else '0') }, null)
			}
		}.first.joinToString("")

private fun getInput() = "11101000110010100"
private fun getFirstTargetLength() = 272
private fun getSecondTargetLength() = 35651584
