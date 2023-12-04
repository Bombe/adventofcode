package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput1() = readInput(10).first().split(",").map(String::toInt)
private fun getInput2() = readInput(10).first().map(Char::toByte)

private fun part1(hashSize: Int = 256, input: List<Int> = getInput1()) = input
		.fold(Hasher((0 until hashSize).toList())) { (elements, position, skipSize), length ->
			Hasher(elements.reverse(position, length),
					(position + length + skipSize) % elements.size, skipSize + 1)
		}.elements.let { it[0] * it[1] }

private fun part2(hashSize: Int = 256, input: List<Byte> = getInput2()) = knotHash(input, hashSize)
		.map { Integer.toHexString(it.toInt() and 0xff) }
		.map { if (it.length < 2) "0" + it else it }
		.joinToString("")

fun knotHash(input: List<Byte>, hashSize: Int = 256) =
		(input + listOf<Byte>(17, 31, 73, 47, 23)).loop(64)
				.fold(Hasher((0 until hashSize).toList())) { (elements, position, skipSize), length ->
					Hasher(elements.reverse(position, length.toInt()),
							(position + length + skipSize) % elements.size, skipSize + 1)
				}.elements.chunked(16)
				.map { it.fold(0) { prev, current -> prev xor current }.toByte() }

private data class Hasher(val elements: List<Int>, val position: Int = 0, val skipSize: Int = 0)

private fun <T> List<T>.reverse(start: Int, length: Int) =
		if (start + length > size) {
			(this + this).let { doubleList ->
				(doubleList.subList(0, start) +
						doubleList.subList(start, start + length).reversed() +
						doubleList.subList(start + length, doubleList.size)).let { fullList ->
					fullList.subList(size, size + ((start + length) % size)) + fullList.subList((start + length) % size, size)
				}
			}
		} else {
			subList(0, start) + subList(start, start + length).reversed() + subList(start + length, size)
		}
