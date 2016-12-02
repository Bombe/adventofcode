package y2015;

import java.util.List;
import java.util.stream.Collectors;

import utils.Input;

/**
 * Advent of Code, Day 17.
 */
public class Day17 {

	private static List<Integer> getInput() {
		return Input.getInput(Day17.class, "y2015/day17.txt").stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	private static int sum(List<Integer> contents) {
		return contents.stream().mapToInt(i -> i).sum();
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			List<Integer> containerSizes = getInput();
			List<List<Integer>> possibleFillings = Knapsack.collect(containerSizes).stream()
					.filter(c -> sum(c) == 150)
					.collect(Collectors.toList());
			System.out.println(possibleFillings.size());
		}

	}

	public static class Puzzle2 {

		private static int countContainers(List<Integer> contents) {
			return (int) contents.stream().filter(i -> i != 0).count();
		}

		public static void main(String... arguments) {
			List<Integer> containerSizes = getInput();
			List<List<Integer>> allPossibleFillings = Knapsack.collect(containerSizes).stream()
					.filter(c -> sum(c) == 150)
					.collect(Collectors.toList());
			int numberOfContainers = allPossibleFillings.stream()
					.mapToInt(Puzzle2::countContainers)
					.min().getAsInt();
			System.out.println(allPossibleFillings.stream()
					.mapToInt(Puzzle2::countContainers)
					.filter(i -> i == numberOfContainers)
					.count());
		}

	}

}
