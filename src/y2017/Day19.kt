package y2017

import y2017.Direction.Down
import y2017.Direction.Left
import y2017.Direction.Right
import y2017.Direction.Up

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(19).mapIndexed { rowIndex, row ->
	rowIndex to row.mapIndexed { index, char ->
		index to char
	}
}.toList()
		.flatMap { (row, pairs) ->
			pairs.filter { it.second != ' ' }.map { (it.first to row) to it.second }
		}.toMap()

private fun part1(maze: Map<Pair<Int, Int>, Char> = getInput()) =
		generateSequence(findMazeEntry(maze)) { mazeState ->
			maze.findNext(mazeState)
		}.last().letters

private fun part2(maze: Map<Pair<Int, Int>, Char> = getInput()) =
		generateSequence(findMazeEntry(maze)) { mazeState ->
			maze.findNext(mazeState)
		}.last().steps

private fun findMazeEntry(maze: Map<Pair<Int, Int>, Char>) =
		maze.entries
				.first { entry -> entry.key.second == 0 }
				.key
				.let { MazeState(it) }

private fun Map<Pair<Int, Int>, Char>.findNext(mazeState: MazeState) =
		if (this[mazeState.location] in 'A'..'Z') {
			mazeState.next().copy(letters = mazeState.letters + this[mazeState.location], steps = mazeState.steps + 1)
		} else {
			if (mazeState.next().location in this) {
				mazeState.next().copy(steps = mazeState.steps + 1)
			} else {
				listOf(Up, Down, Left, Right)
						.filterNot { it == mazeState.direction.inverse }
						.firstOrNull { mazeState.location.next(it) in this }
						?.let { mazeState.copy(location = mazeState.location.next(it), direction = it, steps = mazeState.steps + 1) }
			}
		}

private fun Pair<Int, Int>.next(direction: Direction) = when (direction) {
	Up -> first to (second - 1)
	Down -> first to (second + 1)
	Left -> (first - 1) to second
	Right -> (first + 1) to second
}

private enum class Direction { Up, Down, Left, Right }
private val Direction.inverse get() = listOf(Down, Up, Right, Left)[ordinal]

private data class MazeState(val location: Pair<Int, Int>, val direction: Direction = Down, val letters: String = "", val steps: Int = 0)

private fun MazeState.next() = copy(location = location.next(direction))
