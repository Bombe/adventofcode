package y2016

import kotlin.comparisons.compareValues

fun main(args: Array<String>) {
	println(first(numberOfElves))
	println(second(numberOfElves))
}

private fun first(numberOfElves: Int) = letElvesStealPresentsFromNext((1..numberOfElves).toList())
private fun second(numberOfElves: Int) = letElvesStealPresentsFromAcross((1..numberOfElves).toList())

private fun letElvesStealPresentsFromNext(elfPresents: List<Int>) = solve(elfPresents, List<Int>::next)
private fun letElvesStealPresentsFromAcross(elfPresents: List<Int>) = solve(elfPresents, List<Int>::across)

private tailrec fun solve(elfPresents: List<Int>, targetSelector: (List<Int>, Int) -> Int): Int =
		if (elfPresents.size == 1) {
			elfPresents.first()
		} else {
			solve(elfPresents.fold(Pair(elfPresents.toMutableList(), mutableSetOf<Int>())) { presents, currentElf ->
				if (currentElf !in presents.second) {
					targetSelector(presents.first, currentElf).let { nextElfIndex ->
						presents.first[nextElfIndex].let { nextElf ->
							Pair(presents.first.apply { removeAt(nextElfIndex) },
									presents.second.apply { add(nextElf) })
						}
					}
				} else
					presents
			}.first, targetSelector)
		}

private fun List<Int>.next(key: Int) = (binarySearch { compareValues(it, key) } + 1) % size
private fun List<Int>.across(key: Int) = (binarySearch { compareValues(it, key) } + size / 2) % size

private const val numberOfElves = 3005290
