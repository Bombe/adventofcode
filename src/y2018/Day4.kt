package y2018

import java.time.*
import kotlin.collections.Map.*

fun main(args: Array<String>) {
	part1().product.println()
	part2().product.println()
}

private fun part1() = readInput(4)
		.toList()
		.sorted()
		.parseGuardEvents()
		.collapseGuardEvents()
		.expandGuardEventsToMinutes()
		.collectAsleepMinutes()
		.maxBy { (_, asleepMinutes) -> asleepMinutes.values.sum() }!!
		.let { it -> it.key to it.value.maxBy(Entry<Int, Int>::value)!!.key }

private fun part2() = readInput(4)
		.toList()
		.sorted()
		.parseGuardEvents()
		.collapseGuardEvents()
		.expandGuardEventsToMinutes()
		.collectAsleepMinutes()
		.maxBy { (_, asleepMinutes) -> asleepMinutes.values.max()!! }!!
		.let { it.key to it.value.maxBy(Entry<Int, Int>::value)!!.key }

private fun List<String>.parseGuardEvents() =
		map { event ->
			Regex("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)] (.*)").matchEntire(event)!!
					.groupValues.drop(1)
					.let { LocalDateTime.of(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt())!! to it[5] }
		}

private fun List<Pair<LocalDateTime, String>>.collapseGuardEvents() =
		fold(0 to mutableMapOf<Int, MutableList<MutableList<Int>>>()) { (guard, guardTimes), (time, event) ->
			Regex("Guard #(\\d+) begins shift")
					.matchEntire(event)
					?.let { it.groupValues[1].toInt() }
					?.let { guardId -> guardId to guardTimes.apply { set(guardId, getOrDefault(guardId, mutableListOf()).apply { add(mutableListOf()).let { Unit } }) } }
					?: when (event) {
						"falls asleep" -> guard to guardTimes.apply { get(guard)!!.last() += time.minute }
						"wakes up" -> guard to guardTimes.apply { get(guard)!!.last() += time.minute }
						else -> guard to guardTimes
					}
		}.second

private fun MutableMap<Int, MutableList<MutableList<Int>>>.expandGuardEventsToMinutes() =
		mapValues { (_, times) ->
			times.map { sleepEvents ->
				(0 until 60).fold(listOf<Boolean>()) { minutes, minute ->
					val lastState = minutes.lastOrNull() ?: false
					minutes + (if (minute in sleepEvents) !lastState else lastState)
				}
			}
		}

private fun Map<Int, List<List<Boolean>>>.collectAsleepMinutes() =
		mapValues { (_, asleepMinutes) ->
			asleepMinutes.fold(mutableMapOf<Int, Int>()) { asleepCounts, asleepMinutes ->
				asleepMinutes.forEachIndexed { index, asleep ->
					asleepCounts[index] = asleepCounts.getOrDefault(index, 0) + if (asleep) 1 else 0
				}
				asleepCounts
			}
		}
