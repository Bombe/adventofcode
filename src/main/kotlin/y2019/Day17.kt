/**
 * adventofcode - Day17.kt - Copyright © 2019 David ‘Bombe’ Roden
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
import kotlin.streams.*

fun main() {
	part1().println()
	part2().println()
}

private fun part1() = input.toIntCode()
		.let { intCode ->
			val view = StringBuilder()
			intCode.runUntilHalt(listOf(0L)) { view.append(it.toChar()) }
			//view.toString().println()
			view.toString().toGrid().let { grid ->
				grid.filter { position ->
					((position.key + Coordinate(0, -1)) in grid) &&
					((position.key + (0 to 1)) in grid) &&
					((position.key + (-1 to 0)) in grid) &&
					((position.key + (1 to 0)) in grid)
				}.map { it.key.x * it.key.y }
						.sum()
			}
		}

private fun part2() = input.toIntCode()
		.let { intCode ->
			var lastOutput = 0L
			intCode.memory[0] = 2
			intCode.runUntilHalt("A,C,C,A,B,A,A,B,C,B\nR,8,L,12,R,8\nR,8,L,8,L,8,R,8,R,10\nR,12,L,8,R,10\ny\n".chars().mapToLong { it.toLong() }.toList()) { lastOutput = it }
			lastOutput
		}

// R8, L12, R8, R12, L8, R10, R12, L8, R10, R8, L12, R8, R8, L8, L8, R8, R10, R8, L12, R8, R8, L12, R8, R8, L8, L8, R8, R10, R12, L8, R10, R8, L8, L8, R8, R10

// A,C,C,A,B,A,A,B,C,B
// A: R8, L12, R8,
// B: R8, L8, L8, R8, R10
// C: R12, L8, R10

private fun String.toGrid() = split("\n").mapIndexed { row, line ->
	line.chars().toList().mapIndexedNotNull { column, cell ->
		when (cell) {
			'#'.toInt(), '^'.toInt(), '<'.toInt(), '>'.toInt(), 'v'.toInt() -> Coordinate(column, row) to true
			else -> null
		}
	}
}.flatten().toMap()

private val input = readInput(17).toList()
