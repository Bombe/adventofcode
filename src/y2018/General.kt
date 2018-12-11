package y2018

import java.util.stream.*

fun Any.println() = println(this)

fun <R> timed(block: () -> R) =
		System.currentTimeMillis().let { startTime ->
			block() to (System.currentTimeMillis() - startTime)
		}.also {
			println("${it.first} (${it.second / 1000.0}s)")
		}

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

fun <T> T.loopUntil(loop: (T) -> Pair<Finished, T>): T {
	var currentState = this
	do {
		val (finished, nextState) = loop(currentState)
		currentState = nextState
	} while (!finished)
	return currentState
}

typealias Finished = Boolean

val Int.even get() = (this and 1) == 0
val Int.sqr get() = this * this
val Int.sqrt get() = Math.sqrt(toDouble()).toInt()

val Pair<Int, Int>.product get() = first * second

typealias Coordinate = Pair<Int, Int>

val Coordinate.x get() = first
val Coordinate.y get() = second

operator fun Coordinate.minus(coordinate: Coordinate): Coordinate = (first - coordinate.first) to (second - coordinate.second)
fun Coordinate.distanceTo(coordinate: Coordinate) = Math.abs(coordinate.first - first) + Math.abs(coordinate.second - second)
