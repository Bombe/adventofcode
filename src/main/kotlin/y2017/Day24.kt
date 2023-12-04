package y2017

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(24)
		.map { it.split("/") }
		.map { it.map(String::toInt) }
		.map { Component(it[0], it[1]) }
		.toList()

private data class Component(val port1: Int, val port2: Int)

private operator fun Component.contains(port: Int) = (port == port1) or (port == port2)
private fun Component.otherPort(port: Int) = if (port == port1) port2 else port1

private fun part1(components: List<Component> = getInput()) =
		tryToFit(components, 0)
				.maxBy(Bridge::weight)!!.weight

private fun part2(components: List<Component> = getInput()) =
		tryToFit(components, 0)
				.sortedByDescending(Bridge::size)
				.let { bridges ->
					bridges.filter { it.size == bridges[0].size }
				}.maxBy(Bridge::weight)!!.weight

private typealias Bridge = List<Component>
private val Bridge.weight get() = sumBy { it.port1 + it.port2 }

private fun tryToFit(remainingComponents: List<Component>, currentPort: Int = 0): List<Bridge> =
		remainingComponents.filter {
			currentPort in it
		}.let { matchingComponents: Bridge ->
			matchingComponents.map { listOf(it) } +
					matchingComponents.flatMap { matchingComponent ->
						tryToFit(remainingComponents - matchingComponent, matchingComponent.otherPort(currentPort))
								.map { listOf(matchingComponent) + it }
					}
		}
