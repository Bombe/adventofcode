/**
 * adventofcode - Day5.kt - Copyright © 2019 David ‘Bombe’ Roden
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

private fun part1() = input.runWithFixedInput(1)

private fun part2() = input.runWithFixedInput(5)

private fun IntCode.runWithFixedInput(input: Int) =
		let {
			var lastOutput = 0
			loopUntil { intCode ->
				intCode.exec({ input }, { lastOutput = it })?.let { false to it } ?: (true to intCode)
			}
			lastOutput
		}


private val input
	get() = readInput(5).single().split(",").map(String::toInt).toMutableList().let { IntCode(it) }
