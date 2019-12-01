/**
 * adventofcode - Day1.kt - Copyright © 2019 David ‘Bombe’ Roden
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

fun main() {
	part1().println()
	part2().println()
}

private fun part1() = readInput(1)
		.map(String::toInt)
		.map(Int::requiredFuel)
		.sum()

private fun part2() = readInput(1)
		.map(String::toInt)
		.map(Int::requiredFuelIncludingFuel)
		.sum()

private val Int.requiredFuel get() = this / 3 - 2

private val Int.requiredFuelIncludingFuel get() = requiredFuel + generateSequence(requiredFuel.requiredFuel) { fuel ->
	fuel.requiredFuel.takeIf { it > 0 }
}.sum()
