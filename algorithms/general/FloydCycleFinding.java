package algorithms.general;

/**
 * Created by Rene Argento on 09/09/17.
 */
public class FloydCycleFinding {

    private static class Node {
        private int val;
        private Node next;

        Node(int x) {
            val = x;
            next = null;
        }
    }

    public static void main(String[] args) {
        Node first = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);

        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = first; //Cycle

        Node cycleNode = detectCycle(first);
        if (cycleNode != null) {
            System.out.println(cycleNode.val);
        }
        System.out.println("Expected: 1");
    }

    //Floyd's cycle detection algorithm
    public static Node detectCycle(Node a) {
        Node startNode = a;

        //If we want to remove the cycle it is better to start fast one position after slow
        //If we want to find the beginning of the cycle, it is better to start both together
        Node slow = a;
        Node fast = a;
        //Node fast = a.next;

        boolean hasCycle = false;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;

            fast = fast.next;
            fast = fast.next;

            if (slow != null && slow == fast) {
                hasCycle = true;
                break;
            }
        }

        if (!hasCycle) {
            return null;
        }

        //removeCycle(startNode, fast);

        while (a != fast) {
            a = a.next;
            fast = fast.next;
        }
        return fast;
    }

    private static void removeCycle(Node startNode, Node cycleMeetingPoint) {
        Node start = startNode; //Save reference for printing later

        while (cycleMeetingPoint.next != startNode) {
            startNode = startNode.next;
            cycleMeetingPoint = cycleMeetingPoint.next;
        }

        cycleMeetingPoint.next = null;

        printList(start);
    }

    private static void printList(Node node) {
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    //Floyd cycle detection algorithm with regular values
    private static int anyFunction(int value) {
        return value * 2;
    }

    /**
     * Floyd's cycle finding algorithm using a function instead of pointers
     *
     * @param number0 first value in the series
     * @return an array with values:
     * mu -> the cycle initial position
     * lambda -> the cycle length
     */
    private static int[] detectCycle(int number0) {
        // 1st part: finding k * mu
        // Hare's speed is 2x tortoise's
        int tortoise = anyFunction(number0);
        int hare = anyFunction(anyFunction(number0));

        while (tortoise != hare) {
            tortoise = anyFunction(tortoise);
            hare = anyFunction(anyFunction(hare));
        }

        // 2nd part: finding mu
        // Hare and tortoise have the same speed
        int mu = 0;
        hare = number0;

        while (tortoise != hare) {
            tortoise = anyFunction(tortoise);
            hare = anyFunction(hare);
            mu++;
        }

        // 3rd part: finding lambda
        // Hare moves (starting after tortoise's current position), tortoise stays
        int lambda = 1;
        hare = anyFunction(tortoise);

        while (tortoise != hare) {
            hare = anyFunction(hare);
            lambda++;
        }
        return new int[]{mu, lambda};
    }
}
