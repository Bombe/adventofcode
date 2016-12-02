package y2015;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day11 {

	private static final String INPUT = "hepxcrrq";

	private static String findNextPassword(String password) {
		long number = passwordToNumber(password);
		do {
			number++;
		} while (!isValidPassword(numberToPassword(number)));
		return numberToPassword(number);
	}

	private static long passwordToNumber(String password) {
		long number = 0;
		for (char c : password.toCharArray()) {
			number = number * 26 + (c - 'a');
		}
		return number;
	}

	private static String numberToPassword(long number) {
		StringBuilder password = new StringBuilder();
		long currentNumber = number;
		while (currentNumber > 0) {
			password.insert(0, (char) (currentNumber % 26 + 'a'));
			currentNumber /= 26;
		}
		return password.toString();
	}

	private static boolean isValidPassword(String password) {
		return passwordContainsThreeLetterStraight(password)
				&& passwordDoesNotContainOIL(password)
				&& passwordContainsTwoNonOverlappingPairs(password);
	}

	private static boolean passwordContainsThreeLetterStraight(String password) {
		for (int i = 0; i < password.length() - 2; i++) {
			char first = password.charAt(i);
			if ((password.charAt(i + 1) == (first + 1)) && (password.charAt(i + 2) == (first + 2))) {
				return true;
			}
		}
		return false;
	}

	private static boolean passwordDoesNotContainOIL(String password) {
		return (password.indexOf('i') == -1) && (password.indexOf('o') == -1) && (password.indexOf('l') == -1);
	}

	private static boolean passwordContainsTwoNonOverlappingPairs(String password) {
		int pairs = 0;
		boolean skip = false;
		for (int i = 1; i < password.length(); i++) {
			if (skip) {
				skip = false;
				continue;
			}
			if (password.charAt(i - 1) == password.charAt(i)) {
				pairs++;
				skip = true;
			}
		}
		return pairs >= 2;
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			System.out.println(findNextPassword(INPUT));
		}

	}

	public static class Puzzle2 {

		public static void main(String... arguments) {
			System.out.println(findNextPassword(findNextPassword(INPUT)));
		}

	}

}
