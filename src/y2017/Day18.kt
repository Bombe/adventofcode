package y2017

fun main(args: Array<String>) {
	part1().println()
}

private fun getInput() = readInput(18).toList()

private fun part1(commands: List<String> = getInput()) =
		generateSequence(DuetCpu()) { cpu ->
			val (registers, position, lastPlayedSound, lastReceivedSound) = cpu
			if ((position < 0) or (position >= commands.size) or (lastReceivedSound > -1)) {
				null
			} else {
				commands[position].split(" ").let { parts ->
					when (parts.first()) {
						"snd" -> cpu.playSound(cpu[parts[1]]).advance()
						"set" -> cpu.set(parts[1], cpu[parts[2]]).advance()
						"add" -> cpu.set(parts[1], cpu[parts[1]] + cpu[parts[2]]).advance()
						"mul" -> cpu.set(parts[1], cpu[parts[1]] * cpu[parts[2]]).advance()
						"mod" -> cpu.set(parts[1], cpu[parts[1]] % cpu[parts[2]]).advance()
						"rcv" -> cpu.recoverSound(parts[1]).advance()
						"jgz" -> cpu.advance(if (cpu[parts[1]] > 0) cpu[parts[2]].toInt() else 1)
						else -> throw RuntimeException("($registers,$position,$lastPlayedSound,$lastReceivedSound),$parts")
					}
				}
			}
		}.last().lastRecoveredSound

private data class DuetCpu(val registers: MutableMap<Char, Long> = mutableMapOf(), val position: Int = 0, val lastPlayedSound: Long = -1, val lastRecoveredSound: Long = -1)

private fun DuetCpu.advance(steps: Int = 1) = copy(position = position + steps)
private fun DuetCpu.playSound(value: Long) = copy(lastPlayedSound = value)
private fun DuetCpu.recoverSound(registerOrValue: String) = if (this[registerOrValue] != 0L) copy(lastRecoveredSound = lastPlayedSound) else this
private operator fun DuetCpu.get(registerOrValue: String) = registerOrValue.toLongOrNull() ?: registers.getOrDefault(registerOrValue.first(), 0)
private operator fun DuetCpu.set(register: String, value: Long) = copy(registers = registers.apply { put(register.first(), value) })

