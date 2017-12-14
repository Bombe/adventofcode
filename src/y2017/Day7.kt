package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(7)
		.mapNotNull { Regex("^(.*) \\((.*)\\)( -> (.*))?$").find(it) }
		.map { Program(it.groupValues[1], it.groupValues[2].toInt(), it.groupValues[4].takeIf { it != "" }?.split(",")?.map(String::trim) ?: emptyList()) }
		.toList()

data class Program(val name: String, val weight: Int, val subPrograms: List<String>)

private fun part1(programs: List<Program> = getInput()) =
		programs
				.associateBy { it.name }
				.let { programsByName ->
					programsByName
							.flatMap { (name, program) ->
								program.subPrograms.map {
									it to programsByName[it]!!
								}
							}.toMap()
							.let { programsByParent ->
								programsByName.keys - programsByParent.keys
							}
				}

private fun part2(programs: List<Program> = getInput()) =
		programs
				.associateBy { it.name }
				.let { programsByName ->
					val weights = programs
							.map { it.name to totalWeight(it, programsByName) }
							.toMap()
					programs
							.filter { it.subPrograms.map { weights[it] }.distinct().size > 1 }
							.minBy { weights[it.name]!! }!!
							.let { unbalancedProgram ->
								unbalancedProgram.subPrograms
										.groupBy { weights[it]!! }
										.let { unbalancedWeights ->
											val correctWeight = unbalancedWeights.filter { it.value.size > 1 }.keys.first()
											val incorrectWeight = unbalancedWeights.filter { it.value.size == 1 }.keys.first()
											unbalancedWeights.filter { it.value.size == 1 }.values.first().first()
													.let(programsByName::get)!!
													.weight - (incorrectWeight - correctWeight)
										}
							}
				}

private fun totalWeight(program: Program, programsByName: Map<String, Program>): Int =
		program.weight + program.subPrograms.map { totalWeight(programsByName[it]!!, programsByName) }.sum()
