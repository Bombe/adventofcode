import java.util.stream.Stream

fun Any.println() = println(this)

fun readInput(day: Int): Sequence<String> {
	class Foo()
	return Foo::class.java.getResourceAsStream("Input$day.txt").bufferedReader().lines().asSequence()
}

private fun <T> Stream<T>.asSequence(): Sequence<T> = Sequence { iterator() }
