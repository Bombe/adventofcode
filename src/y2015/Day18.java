package y2015;

import java.util.ArrayList;
import java.util.List;

import utils.Input;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day18 {

	private static boolean[][] getInput() {
		List<boolean[]> rows = new ArrayList<>();
		Input.getInput(Day18.class, "y2015/day18.txt").stream().map(Day18::parseRow).forEach(rows::add);
		return rows.toArray(new boolean[rows.size()][]);
	}

	private static boolean[] parseRow(String line) {
		boolean[] row = new boolean[line.length()];
		for (int i = 0; i < line.length(); i++) {
			row[i] = line.charAt(i) == '#';
		}
		return row;
	}

	private static boolean hasTwoOrThreeNeighbours(boolean[][] currentState, int x, int y) {
		int neighbours = countNeighbours(currentState, x, y);
		return (neighbours == 2) || (neighbours == 3);
	}

	private static boolean hasExactlyThreeNeightbours(boolean[][] currentState, int x, int y) {
		return countNeighbours(currentState, x, y) == 3;
	}

	private static int countNeighbours(boolean[][] currentState, int x, int y) {
		int neighbours = 0;
		for (int yd = -1; yd <= 1; yd++) {
			for (int xd = -1; xd <= 1; xd++) {
				if ((yd == 0) && (xd == 0)) {
					continue;
				}
				if ((y == 0) && (yd < 0)) {
					continue;
				}
				if ((x == 0) && (xd < 0)) {
					continue;
				}
				if ((y == 99) && (yd > 0)) {
					continue;
				}
				if ((x == 99) && (xd > 0)) {
					continue;
				}
				if (currentState[y + yd][x + xd]) {
					neighbours++;
				}
			}
		}
		return neighbours;
	}

	private static int countLights(boolean[][] currentState) {
		int lights = 0;
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				if (currentState[y][x]) {
					lights++;
				}
			}
		}
		return lights;
	}

	private static boolean[][] calculateNextStep(boolean[][] currentState) {
		boolean[][] nextState = new boolean[currentState.length][currentState[0].length];
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				if (currentState[y][x]) {
					nextState[y][x] = hasTwoOrThreeNeighbours(currentState, x, y);
				} else {
					nextState[y][x] = hasExactlyThreeNeightbours(currentState, x, y);
				}
			}
		}
		return nextState;
	}

	public static class Puzzle1 {

		public static void main(String... arguments) {
			boolean[][] currentState = getInput();
			for (int step = 0; step < 100; step++) {
				boolean[][] nextState = calculateNextStep(currentState);
				currentState = nextState;
			}
			System.out.println(countLights(currentState));
		}

	}

	public static class Puzzle2 {

		public static void main(String... arguments) {
			boolean[][] currentState = getInput();
			for (int step = 0; step < 100; step++) {
				boolean[][] nextState = calculateNextStep(currentState);
				nextState[0][0] = true;
				nextState[99][0] = true;
				nextState[0][99] = true;
				nextState[99][99] = true;
				currentState = nextState;
			}
			System.out.println(countLights(currentState));
		}

	}

}
