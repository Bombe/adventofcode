package y2017

import java.util.stream.Stream

fun Any.println() = println(this)

fun readInput(day: Int): Sequence<String> {
	class Foo()
	return Foo::class.java.getResourceAsStream("Input$day.txt").bufferedReader().lines().asSequence()
}

private fun <T> Stream<T>.asSequence(): Sequence<T> = Sequence { iterator() }

fun <T> List<T>.loop(n: Int): List<T> =
		(0 until n).fold(mutableListOf()) { previous, _ ->
			previous.apply { addAll(this@loop) }
		}
