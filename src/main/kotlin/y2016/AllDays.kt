package y2016

import java.lang.System.currentTimeMillis

fun getInput(day: Int) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()

class AllDays()

fun <R> measure(method: () -> R) =
		currentTimeMillis().let { timestamp ->
			method().apply {
				println("${"%.3f".format((currentTimeMillis() - timestamp) / 1000.0)} s")
			}
		}
