/**
 * adventofcode - Day9.kt - Copyright © 2019 David ‘Bombe’ Roden
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

import java.util.concurrent.atomic.*

fun main() {
	part1.println()
	part2.println()
}

private val part1
	get() = captureOutput { IntCode(input.toMutableMap()).runUntilHalt(listOf(1L), it) }

private val part2
	get() = captureOutput { IntCode(input.toMutableMap()).runUntilHalt(listOf(2L), it) }

private val input = readInput(9).single().split(",").map(String::toLong).mapIndexed { index, l -> index to l }.toMap()

private fun captureOutput(run: ((Long) -> Unit) -> Unit) = AtomicLong().also { long ->
	run(long::set)
}.let(AtomicLong::get)
