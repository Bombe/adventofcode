package y2015;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;

import utils.Input;

/**
 * Advent of Code, day 19.
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day19 {

	private static List<Replacement> replacements = Input.getInput(Day19.class, "y2015/day19.txt").stream()
			.map(Replacement::from)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(reverseCollector());

	private static <T> Collector<T, ?, List<T>> reverseCollector() {
		return new Collector<T, ArrayDeque<T>, List<T>>() {
			@Override
			public Supplier<ArrayDeque<T>> supplier() {
				return ArrayDeque::new;
			}

			@Override
			public BiConsumer<ArrayDeque<T>, T> accumulator() {
				return (d, t) -> d.push(t);
			}

			@Override
			public BinaryOperator<ArrayDeque<T>> combiner() {
				return (l, r) -> { l.addAll(r); return l; };
			}

			@Override
			public Function<ArrayDeque<T>, List<T>> finisher() {
				return (d) -> new ArrayList<T>(d);
			}

			@Override
			public Set<Characteristics> characteristics() {
				return EnumSet.noneOf(Characteristics.class);
			}
		};
	}

	private static List<Replacement> getReplacements() {
		return replacements;
	}

	private static String getMedicineMolecule() {
		return Input.getInput(Day19.class, "y2015/day19.txt").stream()
				.filter(l -> !Replacement.from(l).isPresent())
				.filter(l -> !l.isEmpty())
				.findFirst().get();
	}

	public static class Replacement {

		public final String source;
		public final String target;

		private Replacement(String source, String target) {
			this.source = source;
			this.target = target;
		}

		public static Optional<Replacement> from(String line) {
			Matcher matcher = Pattern.compile("^(.+) => (.+)$").matcher(line);
			if (!matcher.matches()) {
				return Optional.empty();
			}
			return Optional.of(new Replacement(matcher.group(1), matcher.group(2)));
		}

		@Override
		public String toString() {
			return String.format("%s -> %s", source, target);
		}

		@Override
		public int hashCode() {
			return Objects.hash(source, target);
		}

		@Override
		public boolean equals(Object obj) {
			return Objects.equals(((Replacement) obj).source, source) && Objects.equals(((Replacement) obj).target, target);
		}

	}

	public static List<String> processMolecule(String molecule, Replacement replacement) {
		List<String> processedMolecules = new ArrayList<>();
		int i = -1;
		while ((i = molecule.indexOf(replacement.source, i + 1)) > -1) {
			processedMolecules.add(molecule.substring(0, i) + replacement.target + molecule.substring(i + replacement.source.length()));
		}
		return processedMolecules;
	}

	public static List<String> unprocessMolecule(String molecule, Replacement replacement) {
		List<String> processedMolecules = new ArrayList<>();
		int i = molecule.length();
		while ((i = molecule.lastIndexOf(replacement.target, i - 1)) > -1) {
			processedMolecules.add(molecule.substring(0, i) + replacement.source + molecule.substring(i + replacement.target.length()));
		}
		return processedMolecules;
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			String molecule = getMedicineMolecule();
			System.out.println(getReplacements().stream()
					.flatMap(r -> processMolecule(molecule, r).stream())
					.distinct()
					.count());
		}
	}

	public static class Puzzle2 {

		private static List<List<Replacement>> getSteps(String targetMolecule) {
			Deque<Replacement> currentReplacements = new ArrayDeque<>();
			Map<String, List<List<Replacement>>> cache = new HashMap<>();
			return getSteps(cache, currentReplacements, targetMolecule);
		}

		private static List<List<Replacement>> getSteps(Map<String, List<List<Replacement>>> cache, Deque<Replacement> currentReplacements,
				String targetMolecule) {
			if (targetMolecule.equals("e")) {
				return new ArrayList<>(Arrays.asList(new ArrayList<>(currentReplacements)));
			}
			List<List<Replacement>> replacements = new ArrayList<>();
			for (Replacement replacement : getReplacements()) {
				int k = targetMolecule.length();
				while ((k = targetMolecule.lastIndexOf(replacement.target, k - 1)) > -1) {
					currentReplacements.push(replacement);
					Once.run(() -> System.out.println(currentReplacements.getLast() + ", " + currentReplacements.size() + ", " + targetMolecule.length()));
					String newMolecule = targetMolecule.substring(0, k) + replacement.source + targetMolecule.substring(k + replacement.target.length());
					replacements.addAll(cache.computeIfAbsent(newMolecule, (s) -> getSteps(cache, currentReplacements, newMolecule)));
					currentReplacements.pop();
				}
			}
			return replacements;
		}

		public static void main(String... arguments) {
			String molecule = getMedicineMolecule();
			System.out.println(getSteps(molecule).size());
		}

	}

	private static class Once {

		private static long lastTime;

		public static void run(Runnable r) {
			if ((System.currentTimeMillis() - lastTime) >= 1000) {
				r.run();
				lastTime = System.currentTimeMillis();
			}
		}

	}

}
