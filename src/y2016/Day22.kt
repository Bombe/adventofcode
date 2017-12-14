package y2016

import java.util.Deque
import java.util.LinkedList
import java.util.Stack

fun main(args: Array<String>) {
	println(first().count())
	println(second().minBy { it.size })
}

private fun first() =
		getGrid().nodes.values.let { nodes ->
			nodes.flatMap { outer ->
				nodes.filter { it != outer }
						.filter { outer.used > 0 && outer.used < it.avail }
						.map { Pair(it, outer) }
			}.distinctBy { if (it.first < it.second) Pair(it.first, it.second) else Pair(it.second, it.first) }
		}

private operator fun Node.compareTo(other: Node) =
		location.compareTo(other.location)

private operator fun Pair<Int, Int>.compareTo(other: Pair<Int, Int>) =
		if (first < other.first) -1 else
			if (first < other.first) 1 else
				if (second < other.second) -1 else
					if (second > other.second) 1 else 0

private fun second(): List<List<Grid>> {
	val initialGrid = getGrid()
	val queue = LinkedList<Grid>()
	initialGrid.allMoves().map { initialGrid.move(it) }.sortedBy(::cost).forEach { queue.add(it) }
	val visited = mutableSetOf<Pair<Int, Int>>()
	while (queue.isNotEmpty()) {
		val grid = queue.pop()
		if (grid.targetData == Pair(0, 0)) {
			return emptyList()
		}
		if (grid.emptyCell() in visited) {
			continue
		}
		visited += grid.emptyCell()
		grid.display().run { println() }
		Thread.sleep(500)
		grid.allMoves().map { grid.move(it) }.sortedBy(::cost).forEach { queue.add(it) }
	}
	return emptyList()
}

private fun cost(grid: Grid): Int {
	return (grid.targetData.first + grid.targetData.second) * 5 +
			grid.nodes.values.first { it.used == 0 }.location.let { (grid.targetData.first - it.first).abs() + (grid.targetData.second - it.second).abs() }
}

private fun Int.abs() = Math.abs(this)

data class Node(val location: Pair<Int, Int>, val used: Int, val avail: Int) {
	fun canMoveTo(other: Node) =
			((location.first == other.location.first) || (location.second == other.location.second)) &&
					(Math.abs(location.first - other.location.first) <= 1) && (Math.abs(location.second - other.location.second) <= 1) &&
					(other.avail >= used) && (used > 0)
}

data class NodeMove(val from: Pair<Int, Int>, val to: Pair<Int, Int>)

class Grid(allNodes: Collection<Node>, val targetData: Pair<Int, Int>) {

	constructor(allNodes: Collection<Node>) : this(allNodes, Pair(allNodes.map { it.location.first }.max()!!, 0))

	val nodes = allNodes.associate { Pair(it.location, it) }
	val width = nodes.map { it.key.first }.max()!! + 1
	val height = nodes.map { it.key.second }.max()!! + 1

	operator fun get(location: Pair<Int, Int>) = nodes[location]!!
	operator fun get(x: Int, y: Int) = nodes[Pair(x, y)]!!

	fun move(move: NodeMove) =
			this[move.from].let { fromNode ->
				this[move.to].let { toNode ->
					if ((fromNode.used == 0) || (toNode.avail < fromNode.used))
						this
					else
						Grid(nodes.values.map {
							when (it.location) {
								move.from -> Node(it.location, 0, it.used + it.avail)
								move.to -> Node(it.location, it.used + fromNode.used, it.avail - fromNode.used)
								else -> it
							}
						}, if (move.from == targetData) move.to else targetData)
				}
			}

	fun emptyCell() = nodes.values.first { it.used == 0 }.location

	fun allMoves() = nodes.entries.map { it.value }.flatMap { node ->
		listOf(
				Pair(node.location.first - 1, node.location.second),
				Pair(node.location.first + 1, node.location.second),
				Pair(node.location.first, node.location.second - 1),
				Pair(node.location.first, node.location.second + 1)
		)
				.filterNot { (it.first < 0) || (it.second < 0) || (it.first >= width) || (it.second >= height) }
				.map { nodes[it]!! }
				.filter { node.canMoveTo(it) }
				.map { NodeMove(node.location, it.location) }
	}

	fun display() =
			(0 until height).forEach { y ->
				(0 until width).forEach { x ->
					this[x, y].let { g ->
						print(if (g.used == 0) "0" else if (g.used > 100) "#" else if (targetData == Pair(x, y)) "T" else ".")
					}
				}
				println()
			}

	override fun equals(other: Any?): Boolean {
		return other is Grid && nodes == other.nodes
	}
}

private fun getGrid(day: Int = 22) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.filter { it.startsWith("/") }
		.map { "/dev/grid/node-x(\\d+)-y(\\d+) +(\\d+)T +(\\d+)T +(\\d+)T +(\\d+)%".toRegex().matchEntire(it)!!.groupValues.slice(1..5).map(String::toInt) }
		.map { Node(Pair(it[0], it[1]), it[3], it[4]) }
		.let { Grid(it) }

