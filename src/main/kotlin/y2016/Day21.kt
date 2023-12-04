package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(password: String = "abcdefgh") =
		getInstructions().fold(password) { password, instruction ->
			instruction.invoke(password)
		}

private fun second(password: String = "fbgdceah") =
		getInstructions().reversed().fold(password) { password, instruction ->
			instruction.inverse(password)
		}

private fun getInstructions(day: Int = 21) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.map {
			"swap position (\\d+) with position (\\d+)".toRegex().matchEntire(it)?.let { matchResult ->
				matchResult.groupValues.slice(1..2).map(String::toInt).sorted().let {
					SwapPositionXWithY(it[0], it[1])
				}
			} ?: "swap letter (.) with letter (.)".toRegex().matchEntire(it)?.let { matchResult ->
				SwapLetterXWithY(matchResult.groupValues[1][0], matchResult.groupValues[2][0])
			} ?: "rotate (left|right) (\\d+) steps?".toRegex().matchEntire(it)?.let { matchResult ->
				RotateSteps(matchResult.groupValues[1] == "left", matchResult.groupValues[2].toInt())
			} ?: "rotate based on position of letter (.)".toRegex().matchEntire(it)?.let { matchResult ->
				RotateBasedOnLetter(matchResult.groupValues[1][0])
			} ?: "reverse positions (\\d+) through (\\d+)".toRegex().matchEntire(it)?.let { matchResult ->
				ReverseXToY(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt())
			} ?: "move position (\\d+) to position (\\d+)".toRegex().matchEntire(it)?.let { matchResult ->
				MoveXToY(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt())
			}!!
		}

interface Operation {
	fun invoke(password: String): String
	fun inverse(password: String) = invoke(password)
}

class SwapPositionXWithY(val first: Int, val second: Int) : Operation {
	override fun invoke(password: String) =
			password.substring(0, first) +
					password.substring(second, second + 1) +
					password.substring(first + 1, second) +
					password.substring(first, first + 1) +
					password.substring(second + 1)
}

class SwapLetterXWithY(val first: Char, val second: Char) : Operation {
	override fun invoke(password: String) =
			password.toCharArray().map {
				when (it) {
					first -> second
					second -> first
					else -> it
				}
			}.joinToString("")
}

class RotateSteps(val rotateLeft: Boolean, val steps: Int) : Operation {
	override fun invoke(password: String) = if (rotateLeft) password.rotateLeft(steps) else password.rotateRight(steps)
	override fun inverse(password: String) = if (rotateLeft) password.rotateRight(steps) else password.rotateLeft(steps)
}

class RotateBasedOnLetter(val letter: Char) : Operation {
	override fun invoke(password: String) = password.indexOf(letter).let {
		((if (it >= 4) it + 1 else it) + 1).let {
			password.rotateRight(it)
		}
	}

	override fun inverse(password: String) =
			(0..7).map { password.rotateLeft(it) }.filter { invoke(it) == password }.single()
}

class ReverseXToY(val begin: Int, val end: Int) : Operation {
	override fun invoke(password: String) =
			password.substring(0, begin) + password.substring(begin, end + 1).reversed() + password.substring(end + 1)
}

class MoveXToY(val source: Int, val destination: Int) : Operation {
	override fun invoke(password: String) =
			(password.substring(0, source) + password.substring(source + 1)).let {
				it.substring(0, destination) + password.substring(source, source + 1) + it.substring(destination)
			}
	override fun inverse(password: String) = MoveXToY(destination, source).invoke(password)
}

private fun String.rotateLeft(offset: Int) = substring(offset % length) + substring(0, offset % length)
private fun String.rotateRight(offset: Int) = substring(length - (offset % length)) + substring(0, length - (offset % length))
