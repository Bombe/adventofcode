package y2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day9 {

    private static final String INPUT = "AlphaCentauri to Snowdin = 66\n" +
            "AlphaCentauri to Tambi = 28\n" +
            "AlphaCentauri to Faerun = 60\n" +
            "AlphaCentauri to Norrath = 34\n" +
            "AlphaCentauri to Straylight = 34\n" +
            "AlphaCentauri to Tristram = 3\n" +
            "AlphaCentauri to Arbre = 108\n" +
            "Snowdin to Tambi = 22\n" +
            "Snowdin to Faerun = 12\n" +
            "Snowdin to Norrath = 91\n" +
            "Snowdin to Straylight = 121\n" +
            "Snowdin to Tristram = 111\n" +
            "Snowdin to Arbre = 71\n" +
            "Tambi to Faerun = 39\n" +
            "Tambi to Norrath = 113\n" +
            "Tambi to Straylight = 130\n" +
            "Tambi to Tristram = 35\n" +
            "Tambi to Arbre = 40\n" +
            "Faerun to Norrath = 63\n" +
            "Faerun to Straylight = 21\n" +
            "Faerun to Tristram = 57\n" +
            "Faerun to Arbre = 83\n" +
            "Norrath to Straylight = 9\n" +
            "Norrath to Tristram = 50\n" +
            "Norrath to Arbre = 60\n" +
            "Straylight to Tristram = 27\n" +
            "Straylight to Arbre = 81\n" +
            "Tristram to Arbre = 90";

    public static List<String> getInput() {
        return Arrays.asList(INPUT.split("\n"));
    }

    public static Map<String, Map<String, Integer>> getDistances() {
        Map<String, Map<String, Integer>> distances = new HashMap<>();
        for (String line : getInput()) {
            String[] split = line.split("( to | = )");
            distances.computeIfAbsent(split[0], s -> new HashMap<>()).put(split[1], Integer.parseInt(split[2]));
            distances.computeIfAbsent(split[1], s -> new HashMap<>()).put(split[0], Integer.parseInt(split[2]));
        }
        return distances;
    }

    public static List<List<String>> getAllRoutes(Map<String, Map<String, Integer>> distances) {
        List<List<String>> allRoutes = new ArrayList<>();
        List<String> currentRoute = new ArrayList<>(distances.keySet().stream().sorted(String::compareTo).collect(Collectors.toList()));
        // Pandita’s algorithm
        while (true) {
            // find largets k where a[k] < a [k+1]
            int k = currentRoute.size() - 2;
            while ((k >= 0) && currentRoute.get(k).compareTo(currentRoute.get(k + 1)) > 0) {
                k--;
            }
            if (k < 0) {
                break;
            }
            // find largest l where a[k] < a [l]
            int l = currentRoute.size() - 1;
            while (currentRoute.get(l).compareTo(currentRoute.get(k)) < 0) {
                l--;
            }
            // swap a[k] and a[l]
            String elementK = currentRoute.get(k);
            String elementL = currentRoute.get(l);
            currentRoute.set(k, elementL);
            currentRoute.set(l, elementK);
            // now reverse a[k+1] thru a[n]
            List<String> subList = currentRoute.subList(k + 1, currentRoute.size());
            for (int i = 1; i < subList.size(); i++) {
                String element = subList.remove(i);
                subList.add(0, element);
            }
            allRoutes.add(new ArrayList<>(currentRoute));
        }
        return allRoutes;
    }

    public static int routeLength(List<String> route, Map<String, Map<String, Integer>> distance) {
        int length = 0;
        for (int i = 1; i < route.size(); i++) {
            length += distance.get(route.get(i - 1)).get(route.get(i));
        }
        return length;
    }

    public static class Puzzle1 {

        public static void main(String... arguments) {
            Map<String, Map<String, Integer>> distances = getDistances();
            System.out.println(getAllRoutes(distances).stream().map(r -> routeLength(r, distances)).mapToInt(i -> i).min().getAsInt());
        }

    }

    public static class Puzzle2 {

        public static void main(String... arguments) {
            Map<String, Map<String, Integer>> distances = getDistances();
            System.out.println(getAllRoutes(distances).stream().map(r -> routeLength(r, distances)).mapToInt(i -> i).max().getAsInt());
        }

    }

}
