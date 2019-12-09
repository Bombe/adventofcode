/**
 * adventofcode - Day7.kt - Copyright © 2019 David ‘Bombe’ Roden
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

import y2015.*
import y2018.*
import java.util.concurrent.atomic.*

fun main() {
	part1().println()
	part2().println()
}

private fun part1() =
		Permutations.permutate(listOf(0, 1, 2, 3, 4), Int::compareTo).map { phases ->
			var power = 0L
			phases.forEach {
				power = IntCode(input.toMutableMap()).runForPhase(it.toLong(), power)
			}
			power
		}.max()

private fun part2() =
		Permutations.permutate(listOf(5, 6, 7, 8, 9), Int::compareTo).map { phases ->
		//listOf(9, 8, 7, 6, 5).let { phases ->
			val inputs = phases.mapIndexed { index, phase -> if (index == 0) mutableListOf(phase.toLong(), 0L) else mutableListOf(phase.toLong()) }.toMutableList()
			val amplifiers = phases.mapIndexed { index, _ -> index to IntCode(input.toMutableMap()) }.toMutableList()
			var maybeFinalOutput: Long? = null
			var finalOutput: Long? = null
			do {
				amplifiers.forEach { (index, amplifier) ->
					var output: Long? = null
					val newAmplifier = amplifier.loopUntil { intCode ->
						intCode.exec({ inputs[index].removeAt(0) }, { output = it })?.let { (output != null) to it } ?: true to intCode
					}
					amplifiers[index] = index to newAmplifier
					if (index == amplifiers.lastIndex) {
						if (output != null) {
							maybeFinalOutput = output
						} else {
							finalOutput = maybeFinalOutput
						}
					}
					inputs[(index + 1) % amplifiers.size].add(output ?: 0)
				}
			} while (finalOutput == null)
			finalOutput!!
		}.max()

private fun IntCode.runForPhase(phase: Long, previousPower: Long = 0): Long =
		AtomicLong().apply {
			runUntilFirstOutput(listOf(phase, previousPower)) { set(it) }
		}.get()

private val testInput = listOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
		27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5)
private val input = readInput(7).single().split(",").map(String::toLong).mapIndexed { index, l -> index to l }.toMap()
