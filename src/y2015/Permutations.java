package y2015;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Permutations {

	public static <V> List<List<V>> permutate(Collection<V> elements, Comparator<? super V> comparator) {
		List<List<V>> allPermutations = new ArrayList<>();
		List<V> currentPermutation = elements.stream().sorted(comparator).collect(Collectors.toList());
		// Pandita’s algorithm
		while (true) {
			// find largets k where a[k] < a [k+1]
			int k = currentPermutation.size() - 2;
			while ((k >= 0) && comparator.compare(currentPermutation.get(k), currentPermutation.get(k + 1)) > 0) {
				k--;
			}
			if (k < 0) {
				break;
			}
			// find largest l where a[k] < a [l]
			int l = currentPermutation.size() - 1;
			while (comparator.compare(currentPermutation.get(l), currentPermutation.get(k)) < 0) {
				l--;
			}
			// swap a[k] and a[l]
			V elementK = currentPermutation.get(k);
			V elementL = currentPermutation.get(l);
			currentPermutation.set(k, elementL);
			currentPermutation.set(l, elementK);
			// now reverse a[k+1] thru a[n]
			List<V> subList = currentPermutation.subList(k + 1, currentPermutation.size());
			for (int i = 1; i < subList.size(); i++) {
				V element = subList.remove(i);
				subList.add(0, element);
			}
			allPermutations.add(new ArrayList<>(currentPermutation));
		}
		return allPermutations;
	}


}
