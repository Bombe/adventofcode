package y2016

fun main(args: Array<String>) {
	println(first())
}

private fun first(instructions: List<List<String>> = getInstructions()) =
		generateSequence(0) { it + 1 }
				.map { it to calculateThousandOutputs(instructions, it) }
				.filter { it.second.withIndex().all { indexedValue -> indexedValue.value == (indexedValue.index % 2) } }
				.first().first

private fun calculateThousandOutputs(instructions: List<List<String>>, a: Int) =
		generateSequence(Cpu3(a)) {
			if ((it.ip < instructions.size) && (it.out.size < 1000)) {
				it.process(instructions[it.ip])
			} else
				null
		}.last().out

private class Cpu3 private constructor(private val registers: IntArray, val ip: Int, val out: List<Int>) {

	constructor(a: Int = 0) : this(IntArray(4, { 0 }).apply { this[0] = a }, 0, emptyList())

	val a: Int get() = registers[0]

	fun process(command: List<String>) = when (command[0]) {
		"cpy" -> cpy(command[1], command[2])
		"inc" -> inc(command[1])
		"dec" -> inc(command[1], -1)
		"jnz" -> jnz(command[1], command[2])
		"out" -> out(command[1])
		else -> throw IllegalArgumentException()
	}

	private fun cpy(source: String, register: String) = Cpu3(registers.apply { this[register[0] - 'a'] = getValue(source) }, ip + 1, out)
	private fun inc(register: String, increment: Int = 1) = Cpu3(registers.apply { this[register[0] - 'a'] = getValue(register) + increment }, ip + 1, out)
	private fun jnz(register: String, offsetSource: String) = Cpu3(registers, ip + (if (getValue(register) != 0) getValue(offsetSource) else 1), out)
	private fun out(source: String) = Cpu3(registers, ip + 1, out + getValue(source))
	private fun getValue(source: String) = if (source in listOf("a", "b", "c", "d")) registers[source[0] - 'a'] else source.toInt()

}

private fun getInstructions(day: Int = 25) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
		.map { it.split(' ') }
