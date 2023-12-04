package y2015;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Knapsack {

	public static List<List<Integer>> collect(List<Integer> weights) {
		List<Integer> currentAmounts = weights.stream().map(i -> 0).collect(Collectors.toList());
		return collect(weights, currentAmounts);
	}

	public static List<List<Integer>> collect(List<Integer> sizes, List<Integer> currentContent) {
		List<List<Integer>> collected = new ArrayList<>();
		List<Integer> content = sizes.stream().map(i -> 0).collect(Collectors.toCollection(ArrayList::new));
		for (int i = 0; i < (1 << sizes.size()); i++) {
			for (int j = 0; j < sizes.size(); j++) {
				content.set(j, (i & (1 << j)) != 0 ? sizes.get(j) : 0);
			}
			collected.add(new ArrayList<>(content));
		}
		return collected;
	}

}
