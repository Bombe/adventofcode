package y2018

fun main(args: Array<String>) {
	timed { part1() }
	timed { part2() }
}

private fun part1() = readInput(16)
		.parseExamples()
		.collectPotentialOpcodes()
		.count { it.second.size >= 3 }

private fun part2() = readInput(16)
		.parseExamples()
		.collectPotentialOpcodes()
		.map { (example, opcode) -> example.instruction.first() to opcode }
		.groupBy({ it.first }, { it.second })
		.mapValues { it.value.flatten().distinct() }
		.deduceOpcodes()
		.let { opcodeMap ->
			readInput(16)
					.parseTestProgram()
					.fold(Cpu()) { cpu, instruction ->
						cpu.execute(opcodeMap[instruction.first()]!!.single(), instruction[1], instruction[2], instruction[3])
					}
		}.registers[0]

private fun Sequence<String>.parseExamples() = toList()
		.chunked(4)
		.fold(emptyList<Example>()) { prev, current ->
			Regex("Before: \\[(.*), (.*), (.*), (.*)]").matchEntire(current[0])
					?.groupValues
					?.drop(1)
					?.map(Integer::parseInt)
					?.let { before ->
						current[1].split(" ")
								.map(Integer::parseInt)
								.let { instruction ->
									Regex("After: {2}\\[(.*), (.*), (.*), (.*)").matchEntire(current[2])
											?.groupValues
											?.drop(1)
											?.map(Integer::parseInt)
											?.let { Example(instruction, before, it) }
								}
					}?.let { prev + it }
					?: prev
		}

private fun Sequence<String>.parseTestProgram() = toList()
		.fold(false to false to emptyList<List<Int>>()) { (parseFlags, instructions), currentLine ->
			val (inTestProgram, lastLineEmpty) = parseFlags
			when {
				inTestProgram && currentLine.isEmpty() -> parseFlags to instructions
				inTestProgram -> parseFlags to instructions.plusElement(currentLine.split(" ").map(Integer::parseInt))
				currentLine.isEmpty() -> (lastLineEmpty to !lastLineEmpty) to instructions
				else -> (false to false) to instructions
			}
		}.second

private fun List<Example>.collectPotentialOpcodes() =
		map { example ->
			example to Opcode.values().filter { opcode ->
				Cpu(example.before)
						.execute(opcode, example.instruction[1], example.instruction[2], example.instruction[3])
						.takeIf { cpu -> cpu.registers == example.after } != null
			}
		}

private fun Map<Int, List<Opcode>>.deduceOpcodes() =
		toMutableMap().also { potentialOpcodes ->
			while (potentialOpcodes.any { it.value.size > 1 }) {
				potentialOpcodes.filter { it.value.size == 1 }
						.forEach { opcode ->
							potentialOpcodes
									.filterKeys { it != opcode.key }
									.filterValues { opcode.value.single() in it }
									.forEach { it -> potentialOpcodes[it.key] = it.value - opcode.value.single() }
						}
			}
		}.toMap()

private data class Example(val instruction: List<Int>, val before: List<Int>, val after: List<Int>)

private data class Cpu(val registers: List<Int> = listOf(0, 0, 0, 0)) {

	fun execute(opcode: Opcode, a: Int, b: Int, c: Int) = opcode(this, a, b, c)

}

private enum class Opcode(private val execution: (Cpu, Int, Int, Int) -> Cpu) {
	eqri({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, if (cpu.registers[a] == b) 1 else 0)) }),
	addr({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] + cpu.registers[b])) }),
	addi({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] + b)) }),
	mulr({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] * cpu.registers[b])) }),
	muli({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] * b)) }),
	banr({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] and cpu.registers[b])) }),
	bani({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] and b)) }),
	borr({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] or cpu.registers[b])) }),
	bori({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a] or b)) }),
	setr({ cpu, a, _, c -> cpu.copy(cpu.registers.replace(c, cpu.registers[a])) }),
	seti({ cpu, a, _, c -> cpu.copy(cpu.registers.replace(c, a)) }),
	gtir({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, if (a > cpu.registers[b]) 1 else 0)) }),
	gtri({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, if (cpu.registers[a] > b) 1 else 0)) }),
	gtrr({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, if (cpu.registers[a] > cpu.registers[b]) 1 else 0)) }),
	eqir({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, if (a == cpu.registers[b]) 1 else 0)) }),
	eqrr({ cpu, a, b, c -> cpu.copy(cpu.registers.replace(c, if (cpu.registers[a] == cpu.registers[b]) 1 else 0)) });

	operator fun invoke(cpu: Cpu, a: Int, b: Int, c: Int) = execution(cpu, a, b, c)

}
