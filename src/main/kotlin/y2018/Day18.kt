package y2018

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() = readInput(18)
		.parseMap()
		.iterate(10, Map<Coordinate, Char>::iterate)
		.value()

private fun part2() = readInput(18)
		.parseMap()
		.let {
			var map = it
			val buffer = mutableListOf<Int>()
			for (i in 1..200) {
				map = map.iterate()
				buffer += map.value()
			}
			val bufferSize = buffer.size
			var loopStart = -1
			var loop = emptyList<Int>()
			for (i in 200..1000000000) {
				map = map.iterate()
				buffer += map.value()
				for (size in 2..bufferSize / 2) {
					loop = buffer.subList(bufferSize - size, bufferSize)
					if (loop == buffer.subList(bufferSize - size - size, bufferSize - size)) {
						loopStart = bufferSize - size - size
						loop = loop.toList()
						break
					}
				}
				buffer.removeAt(0)
				if (loopStart > -1) break
			}
			loop[(1000000000 - loopStart) % loop.size]
		}

private fun Sequence<String>.parseMap() = toList()
		.mapIndexed { y, line -> line.toCharArray().mapIndexed { x, c -> Coordinate(x, y) to c } }
		.flatten()
		.toMap()

private fun <T> T.iterate(n: Int, block: (T) -> T): T {
	var t = this
	repeat(n) {
		t = block(t)
	}
	return t
}

private fun Map<Coordinate, Char>.iterate() =
		mapValues { coordinate ->
			when (coordinate.value) {
				'.' -> if (neighbours(coordinate.key).count { it == '|' } >= 3) '|' else '.'
				'|' -> if (neighbours(coordinate.key).count { it == '#' } >= 3) '#' else '|'
				'#' -> if (neighbours(coordinate.key).let { neighbours -> neighbours.any { it == '|' } && neighbours.any { it == '#' } }) '#' else '.'
				else -> throw RuntimeException()
			}
		}

private fun Map<Coordinate, Char>.neighbours(coordinate: Coordinate) =
		(-1..1).flatMap { y -> (-1..1).mapNotNull { x -> this[coordinate + (x to y)]?.takeUnless { x == 0 && y == 0 } } }

private fun Map<Coordinate, Char>.value() = count { it.value == '|' } * count { it.value == '#' }
