package y2017

import kotlin.math.absoluteValue

fun main(args: Array<String>) {
	part1().println()
}

private fun getInput() = readInput(3).first().toInt()

private fun part1(input: Int = getInput()) =
		input.sqrt.let { squareRoot ->
			(input - squareRoot * squareRoot).let { diff ->
				when (diff) {
					0 -> (squareRoot / 2) + (squareRoot / 2 - 1 + diff.odd())
					in 1..squareRoot -> (squareRoot / 2 + diff.odd()) + (diff - (squareRoot / 2) - 1).absoluteValue
					else -> (squareRoot / 2 + diff.odd()) + ((squareRoot + squareRoot / 2 + 1 + diff.odd()) - diff).absoluteValue
				}
			}
		}

private fun Int.odd() = this and 1
