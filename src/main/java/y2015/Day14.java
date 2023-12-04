package y2015;

import java.util.Arrays;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;
	import java.util.stream.Collectors;
	import java.util.stream.IntStream;

	/**
	 * Advent of code, day 14.
	 *
	 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
	 */
	public class Day14 {

		private static final String INPUT = "Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.\n"
				+ "Cupid can fly 8 km/s for 17 seconds, but then must rest for 114 seconds.\n"
				+ "Prancer can fly 18 km/s for 6 seconds, but then must rest for 103 seconds.\n"
				+ "Donner can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.\n"
				+ "Dasher can fly 11 km/s for 12 seconds, but then must rest for 125 seconds.\n"
				+ "Comet can fly 21 km/s for 6 seconds, but then must rest for 121 seconds.\n"
				+ "Blitzen can fly 18 km/s for 3 seconds, but then must rest for 50 seconds.\n"
				+ "Vixen can fly 20 km/s for 4 seconds, but then must rest for 75 seconds.\n"
				+ "Dancer can fly 7 km/s for 20 seconds, but then must rest for 119 seconds.";

		private static List<String> getInput() {
			return Arrays.asList(INPUT.split("\n"));
		}

		private static int getTravelledDistance(Reindeer reindeer, int seconds) {
			return ((seconds / reindeer.cycleTime) * reindeer.flyTime + Math.min(reindeer.flyTime, seconds % reindeer.cycleTime)) * reindeer.speed;
		}

		private static class Reindeer {

			private static final Pattern PATTERN = Pattern.compile("(.*) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.");
			public final String name;
			public final int speed;
			public final int flyTime;
			public final int restTime;
			public final int cycleTime;

			private Reindeer(String name, int speed, int flyTime, int restTime) {
				this.name = name;
				this.speed = speed;
				this.flyTime = flyTime;
				this.restTime = restTime;
				this.cycleTime = flyTime + restTime;
			}

			public static Reindeer parse(String line) {
				Matcher matcher = PATTERN.matcher(line);
				if (!matcher.matches()) {
					throw new RuntimeException();
				}
				return new Reindeer(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
			}

		}

		public static class Puzzle1 {

			public static void main(String... arguments) {
				System.out.println(getInput().stream().map(Reindeer::parse).mapToInt(r -> getTravelledDistance(r, 2503)).max().getAsInt());
			}

		}

		public static class Puzzle2 {

			public static void main(String... arguments) {
				List<Reindeer> reindeers = getInput().stream().map(Reindeer::parse).collect(Collectors.toList());
				Map<Reindeer, Integer> reindeerPoints = new HashMap<>();
				IntStream.range(1, 2503).forEach(second -> {
					int maxDistance = reindeers.stream().mapToInt(r -> getTravelledDistance(r, second)).sorted().max().getAsInt();
					reindeers.stream()
							.filter(r -> getTravelledDistance(r, second) == maxDistance)
							.forEach(r -> reindeerPoints.put(r, reindeerPoints.computeIfAbsent(r, i -> 0) + 1));
				});
				System.out.println(reindeerPoints.entrySet().stream().sorted((l, r) -> r.getValue().compareTo(l.getValue())).map(e -> e.getKey().name).findFirst().get());
				System.out.println(reindeerPoints.values().stream().mapToInt(i -> i).max().getAsInt());
			}

		}

	}
