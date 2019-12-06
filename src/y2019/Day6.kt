/**
 * adventofcode - Day6.kt - Copyright © 2019 David ‘Bombe’ Roden
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

private fun part1(): Int = input.keys
		.map { input.chain(it) }
		.map { it.size }
		.sum()

private fun part2() = input.let { orbitalMap ->
			val myOrbit = orbitalMap.chain("YOU")
			val santaOrbit = orbitalMap.chain("SAN")
			myOrbit.size + santaOrbit.size - (2 * commonOrbits(myOrbit.reversed(), santaOrbit.reversed()))
		}

private val input = readInput(6)
		.map { it.split(")") }
		.associate { it[1] to it[0] }

private fun Map<String, String>.chain(o: String) = generateSequence(o) {
	this[it]
}.drop(1).toList()

private fun commonOrbits(chain1: List<String>, chain2: List<String>) = chain1.zip(chain2)
		.dropLastWhile { it.first != it.second }
		.size
