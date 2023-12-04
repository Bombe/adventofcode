/**
 * adventofcode - Day2.kt - Copyright © 2019 David ‘Bombe’ Roden
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
	part2()
}

private fun part1() =
		readInput(2).first()
				.split(",")
				.map(String::toLong)
				.mapIndexed { index, l -> index to l }.toMap()
				.toMutableMap()
				.let { IntCode(it) }
				.runUntilFirstOutput(12, 2)
				.memory[0]

private fun IntCode.runUntilFirstOutput(noun: Int, verb: Int) =
		IntCode(memory.apply {
			this[1] = noun.toLong()
			this[2] = verb.toLong()
		})
				.loopUntil { intCode ->
					intCode.exec()?.let { false to it } ?: (true to intCode)
				}

private fun part2() = readInput(2)
		.first()
		.split(",")
		.map(String::toLong)
		.let {
			(0..99).forEach { noun ->
				(0..99).forEach { verb ->
					if (it.mapIndexed { index, l -> index to l }.toMap().toMutableMap().let { IntCode(it) }.runUntilFirstOutput(noun, verb).memory[0] == 19690720L) {
						println("$noun, $verb")
					}
				}
			}
		}
