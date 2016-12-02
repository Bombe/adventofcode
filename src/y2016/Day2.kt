package y2016

class Day2 {

	object First {

		@JvmStatic
		fun main(args: Array<String>) = Day2().first()

	}

	fun first() {
		var key = 5
		getInput(2)
				.forEach {
					key = calculateKeyToPress(key, it) { key, direction -> getNextKeySquare(key, direction) }
					print(key)
				}
	}

	private fun calculateKeyToPress(initialKey: Int, directions: String, nextKey: (Int, Char) -> Int) = directions.toCharArray()
			.fold(initialKey, nextKey)

	private fun getNextKeySquare(key: Int, direction: Char) = when (direction) {
		'U' -> if (key > 3) key - 3 else key
		'D' -> if (key < 7) key + 3 else key
		'L' -> if (((key - 1) % 3) > 0) key - 1 else key
		'R' -> if (((key - 1) % 3) < 2) key + 1 else key
		else -> throw RuntimeException()
	}

	object Second {

		@JvmStatic
		fun main(args: Array<String>) = Day2().second()

	}

	fun second() {
		var key = 5
		getInput(2)
				.forEach {
					key = calculateKeyToPress(key, it) { key, direction -> getNextKeyDiamond(key, direction) }
					print(key.toHex())
				}
	}

	private fun Int.toHex() = Integer.toHexString(this).toUpperCase()

	data class DiamondKey(val up: Int?, val down: Int?, val left: Int?, val right: Int?)

	private val keypad = mapOf(
			1 to DiamondKey(null, 3, null, null),
			2 to DiamondKey(null, 6, null, 3),
			3 to DiamondKey(1, 7, 2, 4),
			4 to DiamondKey(null, 8, 3, null),
			5 to DiamondKey(null, null, null, 6),
			6 to DiamondKey(2, 10, 5, 7),
			7 to DiamondKey(3, 11, 6, 8),
			8 to DiamondKey(4, 12, 7, 9),
			9 to DiamondKey(null, null, 8, null),
			10 to DiamondKey(6, null, null, 11),
			11 to DiamondKey(7, 13, 10, 12),
			12 to DiamondKey(8, null, 11, null),
			13 to DiamondKey(11, null, null, null)
	)

	private fun getNextKeyDiamond(key: Int, direction: Char) = when (direction) {
		'U' -> keypad[key]?.up ?: key
		'D' -> keypad[key]?.down ?: key
		'L' -> keypad[key]?.left ?: key
		'R' -> keypad[key]?.right ?: key
		else -> throw RuntimeException()
	}

}
