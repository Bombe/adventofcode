package y2016

fun main(args: Array<String>) {
	println(solve(getWheels()))
	println(solve(getWheels().let { it + Wheel(it.size + 1, 11, 0) }))
}

private fun solve(wheels: List<Wheel>) =
		generateSequence(0) { if (wheels.goesThrough(it)) null else (it + 1) }
		.last()

private fun List<Wheel>.goesThrough(time: Int) = all { (time + it.index + it.offset) % it.size == 0 }

data class Wheel(val index: Int, val size: Int, val offset: Int)

private fun getWheels(day: Int = 15) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.map { "Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).".toRegex().find(it)!!.groupValues }
		.map { Wheel(it[1].toInt(), it[2].toInt(), it[3].toInt()) }
