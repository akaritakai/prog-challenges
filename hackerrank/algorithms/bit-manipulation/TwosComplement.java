import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/2s-complement
 */
public class TwosComplement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = scanner.nextInt(); i > 0; i--) {
            long a = scanner.nextLong();
            long b = scanner.nextLong();
            if (a < 0 && b < 0) {
                System.out.println(negSum(a) - negSum(b + 1));
            }
            else if (a < 0) {
                System.out.println(negSum(a) + posSum(b));
            }
            else {
                System.out.println(posSum(b) - posSum(a - 1));
            }
        }
    }

    private static long posSum(long n) {
        return A000788(n);
    }

    private static long negSum(long n) {
        n = Math.abs(n);
        return 32 * n - A000788(n - 1);
    }

    /**
     * A000788 - Total number of 1's in binary expansions of 0...n.
     * This can also be thought of as the sum of the hamming weights 1...n,
     * so there are a lot of neat bit manipulation tricks for this.
     */
    private static long A000788(long n) {
        long v = 0;
        for (long b = 1; b <= n; b <<= 1) {
            if ((n & b) != 0) {
                v += ((n >> 1) & ~(b - 1)) + (n & ((b << 1) - 1)) - (b - 1);
            }
            else {
                v += ((n >> 1) & ~(b - 1));
            }
        }
        return v;
    }

}
