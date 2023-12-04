/**
 * adventofcode - Day16.kt - Copyright © 2019 David ‘Bombe’ Roden
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
import kotlin.math.*
import kotlin.streams.*

fun main() {
	//part1().println()
	part2().println()
}

private fun part1() = input
		.let { sequence ->
			var newInput = sequence
			(0..99).forEach { loop ->
				newInput = newInput.fft()
			}
			newInput.subList(0, 8).joinToString("")
		}

private fun List<Int>.fft() =
		mapIndexed { index, _ ->
			abs(mapIndexed { singleIndex, digit -> digit * phase(index, singleIndex) }.sum()) % 10
		}

private fun phase(n: Int, digit: Int) = listOf(0, 1, 0, -1)
		.let {
			it[((digit + 1) / (n + 1)) % it.size]
		}

private fun part2() = testInput.loop(10000)
		.let { sequence ->
			val offset = sequence.take(7).joinToString("").toInt()
			var newInput = sequence
			(0..99).forEach { loop ->
				newInput = newInput.fft()
				newInput.subList(0, 8).joinToString("").println()
			}
			newInput.subList(offset, offset + 8).joinToString("")
		}

private val testInput = "19617804207202209144916044189917".chars().map { it - 48 }.toList()

private val input = readInput(16).single().chars().map { it - 48 }.toList()
