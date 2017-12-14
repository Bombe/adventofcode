package y2016

import java.util.Comparator.comparingInt

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = getInput()
		.map(::toRoom)
		.filter(Room::real)
		.map(Room::sectorId)
		.sum()

private fun second() = getInput()
		.map(::toRoom)
		.filter(Room::real)
		.filter { it.decryptedName == "northpole object storage" }
		.map(Room::sectorId)

private fun toRoom(line: String): Room = "([a-z-]+)-([0-9]+)\\[([a-z]+)\\]"
		.toRegex()
		.matchEntire(line)!!
		.let {
			Room(it.groupValues[1], it.groupValues[2].toInt(), it.groupValues[3])
		}

data class Room(val encryptedName: String, val sectorId: Int, val checksum: String) {

	val real = encryptedName.toCharArray()
			.filter { it != '-' }
			.map { it to 1 }
			.fold(emptyMap<Char, Int>()) { map, newLetter ->
				map + (newLetter.first to (map.getOrElse(newLetter.first, { 0 }) + newLetter.second))
			}.entries
			.fold(emptyList<Pair<Int, Char>>()) { list, entry ->
				list + (entry.value to entry.key)
			}
			.sortedWith(comparingInt<Pair<Int, Char>> { it.first }.reversed().thenComparingInt<Pair<Int, Char>> { it.second.toInt() })
			.map { it.second }
			.take(5)
			.joinToString("") == checksum

	val decryptedName = encryptedName.toCharArray()
			.map { if (it == '-') ' ' else it }
			.map { if (it == ' ') ' ' else ((((it.toInt() - 97) + sectorId) % 26) + 97).toChar() }
			.joinToString("")

}

private fun getInput(day: Int = 4) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
