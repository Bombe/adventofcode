package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() = calculateA()
private fun second() = calculateA(1)

private fun calculateA(ignition: Int = 0): Int {
	var cpu = Cpu(c = ignition)
	val input = getLines()
	while (cpu.ip < input.size) {
		cpu = cpu.process(input[cpu.ip])
	}
	return cpu.a
}

private class Cpu private constructor(private val registers: Map<String, Int>, val ip: Int) {

	constructor(c: Int = 0) : this(mapOf("a" to 0, "b" to 0, "c" to c, "d" to 0), 0)

	val a: Int get() = registers["a"]!!

	fun process(command: String) = when {
		command.startsWith("cpy") -> command.split(' ').let { cpy(it[1], it[2]) }
		command.startsWith("inc") -> command.split(' ').let { inc(it[1]) }
		command.startsWith("dec") -> command.split(' ').let { inc(it[1], -1) }
		command.startsWith("jnz") -> command.split(' ').let { jnz(it[1], it[2].toInt()) }
		else -> throw IllegalArgumentException()
	}

	private fun cpy(source: String, register: String) = Cpu(registers + (register to getValue(source)), ip + 1)
	private fun inc(register: String, increment: Int = 1) = Cpu(registers + (register to getValue(register) + increment), ip + 1)
	private fun jnz(register: String, offset: Int) = Cpu(registers, ip + (if (getValue(register) != 0) offset else 1))
	private fun getValue(source: String) = if (source in registers) registers[source]!! else source.toInt()

}

private fun getLines(day: Int = 12) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
