/**
 * adventofcode - Day4.kt - Copyright © 2019 David ‘Bombe’ Roden
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

import kotlin.streams.*

fun main() {
	part1().println()
	part2().println()
}

private fun part1() = input
		.filter(::twoAdjacentEqualDigits)
		.filter(::onlyIncrementingDigits)
		.size

private fun twoAdjacentEqualDigits(number: Int) =
		number.toString().chars().toList().fold(false to -1) { (found, lastDigit), digit ->
			(found || (lastDigit == digit)) to digit
		}.first

private fun onlyIncrementingDigits(number: Int) =
		number.toString().fold(true to 0.toChar()) { (found, lastDigit), digit ->
			(found && (digit >= lastDigit)) to digit
		}.first

private fun part2() = input
		.filter(::onlyTwoAdjacentEqualDigits)
		.filter(::onlyIncrementingDigits)
		.size

private fun onlyTwoAdjacentEqualDigits(number: Int) =
		number.toString().fold(mutableListOf<String>()) { groups, digit ->
			if (groups.lastOrNull()?.last() == digit) {
				groups.apply { set(lastIndex, last() + digit) }
			} else {
				groups.apply { add(digit.toString()) }
			}
		}.any { it.length == 2 }

private val input
	get() = readInput(4)
			.single()
			.split('-')
			.map(String::toInt)
			.let { it[0].rangeTo(it[1]) }
