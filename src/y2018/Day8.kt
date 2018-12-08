package y2018

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(8)
		.parseTree()
		.all()
		.flatMap { it.metadata }
		.sum()

private fun part2() = readInput(8)
		.parseTree()
		.value()

private fun Sequence<String>.parseTree() =
		single()
				.split(" ")
				.map(Integer::parseInt)
				.iterator()
				.let(::parseNode)

private fun parseNode(data: Iterator<Int>): Node =
		data.next().let { childNodeCount ->
			data.next().let { metadataCount ->
				val childNodes = (0 until childNodeCount).map { parseNode(data) }
				val metadata = (0 until metadataCount).map { data.next() }
				Node(childNodes, metadata)
			}
		}

data class Node(val children: List<Node>, val metadata: List<Int>)

fun Node.all(): List<Node> = listOf(this) + children.flatMap { it.all() }

fun Node.value(): Int = if (children.isEmpty())
	metadata.sum()
else
	metadata.map { index ->
		if (index <= children.size)
			children[index - 1].value()
		else
			0
	}.sum()
