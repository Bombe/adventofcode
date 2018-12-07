package y2018

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(7)
		.parseInstructions()
		.getExecutionList()
		.joinToString("")

private fun part2() = readInput(7)
		.parseInstructions()
		.getExecutionTime()

fun Instructions.getExecutionList() =
		let { instructions ->
			var newInstructions = instructions
			val steps = mutableListOf<Char>()
			while (newInstructions.isNotEmpty()) {
				steps += newInstructions.nextStepWithoutDeps().also { step ->
					newInstructions = newInstructions.removeStep(step)
				}
			}
			steps
		}

fun Instructions.getExecutionTime() =
		let { instructions ->
			var time = 0
			val workers = mutableListOf<Pair<Char, Int>>()
			var newInstructions = instructions
			while (newInstructions.isNotEmpty()) {
				workers
						.filter { worker -> worker.second == time }
						.also { finishedWorkers ->
							finishedWorkers.forEach { finishedWorker ->
								workers.remove(finishedWorker)
								newInstructions = newInstructions.removeStep(finishedWorker.first)
							}
						}
				if (newInstructions.isEmpty()) {
					break
				}
				val nextSteps = newInstructions.getStepsWithoutDeps()
						.filter { it !in workers.map { it.first } }
						.toMutableList()
				while ((workers.size < 5) && nextSteps.isNotEmpty()) {
					val nextStep = nextSteps.removeAt(0)
					workers += nextStep to (time + 60 + nextStep.toInt() - 'A'.toInt() + 1)
				}
				time = workers.map { it.second }.min()!!
			}
			time
		}

private fun Sequence<String>.parseInstructions() =
		map {
			Regex("Step (.) must be finished before step (.) can begin.")
					.matchEntire(it)!!
					.groupValues
					.let { it[2][0] to it[1][0] }
		}.groupBy(Pair<Char, Char>::first, Pair<Char, Char>::second)
				.let { instructions ->
					instructions + instructions.values.flatten()
							.filter { it !in instructions.keys }
							.map { it to listOf<Char>() }
				}

typealias Instructions = Map<Char, List<Char>>

fun Instructions.nextStepWithoutDeps() =
		getStepsWithoutDeps()
				.first()

fun Instructions.getStepsWithoutDeps() =
		filter { it.value.isEmpty() }
				.keys
				.sorted()

fun Instructions.removeStep(step: Char) =
		filterKeys { it != step }
				.mapValues { it.value.filter { it != step } }
