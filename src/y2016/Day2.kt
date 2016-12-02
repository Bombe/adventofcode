package y2016

class Day2 {

	object First {

		@JvmStatic
		fun main(args: Array<String>) = Day2().first()

	}

	object Second {

		@JvmStatic
		fun main(args: Array<String>) = Day2().second()

	}

	object Both {

		@JvmStatic
		fun main(args: Array<String>) {
			Day2().first()
			Day2().second()
		}

	}

	data class Result(val key: Int, val keys: List<Int>)

	data class Key(val up: Int?, val down: Int?, val left: Int?, val right: Int?)

	private val squareKeypad = mapOf(
			1 to Key(null, 4, null, 2),
			2 to Key(null, 5, 1, 3),
			3 to Key(null, 6, 2, null),
			4 to Key(1, 7, null, 5),
			5 to Key(2, 8, 4, 6),
			6 to Key(3, 9, 5, null),
			7 to Key(4, null, null, 8),
			8 to Key(5, null, 7, 9),
			9 to Key(6, null, 8, null)
	)

	private val diamondKeypad = mapOf(
			1 to Key(null, 3, null, null),
			2 to Key(null, 6, null, 3),
			3 to Key(1, 7, 2, 4),
			4 to Key(null, 8, 3, null),
			5 to Key(null, null, null, 6),
			6 to Key(2, 10, 5, 7),
			7 to Key(3, 11, 6, 8),
			8 to Key(4, 12, 7, 9),
			9 to Key(null, null, 8, null),
			10 to Key(6, null, null, 11),
			11 to Key(7, 13, 10, 12),
			12 to Key(8, null, 11, null),
			13 to Key(11, null, null, null)
	)

	fun first() = getCombination(squareKeypad)
			.plus("\n")
			.forEach(::print)

	fun second() = getCombination(diamondKeypad)
			.map(Int::toHex)
			.plus("\n")
			.forEach(::print)

	private fun getCombination(keypad: Map<Int, Key>) = getInput(2)
			.fold(Result(5, emptyList())) { result, directions ->
				calculateKeyToPress(result.key, directions) { key, direction -> getNextKey(keypad, key, direction) }.let {
					Result(it, result.keys + it)
				}
			}.keys

	private fun calculateKeyToPress(initialKey: Int, directions: String, nextKey: (Int, Char) -> Int) = directions.toCharArray()
			.fold(initialKey, nextKey)

	private fun getNextKey(keypad: Map<Int, Key>, key: Int, direction: Char) = when (direction) {
		'U' -> keypad[key]?.up ?: key
		'D' -> keypad[key]?.down ?: key
		'L' -> keypad[key]?.left ?: key
		'R' -> keypad[key]?.right ?: key
		else -> throw RuntimeException()
	}

}

private fun Int.toHex() = Integer.toHexString(this).toUpperCase()
