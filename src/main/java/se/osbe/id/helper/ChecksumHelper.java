package se.osbe.id.helper;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.stream.IntStream.range;

/**
 * @author Oskar Bergström
 * <p>
 * Class to handle checksum calculations
 * (used internally made for speedy calcs, thus no validation on input args).
 */
public class ChecksumHelper {
    private static final int INDEX_START = 0;
    private static final int INDEX_BEFORE_CHECKSUM = 9;
    private static final int BASE = 10;
    private static final int ODD = 1;
    private static final int EVEN = 2;

    public static String calculateChecksum(String str) {
        return calculateChecksum(str.substring(INDEX_START, INDEX_BEFORE_CHECKSUM).toCharArray());
    }

    public static String calculateChecksum(char[] strArray) {
        return valueOf((BASE - (range(INDEX_START, INDEX_BEFORE_CHECKSUM)
                .map(i -> parseInt(valueOf(strArray[i])) * (i % EVEN == 0 ? EVEN : ODD))
                .map(s -> ((s >= BASE) ? (1 + (s % BASE)) % BASE : s))
                .sum() % BASE)) % BASE);
    }
}
