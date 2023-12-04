package y2018

import y2018.Tool.*

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() = coordinates(target.x + 1, target.y + 1)
		.associate { it to it.geologicIndex }
		.mapValues { it.key.type }
		.values.sum()

private fun part2() = coordinates(target.x + 1 + 100, target.y + 1 + 100)
		.flatMap { region -> region.tools.map { region to it }.asSequence() }
		.let { possibleCoordinateTools ->
			val targetWithTool = target to torch
			val unvisited = possibleCoordinateTools.toHashSet()
			val distances = hashMapOf<Pair<Coordinate, Tool>, Int>()
			var currentNode = Coordinate(0, 0) to torch
			distances[currentNode] = 0
			do {
				val currentDistance = distances[currentNode]!!
				currentNode.neighbours
						.filter { it in unvisited }
						.map { it to (currentDistance + it.distance(currentNode)) }
						.filter { it.second < distances[it.first] ?: Int.MAX_VALUE }
						.forEach { distances[it.first] = it.second }
				unvisited -= currentNode
				currentNode = distances.filter { it.key in unvisited }.minBy { it.value }!!.key
				oncePerSecond {
					println("$currentNode -> ${distances[currentNode]}")
				}
			} while (targetWithTool != currentNode)
			distances[targetWithTool]
		}

private fun Pair<Coordinate, Tool>.distance(other: Pair<Coordinate, Tool>) = if (second == other.second) 1 else 7

private val Pair<Coordinate, Tool>.neighbours
	get() = setOf(
			first to (first.tools - second).single(),
			(first.minus(0 to 1) to second).takeIf { first.y > 0 }?.takeIf { second in it.first.tools },
			(first.minus(1 to 0) to second).takeIf { first.x > 0 }?.takeIf { second in it.first.tools },
			(first.plus(0 to 1) to second).takeIf { second in it.first.tools },
			(first.plus(1 to 0) to second).takeIf { second in it.first.tools }
	).filterNotNull()

private val Coordinate.tools
	get() = when (type) {
		0 -> setOf(torch, climbingGear)
		1 -> setOf(neither, climbingGear)
		else -> setOf(neither, torch)
	}

private enum class Tool { neither, torch, climbingGear }

private val Coordinate.geologicIndex
	get(): Int = geologicalIndices.getOrPut(this) {
		when {
			y == 0 -> x * 16807
			x == 0 -> y * 48271
			this == target -> 0
			else -> minus(1 to 0).erosionLevel * minus(0 to 1).erosionLevel
		} % 20183
	}

private val geologicalIndices = mutableMapOf<Coordinate, Int>()

private val Coordinate.erosionLevel get() = (geologicIndex + depth) % 20183
private val Coordinate.type get() = erosionLevel % 3

private val depth = readInput(22).first().split(": ").last().toInt()
private val target = readInput(22).last().split(": ").last().split(',').map(String::toInt)
		.let { Coordinate(it[0], it[1]) }
