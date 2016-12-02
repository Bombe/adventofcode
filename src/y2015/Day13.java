package y2015;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day13 {

	private static final String INPUT = "Alice would gain 2 happiness units by sitting next to Bob.\n"
			+ "Alice would gain 26 happiness units by sitting next to Carol.\n"
			+ "Alice would lose 82 happiness units by sitting next to David.\n"
			+ "Alice would lose 75 happiness units by sitting next to Eric.\n"
			+ "Alice would gain 42 happiness units by sitting next to Frank.\n"
			+ "Alice would gain 38 happiness units by sitting next to George.\n"
			+ "Alice would gain 39 happiness units by sitting next to Mallory.\n"
			+ "Bob would gain 40 happiness units by sitting next to Alice.\n"
			+ "Bob would lose 61 happiness units by sitting next to Carol.\n"
			+ "Bob would lose 15 happiness units by sitting next to David.\n"
			+ "Bob would gain 63 happiness units by sitting next to Eric.\n"
			+ "Bob would gain 41 happiness units by sitting next to Frank.\n"
			+ "Bob would gain 30 happiness units by sitting next to George.\n"
			+ "Bob would gain 87 happiness units by sitting next to Mallory.\n"
			+ "Carol would lose 35 happiness units by sitting next to Alice.\n"
			+ "Carol would lose 99 happiness units by sitting next to Bob.\n"
			+ "Carol would lose 51 happiness units by sitting next to David.\n"
			+ "Carol would gain 95 happiness units by sitting next to Eric.\n"
			+ "Carol would gain 90 happiness units by sitting next to Frank.\n"
			+ "Carol would lose 16 happiness units by sitting next to George.\n"
			+ "Carol would gain 94 happiness units by sitting next to Mallory.\n"
			+ "David would gain 36 happiness units by sitting next to Alice.\n"
			+ "David would lose 18 happiness units by sitting next to Bob.\n"
			+ "David would lose 65 happiness units by sitting next to Carol.\n"
			+ "David would lose 18 happiness units by sitting next to Eric.\n"
			+ "David would lose 22 happiness units by sitting next to Frank.\n"
			+ "David would gain 2 happiness units by sitting next to George.\n"
			+ "David would gain 42 happiness units by sitting next to Mallory.\n"
			+ "Eric would lose 65 happiness units by sitting next to Alice.\n"
			+ "Eric would gain 24 happiness units by sitting next to Bob.\n"
			+ "Eric would gain 100 happiness units by sitting next to Carol.\n"
			+ "Eric would gain 51 happiness units by sitting next to David.\n"
			+ "Eric would gain 21 happiness units by sitting next to Frank.\n"
			+ "Eric would gain 55 happiness units by sitting next to George.\n"
			+ "Eric would lose 44 happiness units by sitting next to Mallory.\n"
			+ "Frank would lose 48 happiness units by sitting next to Alice.\n"
			+ "Frank would gain 91 happiness units by sitting next to Bob.\n"
			+ "Frank would gain 8 happiness units by sitting next to Carol.\n"
			+ "Frank would lose 66 happiness units by sitting next to David.\n"
			+ "Frank would gain 97 happiness units by sitting next to Eric.\n"
			+ "Frank would lose 9 happiness units by sitting next to George.\n"
			+ "Frank would lose 92 happiness units by sitting next to Mallory.\n"
			+ "George would lose 44 happiness units by sitting next to Alice.\n"
			+ "George would lose 25 happiness units by sitting next to Bob.\n"
			+ "George would gain 17 happiness units by sitting next to Carol.\n"
			+ "George would gain 92 happiness units by sitting next to David.\n"
			+ "George would lose 92 happiness units by sitting next to Eric.\n"
			+ "George would gain 18 happiness units by sitting next to Frank.\n"
			+ "George would gain 97 happiness units by sitting next to Mallory.\n"
			+ "Mallory would gain 92 happiness units by sitting next to Alice.\n"
			+ "Mallory would lose 96 happiness units by sitting next to Bob.\n"
			+ "Mallory would lose 51 happiness units by sitting next to Carol.\n"
			+ "Mallory would lose 81 happiness units by sitting next to David.\n"
			+ "Mallory would gain 31 happiness units by sitting next to Eric.\n"
			+ "Mallory would lose 73 happiness units by sitting next to Frank.\n"
			+ "Mallory would lose 89 happiness units by sitting next to George.";

	private static List<String> getInput() {
		return Arrays.asList(INPUT.split("\n"));
	}

	private static final Pattern parser = Pattern.compile("^(.*) would (.*) (\\d+) happiness units by sitting next to (.*).$");

	public static Map<String, Map<String, Integer>> getHappinesses() {
		Map<String, Map<String, Integer>> happinesses = new HashMap<>();
		for (String input : getInput()) {
			Matcher matcher = parser.matcher(input);
			matcher.matches();
			String guest = matcher.group(1);
			boolean gain = matcher.group(2).equals("gain");
			int happiness = Integer.parseInt(matcher.group(3)) * (gain ? 1 : -1);
			String otherGuest = matcher.group(4);
			happinesses.computeIfAbsent(guest, s -> new HashMap<>()).put(otherGuest, happiness);
		}
		return happinesses;
	}

	public static int evaluateSeating(List<String> seating, Map<String, Map<String, Integer>> happinesses) {
		int happiness = 0;
		for (int i = 0; i < seating.size(); i++) {
			String guest = seating.get(i);
			String previousGuest = seating.get((i + seating.size() - 1) % seating.size());
			String nextGuest = seating.get((i + 1) % seating.size());
			happiness += happinesses.get(guest).get(previousGuest);
			happiness += happinesses.get(guest).get(nextGuest);
		}
		return happiness;
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			Map<String, Map<String, Integer>> happinesses = getHappinesses();
			List<List<String>> allSeatingArragements = Permutations.permutate(happinesses.keySet(), String::compareTo);
			System.out.println(allSeatingArragements.stream().map(s -> evaluateSeating(s, happinesses)).mapToInt(i -> i).max().getAsInt());
		}

	}

	public static class Puzzle2 {

		private static Map<String, Map<String, Integer>> insertMyself(Map<String, Map<String, Integer>> happinesses) {
			Map<String, Map<String, Integer>> newHappinesses = new HashMap<>(happinesses);
			Collection<String> names = happinesses.keySet();
			for (String name : names) {
				newHappinesses.get(name).put("Myself", 0);
				newHappinesses.computeIfAbsent("Myself", s -> new HashMap<>()).put(name, 0);
			}
			return newHappinesses;
		}

		public static void main(String... arguments) {
			Map<String, Map<String, Integer>> happinesses = insertMyself(getHappinesses());
			List<List<String>> allSeatingArragements = Permutations.permutate(happinesses.keySet(), String::compareTo);
			System.out.println(allSeatingArragements.stream().map(s -> evaluateSeating(s, happinesses)).mapToInt(i -> i).max().getAsInt());
		}

	}

}
