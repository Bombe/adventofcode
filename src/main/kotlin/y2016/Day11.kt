package y2016

fun main(args: Array<String>) {
	println(first())
}

private fun first() {
	val currentState = Position(0, getInput())
	val results = mutableListOf<List<Move>>()
	makeAllPossibleMoves(emptyList(), results, currentState)
	println(results)
}

private fun makeAllPossibleMoves(moves: List<Move>, results: MutableList<List<Move>>, currentState: Position) {
	val possibleMoves = currentState.possibleMoves()
	val finishMoves = possibleMoves.filter { (currentState + it).factory.finished() }
	results += finishMoves
	if (finishMoves.isEmpty()) {
		possibleMoves
				.filter { (moves.isEmpty()) || (moves.last().reverse() != it) }
				.forEach { move -> makeAllPossibleMoves(moves + move, results, currentState + move) }
	}
}

private fun Move.reverse() = Move(target, source, things)

private fun Position.possibleMoves() =
		listOf(
				if (floor < 3) {
					factory.items[floor]!!.pairs().map { Move(floor, floor + 1, it.toList().toSet()) }
//							factory.items[floor]!!.map { Move(floor, floor + 1, setOf(it)) }
				} else null,
				if (floor > 0) {
					factory.items[floor]!!.map { Move(floor, floor - 1, setOf(it)) }
//							factory.items[floor]!!.pairs().map { Move(floor, floor - 1, it.toList().toSet()) }
				} else null
		)
				.filterNotNull()
				.flatMap { it }
				.filter { move ->
					(factory + move).valid()
				}
				.filterNot { move ->
					((move.source == 1) && (move.target == 0) && factory.items[0]!!.isEmpty()) ||
							((move.source == 2) && (move.target == 1) && factory.items[1]!!.isEmpty() && factory.items[0]!!.isEmpty())
				}

private operator fun Position.plus(move: Move) =
		Position(move.target, factory + move)

private fun <T> Collection<T>.pairs() =
		this.mapIndexed { firstIndex, first ->
			this.mapIndexed { secondIndex, second ->
				if (secondIndex > firstIndex) {
					Pair(first, second)
				} else null
			}.filterNotNull()
		}.flatMap { it }

private operator fun Factory.plus(move: Move) =
		Factory(items.mapValues { oldItems ->
			when (oldItems.key) {
				move.source -> oldItems.value - move.things
				move.target -> oldItems.value + move.things
				else -> oldItems.value
			}
		})

private fun Factory.finished() =
		items[0]!!.isEmpty() && items[1]!!.isEmpty() && items[2]!!.isEmpty()

private fun Factory.valid() =
		items.none { floorItems ->
			floorItems.value
					.filter { it.generator }
					.filterNot { floorItems.value.contains(it.toMicrochip()) }
					.isNotEmpty() &&
					floorItems.value
							.filter { it.microchip }
							.filterNot { floorItems.value.contains(it.toGenerator()) }
							.isNotEmpty()
		}

private interface Thing {
	val material: String
	val generator: Boolean
	val microchip: Boolean
}

private fun Thing.toMicrochip() = Microchip(material)
private fun Thing.toGenerator() = Generator(material)

private data class Generator(override val material: String) : Thing {
	override val generator = true
	override val microchip = false
}

private data class Microchip(override val material: String) : Thing {
	override val generator = false
	override val microchip = true
}

private data class Factory(val items: Map<Int, Set<Thing>>)
private data class Position(val floor: Int, val factory: Factory)
private data class Move(val source: Int, val target: Int, val things: Collection<Thing>)

private val componentRegex = "([^ ]+)( generator|-compatible microchip)".toRegex()

private fun getInput() = getLines()
		.mapIndexed { index, line ->
			index to componentRegex.findAll(line).map { matchResult ->
				matchResult.groupValues[1].let { material ->
					if (matchResult.groupValues[2] == " generator")
						Generator(material)
					else
						Microchip(material)
				}
			}.toSet()
		}.toMap().let(::Factory)

private fun getLines(day: Int = 11) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
