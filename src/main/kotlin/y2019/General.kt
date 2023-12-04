/**
 * adventofcode - General.kt - Copyright © 2019 David ‘Bombe’ Roden
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
import y2019.Mode.*
import java.util.concurrent.atomic.*

fun readInput(day: Int): Sequence<String> {
	class Foo
	return Foo::class.java.getResourceAsStream("Input$day.txt").bufferedReader().lineSequence()
}

fun Any?.println() = println(this)

data class IntCode(val memory: MutableMap<Int, Long>, private val ip: Int = 0, private val relativeBase: Int = 0) {

	fun exec(input: () -> Long? = { 0 }, output: (Long) -> Unit = {}) = memory[ip]!!.toInstruction()
			.let { instruction ->
				when (instruction.opcode) {
					1 -> copy(memory = memory.apply { this[getOffset(ip + 3, instruction.mode3)] = getValue(ip + 1, instruction.mode1) + getValue(ip + 2, instruction.mode2) }, ip = ip + 4)
					2 -> copy(memory = memory.apply { this[getOffset(ip + 3, instruction.mode3)] = getValue(ip + 1, instruction.mode1) * getValue(ip + 2, instruction.mode2) }, ip = ip + 4)
					3 -> input()?.let { copy(memory = memory.apply { this[getOffset(ip + 1, instruction.mode1)] = it }, ip = ip + 2) } ?: throw IllegalStateException("no input")
					4 -> copy(memory = memory.apply { output(getValue(ip + 1, instruction.mode1)) }, ip = ip + 2)
					5 -> copy(memory = memory, ip = if (getValue(ip + 1, instruction.mode1) != 0L) getValue(ip + 2, instruction.mode2).toInt() else ip + 3)
					6 -> copy(memory = memory, ip = if (getValue(ip + 1, instruction.mode1) == 0L) getValue(ip + 2, instruction.mode2).toInt() else ip + 3)
					7 -> copy(memory = memory.apply { this[getOffset(ip + 3, instruction.mode3)] = if (getValue(ip + 1, instruction.mode1) < getValue(ip + 2, instruction.mode2)) 1L else 0L }, ip = ip + 4)
					8 -> copy(memory = memory.apply { this[getOffset(ip + 3, instruction.mode3)] = if (getValue(ip + 1, instruction.mode1) == getValue(ip + 2, instruction.mode2)) 1L else 0L }, ip = ip + 4)
					9 -> copy(ip = ip + 2, relativeBase = relativeBase + getValue(ip + 1, instruction.mode1).toInt())
					99 -> null
					else -> throw IllegalStateException("$this")
				}
			}

	val willHalt get() = memory[ip] == 99L

	private fun getValue(index: Int, mode: Mode = Positional) =
			(memory[index] ?: 0).let {
				when (mode) {
					Positional -> memory[it.toInt()]
					Immediate -> it
					Relative -> memory[(relativeBase + it).toInt()]
				}
			} ?: 0

	private fun getOffset(index: Int, mode: Mode = Positional): Int =
			(memory[index] ?: 0).let {
				when (mode) {
					Positional -> it
					Immediate -> throw IllegalArgumentException("write parameter in immediate mode: $this")
					Relative -> (relativeBase + it)
				}
			}?.toInt() ?: 0

}

enum class Mode { Positional, Immediate, Relative }

data class Instruction(val opcode: Int, val mode1: Mode, val mode2: Mode, val mode3: Mode)

fun Long.toInstruction() = Instruction((this % 100L).toInt(), values()[(this / 100 % 10).toInt()], values()[(this / 1000 % 10).toInt()], values()[(this / 10000 % 10).toInt()])

fun IntCode.runUntilFirstOutput(inputs: Iterable<Long>, outputs: (Long) -> Unit): IntCode {
	val inputIterator = inputs.iterator()
	val inputSupplier = { if (inputIterator.hasNext()) inputIterator.next() else null }
	return loopUntil { intCode ->
		val output = AtomicReference<Long>(null)
		val result = intCode.exec(inputSupplier, { output.set(it) })?.let { (output.get() != null) to it } ?: true to intCode
		output.get()?.let { outputs(it) }
		result
	}
}

fun IntCode.runUntilFirstOutput(inputs: () -> Long, outputs: (Long) -> Unit): IntCode {
	return loopUntil { intCode ->
		val output = AtomicReference<Long>(null)
		val result = intCode.exec(inputs, { output.set(it) })?.let { (output.get() != null) to it } ?: true to intCode
		output.get()?.let { outputs(it) }
		result
	}
}

fun IntCode.runUntilHalt(inputs: Iterable<Long>, outputs: (Long) -> Unit): IntCode {
	val inputIterator = inputs.iterator()
	val inputSupplier = { if (inputIterator.hasNext()) inputIterator.next() else null }
	return loopUntil { intCode ->
		intCode.exec(inputSupplier, outputs)?.let { false to it } ?: true to intCode
	}
}

enum class Direction {

	Up {
		override val left: Direction get() = Left
		override val right: Direction get() = Right
		override fun move(coordinate: Coordinate) = coordinate.copy(second = coordinate.second - 1)
	},
	Down {
		override val left: Direction get() = Right
		override val right: Direction get() = Left
		override fun move(coordinate: Coordinate) = coordinate.copy(second = coordinate.second + 1)
	},
	Left {
		override val left get() = Down
		override val right get() = Up
		override fun move(coordinate: Coordinate) = coordinate.copy(first = coordinate.first - 1)
	},
	Right {
		override val left: Direction get() = Up
		override val right: Direction get() = Down
		override fun move(coordinate: Coordinate) = coordinate.copy(first = coordinate.first + 1)
	};

	abstract val left: Direction
	abstract val right: Direction
	abstract fun move(coordinate: Coordinate): Coordinate

}

fun Map<Coordinate, Char>.toImage() = let { coordinates ->
	val minX = coordinates.keys.map { it.x }.min()!!
	val minY = coordinates.keys.map { it.y }.min()!!
	val maxX = coordinates.keys.map { it.x }.max()!!
	val maxY = coordinates.keys.map { it.y }.max()!!
	(minY..maxY).joinToString("\n") { row ->
		(minX..maxX).map { column ->
			coordinates[column to row] ?: ' '
		}.joinToString("")
	}
}

fun Sequence<String>.toIntCode() = toList().toIntCode()

fun List<String>.toIntCode() = single()
		.split(",")
		.map(String::toLong)
		.mapIndexed { index, i -> index to i }
		.toMap()
		.toMutableMap()
		.let { IntCode(it) }
