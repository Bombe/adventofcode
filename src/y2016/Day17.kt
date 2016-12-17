package y2016

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main(args: Array<String>) {
	println(first().first())
	println(second().map { it.length }.first())
}

private fun first(position: Int = 0, passcode: String = passcode(), pathsTaken: List<Set<Char>> = emptyList()): List<String> {
	if (position == 15) {
		return listOf(pathsTaken.map { it.first() }.joinToString(""))
	} else {
		return passcode.possibleDirections(pathsTaken.map { it.first() }.joinToString("")).filter { direction -> isPossible(position, direction) }.flatMap {
			first(position.move(it), passcode, pathsTaken.plus<Set<Char>>(setOf(it)))
		}.sortedBy { it.length }
	}
}

private fun second(position: Int = 0, passcode: String = passcode(), pathsTaken: List<Set<Char>> = emptyList()): List<String> {
	if (position == 15) {
		return listOf(pathsTaken.map { it.first() }.joinToString(""))
	} else {
		return passcode.possibleDirections(pathsTaken.map { it.first() }.joinToString("")).filter { direction -> isPossible(position, direction) }.flatMap {
			first(position.move(it), passcode, pathsTaken.plus<Set<Char>>(setOf(it)))
		}.sortedByDescending { it.length }
	}
}

private fun Int.move(direction: Char) = when (direction) {
	'U' -> this - 4
	'D' -> this + 4
	'L' -> this - 1
	else -> this + 1
}

private fun isPossible(position: Int, direction: Char) =
	when (direction) {
		'U' -> position > 3
		'D' -> position < 12
		'L' -> (position % 4) > 0
		else -> (position % 4) < 3
	}

private val md5 = MessageDigest.getInstance("MD5")
private fun ByteArray.toHex() = DatatypeConverter.printHexBinary(this).toLowerCase()

private fun String.possibleDirections(path: String = "") = md5.digest((this + path).toByteArray())
		.toHex()
		.toCharArray()
		.take(4)
		.map { it > 'a' }
		.let { mapOf(
				'U' to it[0],
				'D' to it[1],
				'L' to it[2],
				'R' to it[3]
		)}.filterValues { it }
		.keys

private fun passcode() = "qtetzkpl"
