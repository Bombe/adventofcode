/**
 * adventofcode - Day8.kt - Copyright © 2019 David ‘Bombe’ Roden
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
	part1.println()
	part2.println()
}

private val part1
	get() = input
			.minBy { layer -> layer.count { pixel -> pixel == '0' } }!!
			.let { layer -> layer.count { pixel -> pixel == '1' } * layer.count { pixel -> pixel == '2' } }

private val part2
	get() = input
			.reduce { image, layer ->
				image.mapIndexed { index, pixel ->
					if (pixel == '2') layer[index] else pixel
				}.joinToString("")
			}.chunked(width).joinToString("\n")

private const val width = 25
private const val height = 6

private val input = readInput(8).single()
		.chunkedSequence(width * height)
