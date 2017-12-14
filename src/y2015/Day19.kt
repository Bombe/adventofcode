package y2015

fun main(args: Array<String>) {
	println(first())
	println(second())
}

fun first() = getInput(19)
		.filter { !it.isEmpty() }
		.partition { "=>" in it }
		.let { partitions -> partitions.first to partitions.second.first() }
		.let { partitions ->
			partitions.first
					.map { it.split(" => ").let { it[0] to it[1] } }
					.fold(emptyMap<String, List<String>>()) { previousMap, replacementPair ->
						previousMap + (replacementPair.first to (previousMap.getOrElse(replacementPair.first, { emptyList() }) + replacementPair.second))
					}
					.flatMap { entry -> entry.value.map { entry.key to it } }
					.flatMap {
						partitions.second.replace(it.first, it.second)
					}
		}
		.distinct()
		.count()

fun second() = getInput(19)
		.filter { !it.isEmpty() }
		.partition { "=>" in it }
		.let { it.first to it.second.first() }
		.let { createMappings(it.first) to it.second }
		.let {
			var molecule = it.second
			while (molecule != "e") {
				if (it.first.none { it.second in molecule}) {
					molecule = it.second
				}
				it.first.forEach { molecule = molecule.replace(it.second, it.first, false) }
			}
		}

fun createMappings(rawMappings: List<String>) = rawMappings
		.map { it.split(" => ").let { it[0] to it[1] } }
		.fold(emptyMap<String, List<String>>()) { previousMap, replacementPair ->
			previousMap + (replacementPair.first to (previousMap.getOrElse(replacementPair.first, { emptyList() }) + replacementPair.second))
		}
		.flatMap { entry -> entry.value.map { entry.key to it } }

fun processAllReplacements(molecule: String, replacements: List<Pair<String, String>>, target: String): List<String> {
	return replacements.flatMap {
		molecule.replace(it.first, it.second)
	}.filter { it == target }
}

fun String.replace(needle: String, replacement: String): List<String> {
	val replaced = mutableListOf<String>()
	var index = indexOf(needle)
	while (index != -1) {
		replaced += substring(0, index) + replacement + substring(index + needle.length)
		index = indexOf(needle, index + 1)
	}
	return replaced
}

class Day19

fun getInput(day: Int) = Day19::class.java.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
