/**
 * adventofcode - Day11.kt - Copyright © 2019 David ‘Bombe’ Roden
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
	part1.println()
	part2.println()
}

private val input = readInput(11).single().split(",").map(String::toLong)

private val part1 = paint(0).first.size

private val part2 = paint(1)
		.second
		.let(Collection<Coordinate>::moveToOrigin)
		.toImage()

private fun Collection<Coordinate>.moveToOrigin() = let { coordinates ->
	val moveRight = 0 - coordinates.map { it.x }.min()!!
	val moveDown = 0 - coordinates.map { it.y }.min()!!
	coordinates.map { it + (moveRight to moveDown) }
}

private fun Collection<Coordinate>.toImage() = let { coordinates ->
	val width = coordinates.map { it.x }.max()!!
	val height = coordinates.map { it.y }.max()!!
	(0..height).joinToString("\n") { row ->
		(0..width).map { column ->
			if (Coordinate(column, row) in coordinates) '#' else '.'
		}.joinToString("")
	}
}

private fun paint(startPanel: Long) = input
		.let { IntCode(it.mapIndexed { index, i -> index to i }.toMap().toMutableMap()) }
		.let { intCode ->
			val paintedPanels = mutableSetOf<Coordinate>()
			val whitePanels = mutableSetOf<Coordinate>()
			var currentLocation = Coordinate(0, 0)
			if (startPanel == 1L) {
				whitePanels += currentLocation
			}
			var direction = Direction.Up
			var nextColor: Long = -1
			var nextTurn: Long = -1
			var nextIntCode = intCode
			do {
				nextIntCode = nextIntCode.runUntilFirstOutput({ if (currentLocation in whitePanels) 1 else 0 }, { nextColor = it })
				nextIntCode = nextIntCode.runUntilFirstOutput({ if (currentLocation in whitePanels) 1 else 0 }, { nextTurn = it })
				if (!nextIntCode.willHalt) {
					paintedPanels += currentLocation
					when (nextColor) {
						0L -> whitePanels -= currentLocation
						1L -> whitePanels += currentLocation
						else -> throw IllegalArgumentException("$nextColor")
					}
					direction = when (nextTurn) {
						0L -> direction.left
						1L -> direction.right
						else -> throw IllegalArgumentException("$nextTurn")
					}
					currentLocation = direction.move(currentLocation)
				}
			} while (!nextIntCode.willHalt)
			paintedPanels to whitePanels
		}
