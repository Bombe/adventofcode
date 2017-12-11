import kotlin.math.absoluteValue
import kotlin.math.max

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(11).first().split(",")

private fun part1(input: List<String> = getInput()) =
		input.fold(Coords(0, 0, 0)) { coords, direction ->
			when (direction) {
				"n" -> coords.incX().decZ()
				"s" -> coords.decX().incZ()
				"ne" -> coords.incX().decY()
				"sw" -> coords.decX().incY()
				"nw" -> coords.incY().decZ()
				"se" -> coords.decY().incZ()
				else -> throw RuntimeException("invalid direction: $direction")
			}
		}.distance

private fun part2(input: List<String> = getInput()) =
		input.fold(Coords(0, 0, 0) to 0) { (coords, maxDistance), direction ->
			when (direction) {
				"n" -> coords.incX().decZ()
				"s" -> coords.decX().incZ()
				"ne" -> coords.incX().decY()
				"sw" -> coords.decX().incY()
				"nw" -> coords.incY().decZ()
				"se" -> coords.decY().incZ()
				else -> throw RuntimeException("invalid direction: $direction")
			}.let { it to max(maxDistance, it.distance) }
		}.second

private data class Coords(val x: Int, val y: Int, val z: Int)

private fun Coords.incX() = Coords(x + 1, y, z)
private fun Coords.decX() = Coords(x - 1, y, z)
private fun Coords.incY() = Coords(x, y + 1, z)
private fun Coords.decY() = Coords(x, y - 1, z)
private fun Coords.incZ() = Coords(x, y, z + 1)
private fun Coords.decZ() = Coords(x, y, z - 1)
private val Coords.distance get() = (x.absoluteValue + y.absoluteValue + z.absoluteValue) / 2
