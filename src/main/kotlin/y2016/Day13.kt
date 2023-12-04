package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(): Int {
	val map = createMaze()
	var counter = 0
	while (map[31 to 39] == 0) {
		counter++
		processMaze(map, counter)
	}
	return map[31 to 39]!! - 1
}

private fun createMaze(): MutableMap<Pair<Int, Int>, Int> {
	return (0..40).flatMap { y ->
		(0..40).map { x -> (x to y) }
	}.map { p ->
		(p.first to p.second) to if (wall(p.first, p.second)) -1 else if ((p.first == 1) && (p.second == 1)) 1 else 0
	}.toMap(mutableMapOf())
}

private fun processMaze(map: MutableMap<Pair<Int, Int>, Int>, step: Int) {
	map
			.filter { it.value == step }
			.flatMap {
				listOf(
						it.key.first to (it.key.second - 1),
						it.key.first to (it.key.second + 1),
						(it.key.first - 1) to it.key.second,
						(it.key.first + 1) to it.key.second)
			}
			.filterNot { it.first < 0 || it.second < 0 }
			.filter { map[it] == 0 }
			.distinct()
			.forEach { map[it] = step + 1 }
}

private fun second(): Int {
	val map = createMaze()
	var counter = 0
	while (counter < 50) {
		counter++
		processMaze(map, counter)
	}
	return map.count { it.value > 0 }
}

fun wall(x: Int, y: Int) =
		(x * x + 3 * x + 2 * x * y + y + y * y + getInput()).binary().count { it == '1' } % 2 == 1

private fun Int.binary() = Integer.toBinaryString(this)

private fun getInput() = 1350
