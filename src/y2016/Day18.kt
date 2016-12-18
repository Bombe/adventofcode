package y2016

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first() =
		generateSequence(firstRow(), ::getNextRow)
				.take(40)
				.map { it.toCharArray().count { it == '.' } }
				.sum()

private fun second() =
		generateSequence(firstRow(), ::getNextRow)
				.take(400000)
				.map { it.toCharArray().count { it == '.' } }
				.sum()

private fun getNextRow(row: String): String =
		".$row.".let { safeRow ->
			(1 until safeRow.length - 1).map { safeRow.slice((it - 1)..(it + 1)) }
		}.map { if (it[0] != it[2]) '^' else '.' }
				.joinToString("")

private fun firstRow() = ".^^^.^.^^^^^..^^^..^..^..^^..^.^.^.^^.^^....^.^...^.^^.^^.^^..^^..^.^..^^^.^^...^...^^....^^.^^^^^^^"
