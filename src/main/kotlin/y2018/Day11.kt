package y2018

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() =
		coordinates(298, 298, 1, 1)
				.map { it to powerLevel(it, 3) }
				.maxBy { it.second }!!.first

private fun part2() =
		(3..300).map { gridSize ->
			coordinates(301 - gridSize, 301 - gridSize, 1, 1)
					.map { (it to gridSize) to powerLevel(it, gridSize) }
					.maxBy { it.second }!!
		}.maxBy { it.second }!!.first

fun coordinates(width: Int, height: Int, left: Int = 0, top: Int = 0): Sequence<Coordinate> =
		generateSequence(left to top) { (x, y) ->
			val nextX = (x + 1).let { if (it == (left + width)) left else it }
			val nextY = if (nextX == left) y + 1 else y
			if (nextY == (top + height)) null else nextX to nextY
		}

private fun powerLevel(coordinate: Coordinate, gridSize: Int): Int =
		areaSum(coordinate.x + gridSize - 1 to coordinate.y + gridSize - 1) +
				areaSum(coordinate.x - 1 to coordinate.y - 1) -
				areaSum(coordinate.x + gridSize - 1 to coordinate.y - 1) -
				areaSum(coordinate.x - 1 to coordinate.y + gridSize - 1)

private fun areaSum(coordinate: Coordinate): Int =
		summedAreaTable.getOrPut(coordinate) {
			when {
				coordinate.x < 1 || coordinate.y < 1 -> 0
				else -> powerLevel(coordinate) +
						areaSum(coordinate.x to coordinate.y - 1) +
						areaSum(coordinate.x - 1 to coordinate.y) -
						areaSum(coordinate.x - 1 to coordinate.y - 1)
			}
		}

private fun powerLevel(coordinate: Coordinate) =
		(coordinate.x + 10).let { rackId ->
			(((rackId * coordinate.y + serialNumber) * rackId) % 1000) / 100 - 5
		}

private val summedAreaTable = mutableMapOf<Coordinate, Int>()

private val serialNumber = readInput(11).single().let(Integer::parseInt)
