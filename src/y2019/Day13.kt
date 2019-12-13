/**
 * adventofcode - Day13.kt - Copyright © 2019 David ‘Bombe’ Roden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package y2019

import y2018.*

fun main() {
	part1().println()
	part2().println()
}

private fun part1() = input
		.let { intCode ->
			val screen = mutableMapOf<Coordinate, Int>()
			var x = 0
			var y = 0
			var tile = 0
			var nextIntCode = intCode
			do {
				nextIntCode = nextIntCode
						.runUntilFirstOutput({ 0L }) { x = it.toInt() }
						.runUntilFirstOutput({ 0L }) { y = it.toInt() }
						.runUntilFirstOutput({ 0L }) { tile = it.toInt() }
				screen[x to y] = tile
			} while (!nextIntCode.willHalt)
			screen.values.count { it == 2 }
		}

private fun part2() = input
		.let { intCode ->
			var nextIntCode = intCode
			var score = 0
			nextIntCode.memory[0] = 2
			do {
				var x = 0
				var y = 0
				var tile = 0
				var paddle = 0
				var ball = 0
				val paddleInput = when {
					ball.x > paddle -> 1L
					ball.x < paddle -> -1L
					else -> 0L
				}
				nextIntCode = nextIntCode
						.runUntilFirstOutput({ paddleInput }) { x = it.toInt() }
						.runUntilFirstOutput({ paddleInput }) { y = it.toInt() }
						.runUntilFirstOutput({ paddleInput }) { tile = it.toInt() }
				when {
					x == -1 && y == 0 -> score = tile
					tile == 3 -> paddle = x
					tile == 4 -> ball = x
				}
			} while (!nextIntCode.willHalt)
			score
		}

private val input = readInput(13)
		.single()
		.split(",")
		.map(String::toLong)
		.mapIndexed { index, l -> index to l }.toMap()
		.toMutableMap()
		.let { IntCode(it) }
