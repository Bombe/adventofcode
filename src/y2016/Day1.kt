package y2016

class Day1 {

	private fun getProcessedInput() = getInput(1).flatMap { it.split(", ") }

	private fun Int.abs() = Math.abs(this)

	private fun getXMovement(direction: Int) = when (direction) {
		1 -> 1
		3 -> -1
		else -> 0
	}

	private fun getYMovement(direction: Int) = when (direction) {
		0 -> 1
		2 -> -1
		else -> 0
	}

	var x = 0
	var y = 0
	var direction = 0

	data class DirectedPosition(val x: Int = 0, val y: Int = 0, val direction: Int = 0)

	fun first() {
		val lastStop = getProcessedInput()
				.map { it.substring(0, 1) to it.substring(1).toInt() }
				.fold(DirectedPosition()) { previous, command ->
					val steps = command.second
					val newDirection = getNewDirection(previous.direction, command.first)
					DirectedPosition(
							previous.x + getXMovement(newDirection) * steps,
							previous.y + getYMovement(newDirection) * steps,
							newDirection
					)
				}
		println("${lastStop.x.abs() + lastStop.y.abs()}")
	}

	private fun getNewDirection(direction: Int, command: String) = when {
		command.startsWith("L") -> direction + 3
		command.startsWith("R") -> direction + 1
		else -> direction
	} % 4

	fun second() {
		getProcessedInput()
				.map { it.substring(0, 1) to it.substring(1).toInt() }
				.flatMap { command -> (1..command.second).map { command.first to 1 } }
				.fold(DirectedPosition()) { position, command ->
					val newDirection = getNewDirection(position.direction, command.first)
					DirectedPosition(
							position.x + getXMovement(newDirection) * command.second,
							position.y + getYMovement(newDirection) * command.second,
							newDirection
					)
				}
	}

	object First {

		@JvmStatic
		fun main(args: Array<String>) = Day1().first()

	}

	object Second {

		@JvmStatic
		fun main(args: Array<String>) = Day1().second()

	}

}
