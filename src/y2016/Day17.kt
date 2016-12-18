package y2016

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = solve().minBy { it.length }

private fun second() = solve().map { it.length }.max()

private fun solve(position: Int = 0, passcode: String = passcode(), pathsTaken: List<Set<Char>> = emptyList()): List<String> =
		if (position == 15) {
			listOf(pathsTaken.map { it.first() }.joinToString(""))
		} else {
			passcode.possibleDirections(pathsTaken.map { it.first() }.joinToString("")).filter { it.isPossible(position) }.flatMap {
				solve(it.move(position), passcode, pathsTaken.plus<Set<Char>>(setOf(it.toChar())))
			}
		}

private val md5 = MessageDigest.getInstance("MD5")
private fun ByteArray.toHex() = DatatypeConverter.printHexBinary(this).toLowerCase()

private fun String.possibleDirections(path: String = "") = md5.digest((this + path).toByteArray())
		.toHex()
		.toCharArray()
		.take(4)
		.map { it > 'a' }
		.zip(Direction.values())
		.associate { it.second to it.first }
		.filterValues { it }
		.keys

enum class Direction(private val offset: Int, private val check: (Int) -> Boolean) {
	UP(-4, { it > 3 }),
	DOWN(4, { it < 12 }),
	LEFT(-1, { (it % 4) > 0 }),
	RIGHT(1, { (it % 4) < 3 });

	fun isPossible(position: Int) = check.invoke(position)
	fun move(position: Int) = position + offset
	fun toChar() = name[0]
}

private fun passcode() = "qtetzkpl"
