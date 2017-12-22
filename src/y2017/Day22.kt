package y2017

import y2017.CarrierDirection.Down
import y2017.CarrierDirection.Left
import y2017.CarrierDirection.Right
import y2017.CarrierDirection.Up

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(22).toList()
		.mapIndexed { index, row -> index to row }
		.flatMap { it.second.toCharArray().mapIndexed { index, cell -> (it.first to index) to cell } }
		.let { grid ->
			grid.map { it.first.first }.max()!!.let { height ->
				grid.map { it.first.second }.max()!!.let { width ->
					grid.map { ((it.first.first - height / 2) to (it.first.second - width / 2) to it.second) }
				}
			}
		}.toMap()

private enum class CarrierDirection { Up, Right, Down, Left }

private data class Carrier(val x: Int = 0, val y: Int = 0, val direction: CarrierDirection = Up)

private val Carrier.location get() = y to x
private fun Carrier.turnRight() = copy(direction = enumValues<CarrierDirection>().let { it[(direction.ordinal + 1) % it.size] })
private fun Carrier.turnLeft() = copy(direction = enumValues<CarrierDirection>().let { it[(direction.ordinal + it.size - 1) % it.size] })
private fun Carrier.reverse() = copy(direction = enumValues<CarrierDirection>().let { it[(direction.ordinal + 2) % it.size] })
private fun Carrier.moveForward() = when (direction) {
	Up -> copy(y = y - 1)
	Down -> copy(y = y + 1)
	Left -> copy(x = x - 1)
	Right -> copy(x = x + 1)
}

private fun part1(grid: MutableMap<Pair<Int, Int>, Char> = getInput().toMutableMap()) =
		generateSequence(Carrier() to Pair(0, 0)) { (carrier, iterationsAndInfections) ->
			if (iterationsAndInfections.first == 10000)
				null
			else when (grid[carrier.location]) {
				'#' -> carrier.turnRight().moveForward().also { grid[carrier.location] = '.' } to (iterationsAndInfections.first + 1 to iterationsAndInfections.second)
				else -> carrier.turnLeft().moveForward().also { grid[carrier.location] = '#' } to (iterationsAndInfections.first + 1 to iterationsAndInfections.second + 1)
			}
		}.last().second.second

private fun part2(grid: MutableMap<Pair<Int, Int>, Char> = getInput().toMutableMap()) =
		generateSequence(Carrier() to Pair(0, 0)) { (carrier, iterationsAndInfections) ->
			if (iterationsAndInfections.first == 10000000)
				null
			else when (grid[carrier.location]) {
				'#' -> carrier.turnRight().moveForward().also { grid[carrier.location] = 'F' } to (iterationsAndInfections.first + 1 to iterationsAndInfections.second)
				'W' -> carrier.moveForward().also { grid[carrier.location] = '#' } to (iterationsAndInfections.first + 1 to iterationsAndInfections.second + 1)
				'F' -> carrier.reverse().moveForward().also { grid[carrier.location] = '.' } to (iterationsAndInfections.first + 1 to iterationsAndInfections.second)
				else -> carrier.turnLeft().moveForward().also { grid[carrier.location] = 'W' } to (iterationsAndInfections.first + 1 to iterationsAndInfections.second)
			}
		}.last().second.second
