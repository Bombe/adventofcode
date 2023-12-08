package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import utils.readInput

fun main() {
	println(solveDay7Part1(Day7Example::class.readInput(7)))
}

private fun solveDay7Part1(input: List<String>) =
	input.map(String::toHandAndBid)
		.sortedWith { (leftHand, _), (rightHand, _) -> compareHands(leftHand, rightHand) }
		.mapIndexed { index, (hand, bid) -> bid * (index + 1) }
		.sum()

private fun String.toHandAndBid() = substring(0, 5) to substring(6).toInt()

private fun compareHands(left: String, right: String) =
	left.getType().let { leftType ->
		right.getType().let { rightType ->
			if (leftType < rightType) {
				1
			} else if (leftType > rightType) {
				-1
			} else {
				(0..4).fold(0) { result, index ->
					if (result != 0) {
						result
					} else {
						if (left[index].getCard() > right[index].getCard()) {
							1
						} else if (left[index].getCard() < right[index].getCard()) {
							-1
						} else {
							0
						}
					}
				}
			}
		}
	}

private fun Char.getCard() = "23456789TJQKA".indexOf(this)

private fun String.getType() = Type.entries.first { it.isInHand(this) }

private enum class Type {
	Five {
		override fun isInHand(hand: String) = hand.chars().distinct().count() == 1L
	},
	Four {
		override fun isInHand(hand: String) = hand.countCards().any { it.second == 4 }
	},
	FullHouse {
		override fun isInHand(hand: String) = hand.cardCounts() == listOf(2, 3)
	},
	Three {
		override fun isInHand(hand: String) = hand.cardCounts() == listOf(1, 1, 3)
	},
	TwoPairs {
		override fun isInHand(hand: String) = hand.cardCounts() == listOf(1, 2, 2)
	},
	Pair {
		override fun isInHand(hand: String) = hand.cardCounts() == listOf(1, 1, 1, 2)
	},
	HighCard {
		override fun isInHand(hand: String) = hand.cardCounts() == listOf(1, 1, 1, 1, 1)
	};

	abstract fun isInHand(hand: String): Boolean
}

private fun String.cardCounts() = countCards().map(kotlin.Pair<Char, Int>::second).toList().sorted()
private fun String.countCards() = toCharArray().map { it to 1 }.groupBy { it.first }.map { it.key to it.value.size }

class Day7Example {

	@Test
	fun `part 1 can be solved`() {
		assertThat(solveDay7Part1(partInput.split("\n")), equalTo(6440))
	}

	private val partInput = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""

}
