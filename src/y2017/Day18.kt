package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
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

private data class DuetCpu(val registers: MutableMap<Char, Long> = mutableMapOf(), val position: Int = 0, val lastPlayedSound: Long = -1, val lastRecoveredSound: Long = -1, val incomingValues: MutableList<Long> = mutableListOf(), val sendCount: Int = 0)

private fun DuetCpu.advance(steps: Int = 1) = copy(position = position + steps)
private fun DuetCpu.playSound(value: Long) = copy(lastPlayedSound = value)
private fun DuetCpu.recoverSound(registerOrValue: String) = if (this[registerOrValue] != 0L) copy(lastRecoveredSound = lastPlayedSound) else this
private operator fun DuetCpu.get(registerOrValue: String) = registerOrValue.toLongOrNull() ?: registers.getOrDefault(registerOrValue.first(), 0)
private operator fun DuetCpu.set(register: String, value: Long) = copy(registers = registers.apply { put(register.first(), value) })
private fun DuetCpu.send(registerOrValue: String, receiver: DuetCpu) = this.also { receiver.incomingValues += it[registerOrValue] }.copy(sendCount = sendCount + 1)
private fun DuetCpu.receive(register: String) = if (incomingValues.isNotEmpty()) set(register, incomingValues.removeAt(0)) else null
private fun DuetCpu.execute(commands: List<String>, otherCpu: DuetCpu) =
		position.takeUnless { (it < 0) or (it > commands.size) }
				?.let { commands[it] }
				?.split(" ")
				?.let { parts ->
					when (parts.first()) {
						"snd" -> send(parts[1], otherCpu).advance()
						"set" -> set(parts[1], this[parts[2]]).advance()
						"add" -> set(parts[1], this[parts[1]] + this[parts[2]]).advance()
						"mul" -> set(parts[1], this[parts[1]] * this[parts[2]]).advance()
						"mod" -> set(parts[1], this[parts[1]] % this[parts[2]]).advance()
						"rcv" -> receive(parts[1])?.advance()
						"jgz" -> advance(if (this[parts[1]] > 0) this[parts[2]].toInt() else 1)
						else -> throw RuntimeException("$this,$parts")
					}
				}

private fun part2(commands: List<String> = getInput()) =
		generateSequence(DuetCpu().set("p", 0) to DuetCpu().set("p", 1)) { (cpu1, cpu2) ->
			val cpu1After = cpu1.execute(commands, cpu2)
			val cpu2After = cpu2.execute(commands, cpu1)
			when {
				(cpu1After == null) and (cpu2After == null) -> null
				else -> (cpu1After ?: cpu1) to (cpu2After ?: cpu2)
			}
		}.last().second.sendCount
