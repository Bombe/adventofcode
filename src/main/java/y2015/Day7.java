package y2015;

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
public class Day7 {

    private static final String INPUT = "bn RSHIFT 2 -> bo\n" +
            "lf RSHIFT 1 -> ly\n" +
            "fo RSHIFT 3 -> fq\n" +
            "cj OR cp -> cq\n" +
            "fo OR fz -> ga\n" +
            "t OR s -> u\n" +
            "lx -> a\n" +
            "NOT ax -> ay\n" +
            "he RSHIFT 2 -> hf\n" +
            "lf OR lq -> lr\n" +
            "lr AND lt -> lu\n" +
            "dy OR ej -> ek\n" +
            "1 AND cx -> cy\n" +
            "hb LSHIFT 1 -> hv\n" +
            "1 AND bh -> bi\n" +
            "ih AND ij -> ik\n" +
            "c LSHIFT 1 -> t\n" +
            "ea AND eb -> ed\n" +
            "km OR kn -> ko\n" +
            "NOT bw -> bx\n" +
            "ci OR ct -> cu\n" +
            "NOT p -> q\n" +
            "lw OR lv -> lx\n" +
            "NOT lo -> lp\n" +
            "fp OR fv -> fw\n" +
            "o AND q -> r\n" +
            "dh AND dj -> dk\n" +
            "ap LSHIFT 1 -> bj\n" +
            "bk LSHIFT 1 -> ce\n" +
            "NOT ii -> ij\n" +
            "gh OR gi -> gj\n" +
            "kk RSHIFT 1 -> ld\n" +
            "lc LSHIFT 1 -> lw\n" +
            "lb OR la -> lc\n" +
            "1 AND am -> an\n" +
            "gn AND gp -> gq\n" +
            "lf RSHIFT 3 -> lh\n" +
            "e OR f -> g\n" +
            "lg AND lm -> lo\n" +
            "ci RSHIFT 1 -> db\n" +
            "cf LSHIFT 1 -> cz\n" +
            "bn RSHIFT 1 -> cg\n" +
            "et AND fe -> fg\n" +
            "is OR it -> iu\n" +
            "kw AND ky -> kz\n" +
            "ck AND cl -> cn\n" +
            "bj OR bi -> bk\n" +
            "gj RSHIFT 1 -> hc\n" +
            "iu AND jf -> jh\n" +
            "NOT bs -> bt\n" +
            "kk OR kv -> kw\n" +
            "ks AND ku -> kv\n" +
            "hz OR ik -> il\n" +
            "b RSHIFT 1 -> v\n" +
            "iu RSHIFT 1 -> jn\n" +
            "fo RSHIFT 5 -> fr\n" +
            "be AND bg -> bh\n" +
            "ga AND gc -> gd\n" +
            "hf OR hl -> hm\n" +
            "ld OR le -> lf\n" +
            "as RSHIFT 5 -> av\n" +
            "fm OR fn -> fo\n" +
            "hm AND ho -> hp\n" +
            "lg OR lm -> ln\n" +
            "NOT kx -> ky\n" +
            "kk RSHIFT 3 -> km\n" +
            "ek AND em -> en\n" +
            "NOT ft -> fu\n" +
            "NOT jh -> ji\n" +
            "jn OR jo -> jp\n" +
            "gj AND gu -> gw\n" +
            "d AND j -> l\n" +
            "et RSHIFT 1 -> fm\n" +
            "jq OR jw -> jx\n" +
            "ep OR eo -> eq\n" +
            "lv LSHIFT 15 -> lz\n" +
            "NOT ey -> ez\n" +
            "jp RSHIFT 2 -> jq\n" +
            "eg AND ei -> ej\n" +
            "NOT dm -> dn\n" +
            "jp AND ka -> kc\n" +
            "as AND bd -> bf\n" +
            "fk OR fj -> fl\n" +
            "dw OR dx -> dy\n" +
            "lj AND ll -> lm\n" +
            "ec AND ee -> ef\n" +
            "fq AND fr -> ft\n" +
            "NOT kp -> kq\n" +
            "ki OR kj -> kk\n" +
            "cz OR cy -> da\n" +
            "as RSHIFT 3 -> au\n" +
            "an LSHIFT 15 -> ar\n" +
            "fj LSHIFT 15 -> fn\n" +
            "1 AND fi -> fj\n" +
            "he RSHIFT 1 -> hx\n" +
            "lf RSHIFT 2 -> lg\n" +
            "kf LSHIFT 15 -> kj\n" +
            "dz AND ef -> eh\n" +
            "ib OR ic -> id\n" +
            "lf RSHIFT 5 -> li\n" +
            "bp OR bq -> br\n" +
            "NOT gs -> gt\n" +
            "fo RSHIFT 1 -> gh\n" +
            "bz AND cb -> cc\n" +
            "ea OR eb -> ec\n" +
            "lf AND lq -> ls\n" +
            "NOT l -> m\n" +
            "hz RSHIFT 3 -> ib\n" +
            "NOT di -> dj\n" +
            "NOT lk -> ll\n" +
            "jp RSHIFT 3 -> jr\n" +
            "jp RSHIFT 5 -> js\n" +
            "NOT bf -> bg\n" +
            "s LSHIFT 15 -> w\n" +
            "eq LSHIFT 1 -> fk\n" +
            "jl OR jk -> jm\n" +
            "hz AND ik -> im\n" +
            "dz OR ef -> eg\n" +
            "1 AND gy -> gz\n" +
            "la LSHIFT 15 -> le\n" +
            "br AND bt -> bu\n" +
            "NOT cn -> co\n" +
            "v OR w -> x\n" +
            "d OR j -> k\n" +
            "1 AND gd -> ge\n" +
            "ia OR ig -> ih\n" +
            "NOT go -> gp\n" +
            "NOT ed -> ee\n" +
            "jq AND jw -> jy\n" +
            "et OR fe -> ff\n" +
            "aw AND ay -> az\n" +
            "ff AND fh -> fi\n" +
            "ir LSHIFT 1 -> jl\n" +
            "gg LSHIFT 1 -> ha\n" +
            "x RSHIFT 2 -> y\n" +
            "db OR dc -> dd\n" +
            "bl OR bm -> bn\n" +
            "ib AND ic -> ie\n" +
            "x RSHIFT 3 -> z\n" +
            "lh AND li -> lk\n" +
            "ce OR cd -> cf\n" +
            "NOT bb -> bc\n" +
            "hi AND hk -> hl\n" +
            "NOT gb -> gc\n" +
            "1 AND r -> s\n" +
            "fw AND fy -> fz\n" +
            "fb AND fd -> fe\n" +
            "1 AND en -> eo\n" +
            "z OR aa -> ab\n" +
            "bi LSHIFT 15 -> bm\n" +
            "hg OR hh -> hi\n" +
            "kh LSHIFT 1 -> lb\n" +
            "cg OR ch -> ci\n" +
            "1 AND kz -> la\n" +
            "gf OR ge -> gg\n" +
            "gj RSHIFT 2 -> gk\n" +
            "dd RSHIFT 2 -> de\n" +
            "NOT ls -> lt\n" +
            "lh OR li -> lj\n" +
            "jr OR js -> jt\n" +
            "au AND av -> ax\n" +
            "0 -> c\n" +
            "he AND hp -> hr\n" +
            "id AND if -> ig\n" +
            "et RSHIFT 5 -> ew\n" +
            "bp AND bq -> bs\n" +
            "e AND f -> h\n" +
            "ly OR lz -> ma\n" +
            "1 AND lu -> lv\n" +
            "NOT jd -> je\n" +
            "ha OR gz -> hb\n" +
            "dy RSHIFT 1 -> er\n" +
            "iu RSHIFT 2 -> iv\n" +
            "NOT hr -> hs\n" +
            "as RSHIFT 1 -> bl\n" +
            "kk RSHIFT 2 -> kl\n" +
            "b AND n -> p\n" +
            "ln AND lp -> lq\n" +
            "cj AND cp -> cr\n" +
            "dl AND dn -> do\n" +
            "ci RSHIFT 2 -> cj\n" +
            "as OR bd -> be\n" +
            "ge LSHIFT 15 -> gi\n" +
            "hz RSHIFT 5 -> ic\n" +
            "dv LSHIFT 1 -> ep\n" +
            "kl OR kr -> ks\n" +
            "gj OR gu -> gv\n" +
            "he RSHIFT 5 -> hh\n" +
            "NOT fg -> fh\n" +
            "hg AND hh -> hj\n" +
            "b OR n -> o\n" +
            "jk LSHIFT 15 -> jo\n" +
            "gz LSHIFT 15 -> hd\n" +
            "cy LSHIFT 15 -> dc\n" +
            "kk RSHIFT 5 -> kn\n" +
            "ci RSHIFT 3 -> ck\n" +
            "at OR az -> ba\n" +
            "iu RSHIFT 3 -> iw\n" +
            "ko AND kq -> kr\n" +
            "NOT eh -> ei\n" +
            "aq OR ar -> as\n" +
            "iy AND ja -> jb\n" +
            "dd RSHIFT 3 -> df\n" +
            "bn RSHIFT 3 -> bp\n" +
            "1 AND cc -> cd\n" +
            "at AND az -> bb\n" +
            "x OR ai -> aj\n" +
            "kk AND kv -> kx\n" +
            "ao OR an -> ap\n" +
            "dy RSHIFT 3 -> ea\n" +
            "x RSHIFT 1 -> aq\n" +
            "eu AND fa -> fc\n" +
            "kl AND kr -> kt\n" +
            "ia AND ig -> ii\n" +
            "df AND dg -> di\n" +
            "NOT fx -> fy\n" +
            "k AND m -> n\n" +
            "bn RSHIFT 5 -> bq\n" +
            "km AND kn -> kp\n" +
            "dt LSHIFT 15 -> dx\n" +
            "hz RSHIFT 2 -> ia\n" +
            "aj AND al -> am\n" +
            "cd LSHIFT 15 -> ch\n" +
            "hc OR hd -> he\n" +
            "he RSHIFT 3 -> hg\n" +
            "bn OR by -> bz\n" +
            "NOT kt -> ku\n" +
            "z AND aa -> ac\n" +
            "NOT ak -> al\n" +
            "cu AND cw -> cx\n" +
            "NOT ie -> if\n" +
            "dy RSHIFT 2 -> dz\n" +
            "ip LSHIFT 15 -> it\n" +
            "de OR dk -> dl\n" +
            "au OR av -> aw\n" +
            "jg AND ji -> jj\n" +
            "ci AND ct -> cv\n" +
            "dy RSHIFT 5 -> eb\n" +
            "hx OR hy -> hz\n" +
            "eu OR fa -> fb\n" +
            "gj RSHIFT 3 -> gl\n" +
            "fo AND fz -> gb\n" +
            "1 AND jj -> jk\n" +
            "jp OR ka -> kb\n" +
            "de AND dk -> dm\n" +
            "ex AND ez -> fa\n" +
            "df OR dg -> dh\n" +
            "iv OR jb -> jc\n" +
            "x RSHIFT 5 -> aa\n" +
            "NOT hj -> hk\n" +
            "NOT im -> in\n" +
            "fl LSHIFT 1 -> gf\n" +
            "hu LSHIFT 15 -> hy\n" +
            "iq OR ip -> ir\n" +
            "iu RSHIFT 5 -> ix\n" +
            "NOT fc -> fd\n" +
            "NOT el -> em\n" +
            "ck OR cl -> cm\n" +
            "et RSHIFT 3 -> ev\n" +
            "hw LSHIFT 1 -> iq\n" +
            "ci RSHIFT 5 -> cl\n" +
            "iv AND jb -> jd\n" +
            "dd RSHIFT 5 -> dg\n" +
            "as RSHIFT 2 -> at\n" +
            "NOT jy -> jz\n" +
            "af AND ah -> ai\n" +
            "1 AND ds -> dt\n" +
            "jx AND jz -> ka\n" +
            "da LSHIFT 1 -> du\n" +
            "fs AND fu -> fv\n" +
            "jp RSHIFT 1 -> ki\n" +
            "iw AND ix -> iz\n" +
            "iw OR ix -> iy\n" +
            "eo LSHIFT 15 -> es\n" +
            "ev AND ew -> ey\n" +
            "ba AND bc -> bd\n" +
            "fp AND fv -> fx\n" +
            "jc AND je -> jf\n" +
            "et RSHIFT 2 -> eu\n" +
            "kg OR kf -> kh\n" +
            "iu OR jf -> jg\n" +
            "er OR es -> et\n" +
            "fo RSHIFT 2 -> fp\n" +
            "NOT ca -> cb\n" +
            "bv AND bx -> by\n" +
            "u LSHIFT 1 -> ao\n" +
            "cm AND co -> cp\n" +
            "y OR ae -> af\n" +
            "bn AND by -> ca\n" +
            "1 AND ke -> kf\n" +
            "jt AND jv -> jw\n" +
            "fq OR fr -> fs\n" +
            "dy AND ej -> el\n" +
            "NOT kc -> kd\n" +
            "ev OR ew -> ex\n" +
            "dd OR do -> dp\n" +
            "NOT cv -> cw\n" +
            "gr AND gt -> gu\n" +
            "dd RSHIFT 1 -> dw\n" +
            "NOT gw -> gx\n" +
            "NOT iz -> ja\n" +
            "1 AND io -> ip\n" +
            "NOT ag -> ah\n" +
            "b RSHIFT 5 -> f\n" +
            "NOT cr -> cs\n" +
            "kb AND kd -> ke\n" +
            "jr AND js -> ju\n" +
            "cq AND cs -> ct\n" +
            "il AND in -> io\n" +
            "NOT ju -> jv\n" +
            "du OR dt -> dv\n" +
            "dd AND do -> dq\n" +
            "b RSHIFT 2 -> d\n" +
            "jm LSHIFT 1 -> kg\n" +
            "NOT dq -> dr\n" +
            "bo OR bu -> bv\n" +
            "gk OR gq -> gr\n" +
            "he OR hp -> hq\n" +
            "NOT h -> i\n" +
            "hf AND hl -> hn\n" +
            "gv AND gx -> gy\n" +
            "x AND ai -> ak\n" +
            "bo AND bu -> bw\n" +
            "hq AND hs -> ht\n" +
            "hz RSHIFT 1 -> is\n" +
            "gj RSHIFT 5 -> gm\n" +
            "g AND i -> j\n" +
            "gk AND gq -> gs\n" +
            "dp AND dr -> ds\n" +
            "b RSHIFT 3 -> e\n" +
            "gl AND gm -> go\n" +
            "gl OR gm -> gn\n" +
            "y AND ae -> ag\n" +
            "hv OR hu -> hw\n" +
            "1674 -> b\n" +
            "ab AND ad -> ae\n" +
            "NOT ac -> ad\n" +
            "1 AND ht -> hu\n" +
            "NOT hn -> ho";

    private static final List<String> getInput() {
        return Arrays.asList(INPUT.split("\n"));
    }

    public static class Puzzle1 {

        private static final String NUMBER = "^\\d+$";
        private final Map<String, String> wireSources = getInput().stream().map(i -> i.split(" -> ")).collect(Collectors.toMap(s -> s[1], s -> s[0]));
        private final Map<String, Integer> solutions = new HashMap<>();

        private int getValue(String wire) {
            if (wire.matches(NUMBER)) {
                return Integer.parseInt(wire);
            }
            String operation = wireSources.get(wire);
            String[] words = operation.split(" ");
            if (words.length == 1) {
                return solutions.computeIfAbsent(words[0], this::getValue);
            }
            if (words[0].equals("NOT")) {
                return solutions.computeIfAbsent(words[1], this::getValue) ^ 0xffff;
            }
            if (words[1].equals("AND")) {
                return solutions.computeIfAbsent(words[0], this::getValue) & solutions.computeIfAbsent(words[2], this::getValue);
            }
            if (words[1].equals("OR")) {
                return solutions.computeIfAbsent(words[0], this::getValue) | solutions.computeIfAbsent(words[2], this::getValue);
            }
            if (words[1].equals("LSHIFT")) {
                return (solutions.computeIfAbsent(words[0], this::getValue) << solutions.computeIfAbsent(words[2], this::getValue)) & 0xffff;
            }
            if (words[1].equals("RSHIFT")) {
                return solutions.computeIfAbsent(words[0], this::getValue) >> solutions.computeIfAbsent(words[2], this::getValue);
            }
            throw new RuntimeException("Unparseable: " + wire);
        }

        public int solve() {
            return getValue("a");
        }

        public static void main(String... arguments) {
            System.out.println(new Puzzle1().solve());
        }

    }

    public static class Puzzle2 {

        private static final String NUMBER = "^\\d+$";

        private static Map<String, String> getSources() {
            return getInput().stream().map(i -> i.split(" -> ")).collect(Collectors.toMap(s -> s[1], s -> s[0]));
        }

        private int getValue(Map<String, String> sources, Map<String, Integer> solutions, String wire) {
            if (wire.matches(NUMBER)) {
                return Integer.parseInt(wire);
            }
            String operation = sources.get(wire);
            String[] words = operation.split(" ");
            if (words.length == 1) {
                return solutions.computeIfAbsent(words[0], w -> getValue(sources, solutions, w));
            }
            if (words[0].equals("NOT")) {
                return solutions.computeIfAbsent(words[1], w -> getValue(sources, solutions, w)) ^ 0xffff;
            }
            if (words[1].equals("AND")) {
                return solutions.computeIfAbsent(words[0], w -> getValue(sources, solutions, w)) & solutions.computeIfAbsent(words[2], w -> getValue(sources, solutions, w));
            }
            if (words[1].equals("OR")) {
                return solutions.computeIfAbsent(words[0], w -> getValue(sources, solutions, w)) | solutions.computeIfAbsent(words[2], w -> getValue(sources, solutions, w));
            }
            if (words[1].equals("LSHIFT")) {
                return (solutions.computeIfAbsent(words[0], w -> getValue(sources, solutions, w)) << solutions.computeIfAbsent(words[2], w -> getValue(sources, solutions, w))) & 0xffff;
            }
            if (words[1].equals("RSHIFT")) {
                return solutions.computeIfAbsent(words[0], w -> getValue(sources, solutions, w)) >> solutions.computeIfAbsent(words[2], w -> getValue(sources, solutions, w));
            }
            throw new RuntimeException("Unparseable: " + wire);
        }

        public int solve() {
            Map<String, String> sources = getSources();
            Map<String, Integer> solutions = new HashMap<>();
            int valueForA = getValue(sources, solutions, "a");
            sources = new HashMap<>(getSources());
            sources.put("b", String.valueOf(valueForA));
            solutions.clear();
            return getValue(sources, solutions, "a");
        }

        public static void main(String... arguments) {
            System.out.println(new Puzzle2().solve());
        }

    }

}
