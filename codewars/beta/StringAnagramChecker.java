import java.util.Arrays;

/**
 * http://www.codewars.com/kata/string-anagram-checker/
 */
public class StringAnagramChecker {

    public static boolean isAnagram(String first, String second) {
        return Arrays.equals(first.chars().sorted().toArray(), second.chars().sorted().toArray());
    }

}
