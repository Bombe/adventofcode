package y2018

import java.util.*

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(9)
		.toGameEnd()
		.scoreOfWinner()

private fun part2() = readInput(9)
		.toGameEnd()
		.let { gameEnd -> gameEnd.copy(second = gameEnd.lastMarble * 100) }
		.scoreOfWinner()

private fun Sequence<String>.toGameEnd() =
		single()
				.let { Regex("(.*) players; last marble is worth (.*) points").matchEntire(it) }!!
				.groupValues
				.drop(1)
				.map(Integer::parseInt)
				.let { it[0] to it[1] }

private fun GameEnd.scoreOfWinner() =
		LinkedList<Long>(List(this.players) { 0L }).also { players ->
			Game().loopUntil { game ->
				val (newGame, score) = game.placeNextMarble()
				players.addLast(players.removeFirst() + score)
				(game.nextMarble == lastMarble) to newGame
			}
		}.max()!!

private typealias GameEnd = Pair<Int, Int>

private val GameEnd.players get() = first
private val GameEnd.lastMarble get() = second

private typealias Circle = Deque<Int>

private fun Circle.rotateCw(steps: Int) = apply { repeat(steps) { addLast(removeFirst()) } }
private fun Circle.rotateCcw(steps: Int) = apply { repeat(steps) { addFirst(removeLast()) } }

private data class Game(val circle: Circle = LinkedList(listOf(0)), val nextMarble: Int = 1) {
	fun placeNextMarble() = if (nextMarble % 23 == 0)
		circle.rotateCcw(7)
				.let { it to it.removeFirst() }
				.let { copy(circle = it.first, nextMarble = nextMarble + 1) to (nextMarble + it.second) }
	else
		circle.rotateCw(2).let { newCircle ->
			copy(circle = newCircle.apply { addFirst(nextMarble) }, nextMarble = nextMarble + 1) to 0
		}
}
