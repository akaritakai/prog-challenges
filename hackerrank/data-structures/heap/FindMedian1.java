import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/find-median-1/
 */
public class FindMedian1 {

    private static PriorityQueue<Float> minHeap = new PriorityQueue<>(100000);
    private static PriorityQueue<Float> maxHeap = new PriorityQueue<>(100000, (a, b) -> Float.compare(b, a));

    private static void addElement(float n) {
        // add
        if (n < maxHeap.peek()) {
            maxHeap.add(n);
        } else {
            minHeap.add(n);
        }
        // balance
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
        else if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.add(maxHeap.poll());
        }
    }

    private static float getMedian() {
        int n = minHeap.size() - maxHeap.size();
        if (n == 0) {
            return (minHeap.peek() + maxHeap.peek()) / 2f;
        }
        else if (n > 0) {
            return minHeap.peek();
        } else {
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        if (size == 1 || size == 2) {
            // early
            if (size == 1) {
                System.out.println(scanner.nextFloat());
            } else {
                System.out.println((scanner.nextFloat() + scanner.nextFloat()) / 2f);
            }
        } else {
            // early
            float a = scanner.nextFloat();
            System.out.println(a);
            float b = scanner.nextFloat();
            System.out.println((a+b)/2f);
            maxHeap.add(Math.min(a, b));
            minHeap.add(Math.max(a, b));
            // late
            for (int i = 2; i < size; i++) {
                addElement(scanner.nextInt());
                System.out.println(getMedian());
            }
        }
    }


}
