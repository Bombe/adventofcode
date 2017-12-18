package y2017

fun main(args: Array<String>) {
	part1().println()
}

private fun getInput() = readInput(18).toList()

private fun part1(commands: List<String> = getInput()) =
		generateSequence(DuetCpu()) { (registers, position, lastPlayedSound, lastReceivedSound) ->
			if ((position < 0) or (position >= commands.size) or (lastReceivedSound > -1)) {
				null
			} else {
				commands[position].split(" ").let { parts ->
					when (parts.first()) {
						"snd" -> DuetCpu(registers, position + 1, getValue(registers, parts[1]), lastReceivedSound)
						"set" -> DuetCpu(registers.apply { put(parts[1].first(), getValue(registers, parts[2])) }, position + 1, lastPlayedSound, lastReceivedSound)
						"add" -> DuetCpu(registers.apply { put(parts[1].first(), getValue(registers, parts[1]) + getValue(registers, parts[2])) }, position + 1, lastPlayedSound, lastReceivedSound)
						"mul" -> DuetCpu(registers.apply { put(parts[1].first(), getValue(registers, parts[1]) * getValue(registers, parts[2])) }, position + 1, lastPlayedSound, lastReceivedSound)
						"mod" -> DuetCpu(registers.apply { put(parts[1].first(), getValue(registers, parts[1]) % getValue(registers, parts[2])) }, position + 1, lastPlayedSound, lastReceivedSound)
						"rcv" -> DuetCpu(registers, position + 1, lastPlayedSound, if (getValue(registers, parts[1]) != 0L) lastPlayedSound else lastReceivedSound)
						"jgz" -> DuetCpu(registers, if (getValue(registers, parts[1]) > 0) position + getValue(registers, parts[2]).toInt() else position + 1, lastPlayedSound, lastReceivedSound)
						else -> throw RuntimeException("($registers,$position,$lastPlayedSound,$lastReceivedSound),$parts")
					}
				}
			}
		}.last().lastReceivedSound

private data class DuetCpu(val registers: MutableMap<Char, Long> = mutableMapOf(), val position: Int = 0, val lastPlayedSound: Long = -1, val lastReceivedSound: Long = -1)

private fun getValue(registers: Map<Char, Long>, value: String) =
		value.toLongOrNull() ?: registers.getOrDefault(value.first(), 0)
