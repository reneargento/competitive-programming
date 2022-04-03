package algorithms.math;

/**
 * Created by Rene Argento on 22/01/22.
 */
// Computes the last surviving person given the circle size and the skip length.
// The answer is based on an 1-based index.
public class Josephus {

    public static void main(String[] args) {
        int survivor1 = josephus(14, 2) + 1;
        System.out.println("Survivor (general case): " + survivor1 + " Expected: 13");

        int survivor2 = josephusSkip2(14);
        System.out.println("Survivor (special case): " + survivor2 + " Expected: 13");

        int survivor3 = josephusIterative(14, 2) + 1;
        System.out.println("Survivor (iterative case): " + survivor3 + " Expected: 13");
    }

    // The index is 0 based, so add 1 at the answer in the end.
    private static int josephus(int circleSize, int skip) {
        if (circleSize == 1) {
            return 0;
        }
        return (josephus(circleSize - 1, skip) + skip) % circleSize;
    }

    // Special case of josephus with skip 2 can be solved in O(1) by:
    // 1- Converting the circle size to binary
    // 2- Moving the most significant bit to the end
    // 3- Converting the bits to decimal. That is the survivor position.
    private static int josephusSkip2(int circleSize) {
        String bits = toBinary(circleSize);
        bits = bits.substring(1) + bits.charAt(0);
        return Integer.parseInt(bits, 2);
    }

    private static String toBinary(int number) {
        StringBuilder bits = new StringBuilder();
        while (number > 0) {
            int bit = number % 2;
            number /= 2;
            bits.append(bit);
        }
        return bits.reverse().toString();
    }

    // Iterative version of the general recurrence.
    // The index is 0 based, so add 1 at the answer in the end.
    private static int josephusIterative(int circleSize, int skip) {
        int position = 0;
        int currentCircleSize = 2;

        for (int i = 1; i < circleSize; i++) {
            position = (position + skip) % currentCircleSize;
            currentCircleSize++;
        }
        return position;
    }
}
