package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput

fun solveDay1Part1(input: List<String>) =
	input.sumOf { it.firstDigit() * 10 + it.lastDigit() }

fun solveDay1Part2(input: List<String>) =
	input
		.sumOf { it.wordsToDigits().firstDigit() * 10 + it.wordsToDigitsBackward().lastDigit() }

private val digitReplacements = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

private fun String.wordsToDigits(): String =
	digitReplacements.entries.singleOrNull { startsWith(it.key) }
		?.let { it.value.toString() + substring(it.key.length).wordsToDigits() }
		?: if (isNotEmpty()) (substring(0, 1) + substring(1).wordsToDigits()) else ""

private fun String.wordsToDigitsBackward(): String =
	digitReplacements.entries.singleOrNull { endsWith(it.key) }
		?.let { substring(0, length - it.key.length).wordsToDigitsBackward() + it.value.toString() }
		?: if (isNotEmpty()) (substring(0, length - 1).wordsToDigitsBackward() + last()) else ""

private fun String.firstDigit(): Int = dropWhile { !it.isDigit() }.first().code - 48
private fun String.lastDigit(): Int = reversed().firstDigit()

fun main() {
	println(solveDay1Part1(Day1Test::class.readInput(1)))
	println(solveDay1Part2(Day1Test::class.readInput(1)))
}

private class Day1Test {

	@Test
	fun `part 1 can be solved`() {
		val part1Input = """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet"""
		assertThat(solveDay1Part1(part1Input.split("\n")), equalTo(142))
	}

	@Test
	fun `part 2 can be solved`() {
		val part2Input = """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen"""
		assertThat(solveDay1Part2(part2Input.split("\n")), equalTo(281))
	}


}
