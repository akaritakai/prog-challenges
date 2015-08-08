import java.util.*;

/**
 * http://www.codewars.com/kata/5547cc7dcad755e480000004
 */
public class IsMyFriendCheating {
	
    /*
     * The sum of numbers 1..n is n*(n+1)/2
     * So, define the operation sum(n) as n -> n*(n+1)/2.
     *
     * For any pairs (a,b) in 1..n s.t. a < b, where
     * sum(n) - a - b = a * b, we note that
     *
     * sum(n-1)/(n+1) <= a < sqrt(sum(n)+1) - 1
     * and
     * b = (sum(n)-a)/(a+1)
     */
    public static List<long[]> removNb(long n) {
        LinkedList<long[]> pairs = new LinkedList<>();
        long sum = n * (n + 1) / 2;
        long lowerBound = ((n - 1) * n / 2) / (n + 1);
        long upperBound = (long) Math.sqrt(sum + 1) - 1;
        for (long a = upperBound; a >= lowerBound; a--) {
            long b = (sum - a)/(a + 1);
            if (a * b + a + b == sum) {
                pairs.addFirst(new long[]{a, b});
                pairs.addLast(new long[]{b, a});
            }
        }
        return pairs;
    }

}
