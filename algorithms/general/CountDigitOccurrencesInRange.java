package algorithms.general;

/**
 * Created by Rene Argento on 02/05/20.
 */
// Counts the occurrences of digits D in a range of numbers [0 ... n]
// Example: [0 1 2 3 4 5 6 7 8 9 10 11 12 13]
// Digit: 3
// Result: 2 (occurs on numbers 3 and 13)
public class CountDigitOccurrencesInRange {

    // O(d) runtime, where d is the number of digits in the number
    // O(d) space
    public static long countDigitsDInRange(int number, int digitToCount) {
        long count = 0;
        int length = String.valueOf(number).length();

        for (int digit = 0; digit < length; digit++) {
            count += countDigitsInRangeAtDigit(number, digit, digitToCount);
        }
        return count;
    }

    private static long countDigitsInRangeAtDigit(int number, int digit, int digitToCount) {
        long powerOf10 = (long) Math.pow(10, digit);
        long nextPowerOf10 = powerOf10 * 10;

        long roundDown = number - (number % nextPowerOf10);
        long roundUp = roundDown + nextPowerOf10;

        long digitValue = (number / powerOf10) % 10;

        if (digitValue < digitToCount) {
            return roundDown / 10;
        } else if (digitValue > digitToCount) {
            return roundUp / 10;
        } else {
            long leftCount = roundDown / 10;
            long rightCount = number % powerOf10;
            return leftCount + 1 + rightCount;
        }
    }

    public static void main(String[] args) {
        long count1 = countDigitsDInRange(25, 2);
        System.out.println("Count: " + count1 + " Expected: 9");

        long count2 = countDigitsDInRange(222, 2);
        System.out.println("Count: " + count2 + " Expected: 69");

        long count3 = countDigitsDInRange(1, 2);
        System.out.println("Count: " + count3 + " Expected: 0");

        long count4 = countDigitsDInRange(372332879, 2);
        System.out.println("Count: " + count4 + " Expected: 400806638");

        long count5 = countDigitsDInRange(Integer.MAX_VALUE, 2);
        System.out.println("Count: " + count5 + " Expected: 2071027783");

        long count6 = countDigitsDInRange(37, 3);
        System.out.println("Count: " + count6 + " Expected: 12");
    }

}
