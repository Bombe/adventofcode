package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(16).first().split(",").map(::toMove)

private fun toMove(move: String) = when (move[0]) {
	's' -> Spin(move.drop(1).toInt())
	'x' -> move.drop(1).split("/").map(String::toInt).let { Exchange(it[0], it[1]) }
	'p' -> move.drop(1).split("/").map { it[0] - 'a' + 1 }.let { Partner(it[0], it[1]) }
	else -> throw RuntimeException()
}

private sealed class Move {
	abstract fun execute(programs: MutableList<Int>): MutableList<Int>
}

private class Spin(private val offset: Int) : Move() {
	override fun execute(programs: MutableList<Int>) = programs.apply {
		(0 until offset).forEach { _ ->
			programs.add(0, programs.last())
			programs.removeAt(programs.size - 1)
		}
	}
}

private class Exchange(private val a: Int, private val b: Int) : Move() {
	override fun execute(programs: MutableList<Int>) = programs.apply {
		val valueA = this[a]
		this[a] = this[b]
		this[b] = valueA
	}
}

private class Partner(private val a: Int, private val b: Int) : Move() {
	override fun execute(programs: MutableList<Int>) = programs.apply {
		val indexA = indexOf(a)
		val indexB = indexOf(b)
		this[indexA] = b
		this[indexB] = a
	}
}

private fun performDanceMoves(programs: MutableList<Int>, moves: List<Move>) =
		moves.fold(programs) { programs_, move ->
			move.execute(programs_)
		}

private fun part1(numberOfPrograms: Int = 16, moves: List<Move> = getInput()) =
		performDanceMoves((1..numberOfPrograms).toMutableList(), moves)
				.joinToString("") { (it + 96).toChar().toString() }

private fun part2(numberOfPrograms: Int = 16, moves: List<Move> = getInput(), cache: MutableMap<List<Int>, List<Int>> = mutableMapOf()) =
		(1..1000000000).fold((1..numberOfPrograms).toList()) { programs, _ ->
			cache.getOrPut(programs) {
				performDanceMoves(programs.toMutableList(), moves).toList()
			}
		}.joinToString("") { (it + 96).toChar().toString() }
