package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(23)

private data class Day23Cpu(val registers: MutableMap<Char, Long> = mutableMapOf(), val pointer: Int = 0, val mulCounter: Int = 0)

private fun Day23Cpu.execute(instructions: List<String>) =
		pointer
				.takeIf { it in (0 until instructions.size) }
				?.let {
					instructions[it].split(" ").let { parts ->
						when (parts[0]) {
							"set" -> copy(registers = registers.apply { this[parts[1].first()] = getValue(parts[2]) }).next()
							"sub" -> copy(registers = registers.apply { this[parts[1].first()] = registers.getOrDefault(parts[1].first(), 0) - getValue(parts[2]) }).next()
							"mul" -> copy(registers = registers.apply { this[parts[1].first()] = registers.getOrDefault(parts[1].first(), 0) * getValue(parts[2]) }, mulCounter = mulCounter + 1).next()
							"jnz" -> copy(pointer = if (getValue(parts[1]) != 0L) (pointer + getValue(parts[2]).toInt()) else pointer + 1)
							else -> throw RuntimeException(this.toString())
						}
					}
				}

private fun Day23Cpu.next() = copy(pointer = pointer + 1)

private fun Day23Cpu.getValue(registerOrValue: String) =
		registerOrValue.toLongOrNull()
				?: registers.getOrDefault(registerOrValue.first(), 0)

private fun part1(instructions: List<String> = getInput().toList()) =
		generateSequence(Day23Cpu()) { cpu ->
			cpu.execute(instructions)
		}.last().mulCounter

private fun part2() =
		(108400.rangeTo(125400).step(17))
				.count { !it.prime }

private val Int.prime
	get() =
		(2..(this / 2)).none { this % it == 0 }

/**  translates assember for reference:

private fun _part2(): Int {
	var b = 108400
	var c = 125400
	var d: Int
	var e: Int
	var f: Boolean
	var h = 0
	while (true) {
		f = false
		d = 2
		do {
			e = 2
			do {
				f = f or (d * e == b)
				e += 1
			} while (e != b)
			d += 1
		} while (d != b)
		if (f) {
			h += 1
		}
		if (b == c) {
			return h
		}
		b += 17
	}
}

*/
