package y2016

fun getInput(day: Int) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()

class AllDays()
