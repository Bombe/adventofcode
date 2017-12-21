package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(21)
		.map { it.split(" => ").map { it.replace("/", "") } }
		.map { it.map { Pattern(it) } }
		.map { it[0] to it[1] }
		.toList()
		.flatMap { (rule, target) ->
			listOf(
					rule to target,
					rule.rotate() to target,
					rule.rotate().rotate() to target,
					rule.rotate().rotate().rotate() to target,
					rule.flip() to target,
					rule.rotate().flip() to target,
					rule.rotate().rotate().flip() to target,
					rule.rotate().rotate().rotate().flip() to target
			)
		}.toMap()

private data class Pattern(val bits: String)

private val Pattern.size get() = bits.length.sqrt
private val Pattern.divisor get() = if (size.even) 2 else 3

private fun Pattern.rotate(): Pattern = (0 until bits.length).fold("") { result, index ->
	result + bits[size * (size - 1) + (index / size) - (index % size) * size]
}.let(::Pattern)

private fun Pattern.flip(): Pattern = (0 until bits.length).fold("") { result, index ->
	result + bits[(index / size) * size + (size - 1 - index % size)]
}.let(::Pattern)

private fun Pattern.split() =
		bits.foldIndexed(mutableMapOf<Int, MutableList<Char>>()) { index, largePixels, originalPixel ->
			((index / divisor) % (size / divisor) + (index / (size * divisor) * (size / divisor))).let {
				largePixels[it] = largePixels.getOrDefault(it, mutableListOf()).apply { add(originalPixel) }
			}
			largePixels
		}.entries.sortedBy { it.key }.map { it.value.joinToString("") }.map(::Pattern)

private fun List<Pattern>.combine() = if (size == 1) this[0] else
	(0 until first().bits.length * size).fold(mutableListOf<Char>()) { result, index ->
		(index / first().size % size.sqrt + index / (first().size.sqr * size.sqrt) * size.sqrt).let { element ->
			(index % first().size + ((index / (first().size * size.sqrt) * first().size) % first().size.sqr)).let { bit ->
				result.apply { add(this@combine[element].bits[bit]) }
			}
		}
	}.joinToString("").let { Pattern(it) }

private fun part1(replacementRules: Map<Pattern, Pattern> = getInput()) =
		solve(5, replacementRules)

private fun part2(replacementRules: Map<Pattern, Pattern> = getInput()) =
		solve(18, replacementRules)

private fun solve(iterations: Int, replacementRules: Map<Pattern, Pattern>): Int {
	return (1..iterations).fold(Pattern(".#...####")) { image, _ ->
		image
				.split()
				.map { replacementRules[it]!! }
				.combine()
	}.bits.count { it == '#' }
}
