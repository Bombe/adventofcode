package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(17).first().toInt()

private fun part1(stepSize: Int = getInput()) =
		(1..2017).fold(mutableListOf(0) to 0) { (buffer, position), index ->
			val newPosition = (position + stepSize) % buffer.size
			buffer.apply { add(newPosition + 1, index) } to newPosition + 1
		}.first.let { it[it.indexOf(2017) + 1] }

private fun part2(stepSize: Int = getInput()) =
		(1..50000000).fold(Triple(1, 0, 0)) { (bufferSize, position, solution), index ->
			val newPosition = (position + stepSize) % bufferSize
			Triple(bufferSize + 1, newPosition + 1, if (newPosition == 0) index else solution)
		}.third
