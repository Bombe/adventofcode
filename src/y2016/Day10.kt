package y2016

fun main(args: Array<String>) {
	println(first().solution1)
	println(first().solution2)
}

private val botRegex = "bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)".toRegex()
private val initRegex = "value (\\d+) goes to bot (\\d+)".toRegex()

private fun MatchResult.initValue() = groupValues[2].toInt() to groupValues[1].toInt()

private fun MatchResult.instruction() =
		groupValues[1].toInt() to
				BotInstruction(
						if (groupValues[2] == "bot") groupValues[3].toInt() else null,
						if (groupValues[2] == "output") groupValues[3].toInt() else null,
						if (groupValues[4] == "bot") groupValues[5].toInt() else null,
						if (groupValues[4] == "output") groupValues[5].toInt() else null
				)

private data class BotInstruction(val lowBot: Int?, val lowOutput: Int?, val highBot: Int?, val highOutput: Int?)

private fun first() = getLines()
		.let { lines ->
			val initialState = lines
					.mapNotNull { initRegex.find(it)?.initValue() }
					.groupBy { it.first }
					.mapValues { it.value.map { it.second } }
					.let { State(bots = it) }
			var currentState = initialState.processInstructions(lines)
			while (currentState.solution1.isEmpty() || currentState.solution2.isEmpty()) {
				currentState = currentState.processInstructions(lines)
			}
			currentState
		}

private data class State(
		val bots: Map<Int, List<Int>> = emptyMap<Int, List<Int>>().withDefault { emptyList() },
		val outputs: Map<Int, List<Int>> = emptyMap<Int, List<Int>>().withDefault { emptyList() },
		val solution1: List<Int> = emptyList(),
		val solution2: List<Int> = emptyList()
)

private fun State.processInstructions(lines: List<String>) = lines
		.mapNotNull { botRegex.find(it)?.instruction() }
		.fold(this) { state, instruction ->
			val botIndex = instruction.first
			if (state.bots.getOrElse(botIndex, { emptyList() }).size < 2) {
				state
			} else {
				state.bots[botIndex]!!.sorted()
						.let { sortedValues ->
							State(
									state.bots + listOf(
											instruction.second.lowBot?.let { it to (state.bots.getOrElse(it, { emptyList() }) + sortedValues[0]) },
											instruction.second.highBot?.let { it to (state.bots.getOrElse(it, { emptyList() }) + sortedValues[1]) },
											botIndex to emptyList<Int>()
									).filterNotNull(),
									state.outputs + listOf(
											instruction.second.lowOutput?.let { it to outputs.getOrElse(it, { emptyList() }) + sortedValues[0] },
											instruction.second.highOutput?.let { it to outputs.getOrElse(it, { emptyList() }) + sortedValues[1] }
									).filterNotNull(),
									state.solution1 + listOf(if (sortedValues == listOf(17, 61)) botIndex else null).filterNotNull(),
									state.solution2 + listOf(
											if ((outputs[0]?.isNotEmpty() ?: false) && (outputs[1]?.isNotEmpty() ?: false) && (outputs[2]?.isNotEmpty() ?: false))
												outputs[0]!!.first() * outputs[1]!!.first() * outputs[2]!!.first() else null
									).filterNotNull()
							)
						}
			}
		}

private fun getLines(day: Int = 10) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
