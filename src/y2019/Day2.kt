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
				.map(String::toInt)
				.toMutableList()
				.let { IntCode(it) }
				.run(12, 2)
				.ints[0]

private fun IntCode.run(noun: Int, verb: Int) =
		IntCode(ints.apply {
			this[1] = noun
			this[2] = verb
		})
				.loopUntil { intCode ->
					intCode.exec()?.let { false to it } ?: (true to intCode)
				}

private fun part2() = readInput(2)
		.first()
		.split(",")
		.map(String::toInt)
		.let {
			(0..99).forEach { noun ->
				(0..99).forEach { verb ->
					if (it.toMutableList().let { IntCode(it) }.run(noun, verb).ints[0] == 19690720) {
						println("$noun, $verb")
					}
				}
			}
		}

class IntCode(val ints: MutableList<Int>, private val ip: Int = 0) {

	fun exec() = when (ints[ip]) {
		1 -> IntCode(ints.apply { set(ints[ip + 3], ints[ints[ip + 1]] + ints[ints[ip + 2]]) }, ip + 4)
		2 -> IntCode(ints.apply { set(ints[ip + 3], ints[ints[ip + 1]] * ints[ints[ip + 2]]) }, ip + 4)
		99 -> null
		else -> throw IllegalStateException()
	}

}
