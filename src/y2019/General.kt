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
import java.util.concurrent.atomic.*

fun readInput(day: Int): Sequence<String> {
	class Foo
	return Foo::class.java.getResourceAsStream("Input$day.txt").bufferedReader().lineSequence()
}

fun Any?.println() = println(this)

class IntCode(val ints: MutableList<Int>, private val ip: Int = 0) {

	fun exec(input: () -> Int? = { 0 }, output: (Int) -> Unit = {}) = ints[ip].toInstruction()
			.let { instruction ->
				when (instruction.opcode) {
					1 -> IntCode(ints.apply { this[getValue(ip + 3)] = getValue(ip + 1, instruction.immediate1) + getValue(ip + 2, instruction.immediate2) }, ip + 4)
					2 -> IntCode(ints.apply { this[getValue(ip + 3)] = getValue(ip + 1, instruction.immediate1) * getValue(ip + 2, instruction.immediate2) }, ip + 4)
					3 -> input()?.let { IntCode(ints.apply { this[getValue(ip + 1)] = it }, ip + 2) } ?: throw IllegalStateException("no input")
					4 -> IntCode(ints.apply { output(getValue(ip + 1, instruction.immediate1)) }, ip + 2)
					5 -> IntCode(ints, if (getValue(ip + 1, instruction.immediate1) != 0) getValue(ip + 2, instruction.immediate2) else ip + 3)
					6 -> IntCode(ints, if (getValue(ip + 1, instruction.immediate1) == 0) getValue(ip + 2, instruction.immediate2) else ip + 3)
					7 -> IntCode(ints.apply { this[getValue(ip + 3)] = if (getValue(ip + 1, instruction.immediate1) < getValue(ip + 2, instruction.immediate2)) 1 else 0 }, ip + 4)
					8 -> IntCode(ints.apply { this[getValue(ip + 3)] = if (getValue(ip + 1, instruction.immediate1) == getValue(ip + 2, instruction.immediate2)) 1 else 0 }, ip + 4)
					99 -> null
					else -> throw IllegalStateException()
				}
			}

	private fun getValue(index: Int, immediate: Boolean = true) =
			ints[index].let { if (immediate) it else ints[it] }

}

data class Instruction(val opcode: Int, val immediate1: Boolean, val immediate2: Boolean)

fun Int.toInstruction() = Instruction(this % 100, ((this / 100) % 10) == 1, ((this / 1000) % 10) == 1)

fun IntCode.run(inputs: Iterable<Int>, outputs: (Int) -> Unit): IntCode {
	val inputIterator = inputs.iterator()
	val inputSupplier = { if (inputIterator.hasNext()) inputIterator.next() else null }
	return loopUntil { intCode ->
		val output = AtomicReference<Int>(null)
		val result = intCode.exec(inputSupplier, { output.set(it) })?.let { (output.get() != null) to it } ?: true to intCode
		output.get()?.let { outputs(it) }
		result
	}
}
