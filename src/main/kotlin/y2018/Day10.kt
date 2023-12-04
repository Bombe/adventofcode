package y2018

import kotlin.math.*

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() = readInput(10)
		.parseLights()
		.findMessage()
		.second.asText().joinToString("\n")

private fun part2() = readInput(10)
		.parseLights()
		.findMessage()
		.first

private fun Sequence<String>.parseLights() =
		mapNotNull { Regex("position=<(.*), (.*)> velocity=<(.*), (.*)>").matchEntire(it) }
				.map { it.groupValues.drop(1) }
				.map { it.map(String::trim) }
				.map { it.map(Integer::parseInt) }
				.map { Light(it[0], it[1], it[2] to it[3]) }
				.toList()

private fun List<Light>.findMessage() =
		let { 0 to it }
				.loopUntil { (iteration, lights) ->
					(lights.move().move().extent() > lights.move().extent()) to ((iteration + 1) to lights.move())
				}

private fun List<Light>.move() =
		map { it.copy(x = it.x + it.speed.first, y = it.y + it.speed.second, speed = it.speed) }

private fun List<Light>.extent() =
		abs(map(Light::x).max()!! - map(Light::x).min()!!) + abs(map(Light::y).max()!! - map(Light::y).min()!!)

private fun List<Light>.asText(): List<String> {
	val maxX = map(Light::x).max()!!
	val minX = map(Light::x).min()!!
	val maxY = map(Light::y).max()!!
	val minY = map(Light::y).min()!!
	val width = maxX - minX + 1
	val height = maxY - minY + 1
	return map { it.x to it.y }
			.let { scaledLights ->
				CharArray(width * height) { '.' }.also { grid ->
					scaledLights.forEach { grid[(it.second - minY) * width + (it.first - minX)] = '#' }
				}
			}.toList()
			.chunked(width)
			.map { it.joinToString("") }
}

private data class Light(val x: Int, val y: Int, val speed: Pair<Int, Int>)
