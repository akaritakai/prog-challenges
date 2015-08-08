/**
 * http://www.codewars.com/kata/can-you-get-the-loop/
 */
public class CanYouGetTheLoop {

    /**
     * The tortoise and hare algorithm (Floyd's cycle-finding algorithm) is a classic
     * cycle detection method, and is very important to know. After all:
     *
     * Those who don't know of cycle detection are doomed to repeat it.
     */
    public int loopSize(Node node) {
        Node tortoise = node;
        Node hare = node.getNext();
        while (tortoise != hare) {
            tortoise = tortoise.getNext();
            hare = hare.getNext().getNext();
        }
        int count = 1;
        hare = tortoise.getNext();
        while (tortoise != hare) {
            hare = hare.getNext();
            count++;
        }
        return count;
    }

}
