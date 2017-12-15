package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(15)
		.map { it.split(" ").last() }
		.map(String::toLong)
		.toList()

private val startValueA get() = getInput()[0]
private val startValueB get() = getInput()[1]

private val multiplicatorA = 16807
private val multiplicatorB = 48271
private val divider = 2147483647

private fun part1(firstValueA: Long = startValueA, firstValueB: Long = startValueB) =
		generateSequence(firstValueA to firstValueB) { (valueA, valueB) ->
			(valueA * multiplicatorA % divider) to (valueB * multiplicatorB % divider)
		}.drop(1)
				.take(40000000)
				.count { (it.first and 0xffff) == (it.second and 0xffff) }

private fun acceptableForA(value: Long) = (value % 4) == 0L
private fun acceptableForB(value: Long) = (value % 8) == 0L

private fun part2(firstValueA: Long = startValueA, firstValueB: Long = startValueB) =
		generateSequence(firstValueA) { valueA ->
			(valueA * multiplicatorA % divider)
		}.drop(1).filter(::acceptableForA)
				.zip(generateSequence(firstValueB) { valueB ->
					(valueB * multiplicatorB % divider)
				}.drop(1).filter(::acceptableForB))
				.take(5000000)
				.count { (it.first and 0xffff) == (it.second and 0xffff) }
