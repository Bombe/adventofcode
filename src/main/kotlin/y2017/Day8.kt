package y2017

import kotlin.math.max

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(8).map(::toInstruction).toList()

private fun toInstruction(line: String) = Regex("(.*) (.*) (.*) if (.*) (.*) (.*)")
		.matchEntire(line)!!
		.let {
			Instruction(
					it.groupValues[1],
					it.groupValues[3].toInt() * if (it.groupValues[2] == "inc") 1 else -1,
					it.groupValues[4],
					when (it.groupValues[5]) {
						"==" -> { i -> i == it.groupValues[6].toInt() }
						"!=" -> { i -> i != it.groupValues[6].toInt() }
						">" -> { i -> i > it.groupValues[6].toInt() }
						">=" -> { i -> i >= it.groupValues[6].toInt() }
						"<" -> { i -> i < it.groupValues[6].toInt() }
						"<=" -> { i -> i <= it.groupValues[6].toInt() }
						else -> { _ -> false }
					}
			)
		}

data class Instruction(val register: String, val amount: Int, val conditionRegister: String, val condition: (Int) -> Boolean)

private fun part1(instructions: List<Instruction> = getInput()) =
		instructions.fold(emptyMap<String, Int>()) { registers, instruction ->
			registers.process(instruction)
		}.maxBy { it.value }!!

private fun part2(instructions: List<Instruction> = getInput()) =
		instructions.fold(0 to emptyMap<String, Int>()) { (highestValue, registers), instruction ->
			registers.process(instruction)
					.let { max(highestValue, (it.maxBy { it.value }?.value ?: 0)) to it }
		}.first

private fun Map<String, Int>.process(instruction: Instruction) =
		if (instruction.condition(this[instruction.conditionRegister] ?: 0))
			this + (instruction.register to ((this[instruction.register] ?: 0) + instruction.amount))
		else
			this
