package y2015;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day10 {

	private static final String INPUT = "1113222113";

	public static String lookAndSay(String input) {
		StringBuilder explained = new StringBuilder();
		char last = 0;
		int count = 0;
		for (char c : input.toCharArray()) {
			if (c != last) {
				if (count > 0) {
					explained.append(count).append(last);
					count = 0;
				}
			}
			last = c;
			count++;
		}
		explained.append(count).append(last);
		return explained.toString();
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			String currentInput = INPUT;
			for (int i = 0; i < 40; i++) {
				currentInput = lookAndSay(currentInput);
			}
			System.out.println(currentInput.length());
		}

	}

	public static class Puzzle2 {

		public static void main(String... arguments) {
			String currentInput = INPUT;
			for (int i = 0; i < 50; i++) {
				currentInput = lookAndSay(currentInput);
			}
			System.out.println(currentInput.length());
		}

	}

}
