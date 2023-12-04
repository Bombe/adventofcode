package y2018

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(6).toList()
		.parseCoordinates()
		.findLargestFiniteArea()

private fun part2() = readInput(6).toList()
		.parseCoordinates()
		.findCenterRegion()

private fun List<String>.parseCoordinates() =
		map { it.split(",") }
				.map { it.map(String::trim) }
				.map { it.map(Integer::parseInt) }
				.map { it[0] to it[1] }
				.let(::Grid)

private fun Grid.findLargestFiniteArea() =
		locations()
				.mapNotNull { location -> getClosestCoordinate(location)?.let { it to location } }
				.groupBy(Pair<Coordinate, Coordinate>::first, Pair<Coordinate, Coordinate>::second)
				.let { coordinateAreas ->
					val borderCoordinates = border().mapNotNull { getClosestCoordinate(it) }
							.distinct()
					coordinateAreas.filterKeys { it !in borderCoordinates }
				}
				.map { it.value.size }
				.max()!!

private fun Grid.findCenterRegion() =
		locations()
				.map { getDistanceFromAllCoordinates(it) }
				.filter { it < 10000 }
				.size

data class Grid(private val coordinates: List<Coordinate>) {
	private val minX = coordinates.map(Pair<Int, Int>::first).min()!!
	private val minY = coordinates.map(Pair<Int, Int>::second).min()!!
	private val width = coordinates.map(Pair<Int, Int>::first).max()!! - minX + 1
	private val height = coordinates.map(Pair<Int, Int>::second).max()!! - minY + 1

	fun locations(): List<Coordinate> = (0 until height).flatMap { y -> (0 until width).map { x -> (minX + x) to (minY + y) } }

	fun border(offset: Int = 0) =
			(-offset until width + offset).flatMap { x -> listOf((minX + x) to (minY), (minX + x) to (minY + height - 1)) } +
					(-offset + 1 until height + offset - 1).flatMap { y -> listOf(minX to (minY + y), (minX + width - 1) to (minY + y)) }

	fun getClosestCoordinate(location: Coordinate) =
			coordinates.map { it to it.distanceTo(location) }.sortedBy { it.second }
					.let { closestCoordinates ->
						closestCoordinates[0].takeIf { it.second < closestCoordinates[1].second }
					}?.first

	fun getDistanceFromAllCoordinates(location: Coordinate) =
			coordinates.map { location.distanceTo(it) }.sum()
}
