package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(): Int {
	var time = 0
	val wheels = getWheels()
	while (!wheels.all { (time + it.index + it.offset) % it.size == 0 }) {
		time++
	}
	return time
}

private fun second(): Int {
	var time = 0
	val wheels = getWheels().let { it + Wheel(it.size + 1, 11, 0) }
	while (!wheels.all { (time + it.index + it.offset) % it.size == 0 }) {
		time++
	}
	return time
}

data class Wheel(val index: Int, val size: Int, val offset: Int)

private fun getWheels(day: Int = 15) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.map { "Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).".toRegex().find(it)!!.groupValues }
		.map { Wheel(it[1].toInt(), it[2].toInt(), it[3].toInt()) }
