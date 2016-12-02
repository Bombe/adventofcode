package y2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.Input;

/**
 * Advent of Code, day 16.
 */
public class Day16 {

	private static List<Sue> getInput() {
		return Input.getInput(Day16.class, "y2015/day16.txt").stream().map(Sue::parse).collect(Collectors.toList());
	}

	public static class Sue {

		public final int number;
		public final OptionalInt children;
		public final OptionalInt cats;
		public final OptionalInt samoyeds;
		public final OptionalInt pomeranians;
		public final OptionalInt akitas;
		public final OptionalInt vizslas;
		public final OptionalInt goldfish;
		public final OptionalInt trees;
		public final OptionalInt cars;
		public final OptionalInt perfumes;

		public Sue(int number, Map<String, OptionalInt> things) {
			this.number = number;
			this.children = things.getOrDefault("children", OptionalInt.empty());
			this.cats = things.getOrDefault("cats", OptionalInt.empty());
			this.samoyeds = things.getOrDefault("samoyeds", OptionalInt.empty());
			this.pomeranians = things.getOrDefault("pomeranians", OptionalInt.empty());
			this.akitas = things.getOrDefault("akitas", OptionalInt.empty());
			this.vizslas = things.getOrDefault("vizslas", OptionalInt.empty());
			this.goldfish = things.getOrDefault("goldfish", OptionalInt.empty());
			this.trees = things.getOrDefault("trees", OptionalInt.empty());
			this.cars = things.getOrDefault("cars", OptionalInt.empty());
			this.perfumes = things.getOrDefault("perfumes", OptionalInt.empty());
		}

		public static Sue parse(String line) {
			Pattern pattern = Pattern.compile("Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)");
			Matcher matcher = pattern.matcher(line);
			if (!matcher.matches()) {
				throw new RuntimeException();
			}
			int number = Integer.parseInt(matcher.group(1));
			Map<String, OptionalInt> things = new HashMap<>();
			for (int i = 2; i < 8; i += 2) {
				things.put(matcher.group(i), OptionalInt.of(Integer.parseInt(matcher.group(i + 1))));
			}
			return new Sue(number, things);
		}

	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			System.out.println(getInput().stream()
							.filter(s -> !s.children.isPresent() || s.children.getAsInt() == 3)
							.filter(s -> !s.cats.isPresent() || s.cats.getAsInt() == 7)
							.filter(s -> !s.samoyeds.isPresent() || s.samoyeds.getAsInt() == 2)
							.filter(s -> !s.pomeranians.isPresent() || s.pomeranians.getAsInt() == 3)
							.filter(s -> !s.akitas.isPresent() || s.akitas.getAsInt() == 0)
							.filter(s -> !s.vizslas.isPresent() || s.vizslas.getAsInt() == 0)
							.filter(s -> !s.goldfish.isPresent() || s.goldfish.getAsInt() == 5)
							.filter(s -> !s.trees.isPresent() || s.trees.getAsInt() == 3)
							.filter(s -> !s.cars.isPresent() || s.cars.getAsInt() == 2)
							.filter(s -> !s.perfumes.isPresent() || s.perfumes.getAsInt() == 1)
							.map(s -> s.number)
							.collect(Collectors.toList())
			);
		}

	}

	public static class Puzzle2 {

		public static void main(String... arguments) {
			System.out.println(getInput().stream()
							.filter(s -> !s.children.isPresent() || s.children.getAsInt() == 3)
							.filter(s -> !s.cats.isPresent() || s.cats.getAsInt() > 7)
							.filter(s -> !s.samoyeds.isPresent() || s.samoyeds.getAsInt() == 2)
							.filter(s -> !s.pomeranians.isPresent() || s.pomeranians.getAsInt() < 3)
							.filter(s -> !s.akitas.isPresent() || s.akitas.getAsInt() == 0)
							.filter(s -> !s.vizslas.isPresent() || s.vizslas.getAsInt() == 0)
							.filter(s -> !s.goldfish.isPresent() || s.goldfish.getAsInt() < 5)
							.filter(s -> !s.trees.isPresent() || s.trees.getAsInt() > 3)
							.filter(s -> !s.cars.isPresent() || s.cars.getAsInt() == 2)
							.filter(s -> !s.perfumes.isPresent() || s.perfumes.getAsInt() == 1)
							.map(s -> s.number)
							.collect(Collectors.toList())
			);
		}

	}

}
