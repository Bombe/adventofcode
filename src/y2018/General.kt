package y2018

import java.util.stream.*

fun Any.println() = println(this)

fun readInput(day: Int): Sequence<String> {
	class Foo
	return Foo::class.java.getResourceAsStream("Input$day.txt").bufferedReader().lines().asSequence()
}

private fun <T> Stream<T>.asSequence(): Sequence<T> = Sequence { iterator() }

fun <T> List<T>.loop(): Sequence<T> =
		generateSequence(0) { index -> (index + 1) % size }
				.map(this::get)

fun <T, S> Sequence<T>.foldWhile(initialState: S, next: (T, S) -> Pair<Boolean, S>): S? {
	var currentState = true to initialState
	val iterator = iterator()
	while (currentState.first && iterator.hasNext()) {
		currentState = next(iterator.next(), currentState.second)
	}
	return currentState.second.takeUnless { currentState.first }
}

fun <T> List<T>.loop(n: Int): List<T> =
		(0 until n).fold(mutableListOf()) { previous, _ ->
			previous.apply { addAll(this@loop) }
		}

val Int.even get() = (this and 1) == 0
val Int.sqr get() = this * this
val Int.sqrt get() = Math.sqrt(toDouble()).toInt()

val Pair<Int, Int>.product get() = first * second
