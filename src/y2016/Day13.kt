package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(): Int {
	val map = (pairs().map { p ->
		(p.first to p.second) to if (wall(p.first, p.second)) -1 else 0
	} + ((1 to 1) to 1)).toMap(mutableMapOf())
	var counter = 0
	while (map[31 to 39] == 0) {
		counter++
		pairs()
				.filter { map[it] == counter }
				.flatMap {
					listOf(
							it.first to (it.second - 1),
							it.first to (it.second + 1),
							(it.first - 1) to it.second,
							(it.first + 1) to it.second)
				}
				.filterNot { it.first < 0 || it.second < 0 }
				.filter { map[it] == 0 }
				.distinct()
				.forEach { map[it] = counter + 1 }
	}
	return map[31 to 39]!! - 1
}

private fun pairs() = (0..40).flatMap { y -> (0..40).map { x -> (x to y) } }

private fun second(): Int {
	val map = (pairs().map { p ->
		(p.first to p.second) to if (wall(p.first, p.second)) -1 else 0
	} + ((1 to 1) to 1)).toMap(mutableMapOf())
	var counter = 0
	while (counter < 50) {
		counter++
		pairs()
				.filter { map[it] == counter }
				.flatMap {
					listOf(
							it.first to (it.second - 1),
							it.first to (it.second + 1),
							(it.first - 1) to it.second,
							(it.first + 1) to it.second)
				}
				.filterNot { it.first < 0 || it.second < 0 }
				.filter { map[it] == 0 }
				.distinct()
				.forEach { map[it] = counter + 1 }
	}
	return map.count { it.value > 0 }
}

fun wall(x: Int, y: Int) =
		(x * x + 3 * x + 2 * x * y + y + y * y + getInput()).binary().count { it == '1' } % 2 == 1

private fun Int.binary() = Integer.toBinaryString(this)

private fun getInput() = 1350
