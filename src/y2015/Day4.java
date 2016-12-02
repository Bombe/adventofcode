package y2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO
 *
 * @author <a href="mailto:d.roden@emetriq.com">David Roden</a>
 */
public class Day4 {

    static String INPUT = "bgvyzdsv";

    static class Puzzle1 {

        public static void main(String[] args) throws NoSuchAlgorithmException {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int number = 1;
            while (true) {
                md5.reset();
                md5.update((INPUT + number).getBytes());
                byte[] digest = md5.digest();
                if ((digest[0] == 0) && (digest[1] == 0) && (digest[2] < 16)) {
                    System.out.println(number);
                    break;
                }
                number++;
            }
        }

    }

    static class Puzzle2 {

        public static void main(String[] args) throws NoSuchAlgorithmException {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int number = 1;
            while (true) {
                md5.reset();
                md5.update((INPUT + number).getBytes());
                byte[] digest = md5.digest();
                if ((digest[0] == 0) && (digest[1] == 0) && (digest[2] == 0)) {
                    System.out.println(number);
                    break;
                }
                number++;
            }
        }

    }

}
