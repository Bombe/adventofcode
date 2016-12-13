package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(): Int {
	var cpu = Cpu()
	val input = getLines()
	while (cpu.ip < input.size) {
		cpu = cpu.process(input[cpu.ip])
	}
	return cpu.a
}

private fun second(): Int {
	var cpu = Cpu(c = 1)
	val input = getLines()
	while (cpu.ip < input.size) {
		cpu = cpu.process(input[cpu.ip])
	}
	return cpu.a
}

private data class Cpu(val a: Int = 0, val b: Int = 0, val c: Int = 0, val d: Int = 0, val ip: Int = 0) {

	fun process(command: String) = when {
		command.startsWith("cpy") -> command.split(' ').let { cpy(it[1], it[2]) }
		command.startsWith("inc") -> command.split(' ').let { inc(it[1]) }
		command.startsWith("dec") -> command.split(' ').let { inc(it[1], -1) }
		command.startsWith("jnz") -> command.split(' ').let { jnz(it[1], it[2].toInt()) }
		else -> throw IllegalArgumentException()
	}

	private fun cpy(source: String, register: String) = when (register) {
		"a" -> copy(a = getValue(source), ip = ip + 1)
		"b" -> copy(b = getValue(source), ip = ip + 1)
		"c" -> copy(c = getValue(source), ip = ip + 1)
		"d" -> copy(d = getValue(source), ip = ip + 1)
		else -> throw IllegalArgumentException()
	}

	private fun inc(register: String, increment: Int = 1) = when (register) {
		"a" -> copy(a = a + increment, ip = ip + 1)
		"b" -> copy(b = b + increment, ip = ip + 1)
		"c" -> copy(c = c + increment, ip = ip + 1)
		"d" -> copy(d = d + increment, ip = ip + 1)
		else -> throw IllegalArgumentException()
	}

	private fun jnz(register: String, offset: Int) = if (getValue(register) != 0) copy(ip = ip + offset) else copy(ip = ip + 1)

	private fun getValue(source: String) = when (source) {
		"a" -> a
		"b" -> b
		"c" -> c
		"d" -> d
		else -> source.toInt()
	}
}

private fun getLines(day: Int = 12) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
