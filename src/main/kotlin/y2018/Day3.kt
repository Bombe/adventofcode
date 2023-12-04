package y2018

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun part1() = readInput(3)
		.map(::parseClaim)
		.fold(IntArray(1000 * 1000)) { fabric, claim ->
			(0 until claim.width).forEach { x ->
				(0 until claim.height).forEach { y ->
					fabric[claim.left + x + (claim.top + y) * 1000]++
				}
			}
			fabric
		}
		.filter { it > 1 }
		.count()

private fun part2() = readInput(3)
		.map(::parseClaim)
		.toList()
		.let { claims ->
			val fabric = claims.fold(IntArray(1000 * 1000)) { fabric, claim ->
				(0 until claim.width).forEach { x ->
					(0 until claim.height).forEach { y ->
						fabric[claim.left + x + (claim.top + y) * 1000]++
					}
				}
				fabric
			}
			claims.single { it.squares().all { square -> fabric[square] == 1 } }
		}

private fun parseClaim(line: String) = Regex("#([\\d]+) @ ([\\d]+),([\\d]+): ([\\d]+)x([\\d]+)")
		.matchEntire(line)!!
		.groupValues
		.drop(1)
		.map(Integer::parseInt)
		.let { Claim(it[0], it[1], it[2], it[3], it[4]) }

private fun Claim.squares() = IntArray(width * height).apply {
	(0 until height).forEach { y ->
		(0 until width).forEach { x ->
			this[y * width + x] = (top + y) * 1000 + (left + x)
		}
	}
}

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int)
