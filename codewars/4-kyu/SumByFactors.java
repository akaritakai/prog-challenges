import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * http://www.codewars.com/kata/sum-by-factors/
 */
public class SumByFactors {

    public static String sumOfDivided(int[] l) {
        int maxInList = Arrays.stream(l)
                .max()
                .getAsInt();
        // Credit to https://dzone.com/articles/whats-wrong-java-8-part-vii for this functional primality check.
        IntPredicate isPrime = n -> n < 4 || !(n % 2 == 0 ||
                IntStream.range(2, (int) Math.sqrt(n) + 1)
                        .filter(x -> x % 2 != 0 && n % x == 0)
                        .anyMatch(x -> true));
        IntStream primeStream = IntStream.range(2, maxInList)
                .filter(isPrime);
        Function<Integer, Integer> sumForPrime = p -> Arrays.stream(l)
                .filter(n -> n % p == 0)
                .sum();
        return primeStream
                .filter(n -> sumForPrime.apply(n) != 0)
                .mapToObj(n -> "(" + n + " " + sumForPrime.apply(n) + ")")
                .collect(Collectors.joining());
    }

}
