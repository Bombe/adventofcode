package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(program: List<List<String>> = getInstructions()): Int {
	return solveForA(program, 7)
}

private fun second(program: List<List<String>> = getInstructions()): Int {
	return solveForA(program, 12)
}

private fun solveForA(program: List<List<String>>, a: Int): Int {
	var cpu = Cpu2(a)
	val program = program.toMutableList()
	while (cpu.ip < program.size) {
		cpu = cpu.process(program[cpu.ip], program)
	}
	return cpu.a
}

private class Cpu2 private constructor(private val registers: IntArray, val ip: Int) {

	constructor(a: Int = 0) : this(IntArray(4, { 0 }).apply { this[0] = a }, 0)

	val a: Int get() = registers[0]

	fun process(command: List<String>, program: MutableList<List<String>>) = when (command[0]) {
		"cpy" -> cpy(command[1], command[2])
		"inc" -> inc(command[1])
		"dec" -> inc(command[1], -1)
		"jnz" -> jnz(command[1], command[2])
		"tgl" -> tgl(command[1], program)
		else -> throw IllegalArgumentException()
	}

	private fun cpy(source: String, register: String) = if (register in listOf("a", "b", "c", "d")) Cpu2(registers.apply { this[register[0] - 'a'] = getValue(source) }, ip + 1) else Cpu2(registers, ip + 1)
	private fun inc(register: String, increment: Int = 1) = Cpu2(registers.apply { this[register[0] - 'a'] = getValue(register) + increment }, ip + 1)
	private fun jnz(register: String, offsetSource: String) = Cpu2(registers, ip + (if (getValue(register) != 0) getValue(offsetSource) else 1))
	private fun tgl(register: String, program: MutableList<List<String>>) = Cpu2(registers, ip + 1).apply {
		val offset = this@Cpu2.ip + getValue(register)
		if (offset < program.size) {
			program[offset] = when (program[offset][0]) {
				"inc" -> program[offset].mapIndexed { index, code -> if (index == 0) "dec" else code }
				"dec" -> program[offset].mapIndexed { index, code -> if (index == 0) "inc" else code }
				"tgl" -> program[offset].mapIndexed { index, code -> if (index == 0) "inc" else code }
				"jnz" -> program[offset].mapIndexed { index, code -> if (index == 0) "cpy" else code }
				else -> program[offset].mapIndexed { index, code -> if (index == 0) "jnz" else code }
			}
		}
	}

	private fun getValue(source: String) = if (source in listOf("a", "b", "c", "d")) registers[source[0] - 'a']!! else source.toInt()

}

private fun getInstructions(day: Int = 23) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.map { it.split(' ') }
