package y2017

fun main(args: Array<String>) {
	part1(Turing(
			12208951,
			mapOf(
					0 to State(Reaction(1, 1, 1), Reaction(0, -1, 4)),
					1 to State(Reaction(1, -1, 2), Reaction(0, 1, 0)),
					2 to State(Reaction(1, -1, 3), Reaction(0, 1, 2)),
					3 to State(Reaction(1, -1, 4), Reaction(0, -1, 5)),
					4 to State(Reaction(1, -1, 0), Reaction(1, -1, 2)),
					5 to State(Reaction(1, -1, 4), Reaction(1, 1, 0))
			)
	)).println()
}

private data class Turing(val steps: Int = 0, val states: Map<Int, State> = emptyMap())

private data class State(val onZero: Reaction, val onOne: Reaction)
private operator fun State.get(state: Int) = if (state == 0) onZero else onOne

private data class Reaction(val write: Int, val direction: Int, val nextState: Int)

private data class Machine(val tape: MutableMap<Int, Int> = mutableMapOf(), val position: Int = 0, val state: Int = 0, val stepCount: Int = 0)
private fun Machine.valueAtTape() = if (tape[position] == 1) 1 else 0
private fun Machine.process(reaction: Reaction) = copy(tape = tape.apply { this[position] = reaction.write }, position = position + reaction.direction, state = reaction.nextState, stepCount = stepCount + 1)

private fun part1(problem: Turing) =
		generateSequence(Machine()) { machine ->
			machine
					.takeIf { it.stepCount != problem.steps }
					?.let {
						problem.states[machine.state]!![machine.valueAtTape()].let(machine::process)
					}
		}.last().tape.values.count { it == 1 }
