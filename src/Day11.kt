import kotlin.math.absoluteValue
import kotlin.math.max

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(11).first().split(",")

private fun part1(input: List<String> = getInput()) =
		input
				.fold(Coords(), Coords::move)
				.distance

private fun part2(input: List<String> = getInput()) =
		input
				.fold(Coords() to 0) { (coords, maxDistance), direction ->
					coords
							.move(direction)
							.let { it to max(maxDistance, it.distance) }
				}.second

private data class Coords(val x: Int = 0, val y: Int = 0, val z: Int = 0)

private fun Coords.incX() = Coords(x + 1, y, z)
private fun Coords.decX() = Coords(x - 1, y, z)
private fun Coords.incY() = Coords(x, y + 1, z)
private fun Coords.decY() = Coords(x, y - 1, z)
private fun Coords.incZ() = Coords(x, y, z + 1)
private fun Coords.decZ() = Coords(x, y, z - 1)
private fun Coords.move(direction: String) = when (direction) {
	"n" -> incX().decZ()
	"s" -> decX().incZ()
	"ne" -> incX().decY()
	"sw" -> decX().incY()
	"nw" -> incY().decZ()
	"se" -> decY().incZ()
	else -> this
}

private val Coords.distance get() = (x.absoluteValue + y.absoluteValue + z.absoluteValue) / 2
