package y2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.Input;

/**
 * Advent of Code, day 15.
 */
public class Day15 {

	private static List<Ingredient> getInput() {
		return Input.getInput(Day15.class, "y2015/day15.txt").stream().map(Ingredient::parse).collect(Collectors.toList());
	}

	public static class Ingredient {

		private static final Pattern PATTERN =
				Pattern.compile(".*: capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)");
		public final int capacity;
		public final int durability;
		public final int flavor;
		public final int texture;
		public final int calories;

		public Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
			this.capacity = capacity;
			this.durability = durability;
			this.flavor = flavor;
			this.texture = texture;
			this.calories = calories;
		}

		public static Ingredient parse(String line) {
			Matcher matcher = PATTERN.matcher(line);
			if (!matcher.matches()) {
				throw new IllegalArgumentException();
			}
			return new Ingredient(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
					Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));
		}

	}

	private static List<List<Integer>> collect(List<Integer> currentAmounts, int remainingCount, int remainingSum) {
		if (remainingCount == 0) {
			if (remainingSum == 0) {
				return Arrays.asList(new ArrayList<>(currentAmounts));
			}
			return Collections.emptyList();
		}
		List<List<Integer>> collected = new ArrayList<>();
		for (int i = 0; i <= remainingSum; i++) {
			currentAmounts.set(remainingCount - 1, i);
			collected.addAll(collect(currentAmounts, remainingCount - 1, remainingSum - i));
		}
		return collected;
	}

	private static int score(List<Ingredient> ingredients, List<Integer> ingredientAmounts) {
		return scoreSingleValue(ingredients, ingredientAmounts, i -> i.capacity)
				* scoreSingleValue(ingredients, ingredientAmounts, i -> i.durability)
				* scoreSingleValue(ingredients, ingredientAmounts, i -> i.flavor)
				* scoreSingleValue(ingredients, ingredientAmounts, i -> i.texture);
	}

	private static int scoreSingleValue(List<Ingredient> ingredients, List<Integer> ingredientAmounts,
			Function<Ingredient, Integer> singleValueSupplier) {
		int sum = 0;
		for (int i = 0; i < ingredientAmounts.size(); i++) {
			sum += singleValueSupplier.apply(ingredients.get(i)) * ingredientAmounts.get(i);
		}
		return (sum < 0) ? 0 : sum;
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			List<Ingredient> ingredients = getInput();
			List<Integer> currentAmounts = ingredients.stream().map(i -> 0).collect(Collectors.toCollection(ArrayList::new));
			List<List<Integer>> ingredientAmounts = collect(currentAmounts, ingredients.size(), 100);
			System.out.println(ingredientAmounts.stream().mapToInt(i -> score(ingredients, i)).max().getAsInt());
		}

	}

	public static class Puzzle2 {

		private static int countCalories(List<Ingredient> ingredients, List<Integer> ingredientAmounts) {
			return scoreSingleValue(ingredients, ingredientAmounts, i -> i.calories);
		}

		public static void main(String... arguments) {
			List<Ingredient> ingredients = getInput();
			List<Integer> currentAmounts = ingredients.stream().map(i -> 0).collect(Collectors.toCollection(ArrayList::new));
			List<List<Integer>> ingredientAmounts = collect(currentAmounts, ingredients.size(), 100);
			System.out.println(ingredientAmounts.stream().
					filter(i -> countCalories(ingredients, i) == 500)
					.mapToInt(i -> score(ingredients, i)).max().getAsInt());
		}

	}

}
