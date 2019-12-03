/**
 * adventofcode - Day3.kt - Copyright © 2019 David ‘Bombe’ Roden
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

private fun part1() = getWirePaths()
		.let { wirePaths ->
			wirePaths[0]
					.intersect(wirePaths[1])
					.filterNot { it == Coordinate(0, 0) }
		}
		.map { it.manhattanDistanceTo() }
		.min()

private fun part2() = getWirePaths()
		.let { wirePaths ->
			wirePaths[0]
					.intersect(wirePaths[1])
					.filterNot { it == Coordinate(0, 0) }
					.map {
						wirePaths[0].indexOf(it) + wirePaths[1].indexOf(it)
					}
		}
		.min()

private fun getWirePaths(): List<MutableList<Coordinate>> {
	return readInput(3)
			.toList()
			.map { it.split(",") }
			.map { it.map { it[0] to it.substring(1).toInt() } }
			.map {
				it.fold(mutableListOf(Coordinate(0, 0))) { coordinates, vector ->
					coordinates.last().let { coordinate ->
						when (vector.first) {
							'U' -> coordinates.apply { addAll(1.rangeTo(vector.second).map { coordinate.copy(second = coordinate.second - it) }) }
							'D' -> coordinates.apply { addAll(1.rangeTo(vector.second).map { coordinate.copy(second = coordinate.second + it) }) }
							'L' -> coordinates.apply { addAll(1.rangeTo(vector.second).map { coordinate.copy(first = coordinate.first - it) }) }
							'R' -> coordinates.apply { addAll(1.rangeTo(vector.second).map { coordinate.copy(first = coordinate.first + it) }) }
							else -> throw IllegalArgumentException()
						}
					}
				}
			}
}
