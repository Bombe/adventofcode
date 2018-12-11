package y2018

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() =
		coordinates(298, 298, 1, 1)
				.maxBy { coordinate -> powerLevel(coordinate, 3) }!!

private fun part2() =
		(3..300).reversed().map { gridSize ->
			coordinates(301 - gridSize, 301 - gridSize, 1, 1)
					.fold((0 to 0) to Integer.MIN_VALUE) { (coordinate, powerlevel), nc ->
						powerLevel(nc, gridSize).let { pl: Int ->
							if (pl > powerlevel)
								(nc) to pl else coordinate to powerlevel
						}
					}.let { (it.first to gridSize) to it.second }
		}.maxBy { it.second }!!.first

private fun coordinates(width: Int, height: Int, left: Int = 0, top: Int = 0) =
		generateSequence(left to top) { (x, y) ->
			val nextX = (x + 1).let { if (it == (left + width)) left else it }
			val nextY = if (nextX == left) y + 1 else y
			if (nextY == (top + height)) null else nextX to nextY
		}

private val serialNumber = readInput(11).single().let(Integer::parseInt)
private val powerLevels = (1..300).flatMap { y -> (1..300).map { x -> (x to y) to powerLevel(x, y, serialNumber) } }
		.toMap()
private val powerLevels2 = mutableMapOf<Pair<Coordinate, Int>, Int>()

private fun powerLevel(x: Int, y: Int, serialNumber: Int) = (x + 10).let { rackId ->
	(((rackId * y + serialNumber) * rackId) % 1000) / 100 - 5
}

private fun powerLevel(coordinate: Coordinate, gridSize: Int): Int =
		powerLevels2.getOrPut(coordinate to gridSize) {
			if (gridSize == 1) powerLevels[coordinate]!! else
				powerLevel(coordinate, gridSize - 1) +
						(0 until gridSize - 1).map { powerLevels[coordinate.first + gridSize - 1 to coordinate.second + it]!! }.sum() +
						(0 until gridSize).map { powerLevels[coordinate.first + it to coordinate.second + gridSize - 1]!! }.sum()
		}
