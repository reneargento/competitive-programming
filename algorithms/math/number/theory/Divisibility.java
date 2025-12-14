package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 21/10/25.
 */
// Methods to test if a number is divisible by 2 up to 12
public class Divisibility {

    private static boolean isDivisibleBy2(String number) {
        int lastDigit = Character.getNumericValue(number.charAt(number.length() - 1));
        return lastDigit % 2 == 0;
    }

    private static boolean isDivisibleBy3(String number) {
        int digitSum = 0;
        for (char digit : number.toCharArray()) {
            digitSum += Character.getNumericValue(digit);
        }
        return digitSum % 3 == 0;
    }

    private static boolean isDivisibleBy4(String number) {
        int startIndex = Math.max(0, number.length() - 2);
        int last2Digits = Integer.parseInt(number.substring(startIndex));
        return last2Digits % 4 == 0;
    }

    private static boolean isDivisibleBy5(String number) {
        int lastDigit = Character.getNumericValue(number.charAt(number.length() - 1));
        return lastDigit == 0 || lastDigit == 5;
    }

    private static boolean isDivisibleBy6(String number) {
        return isDivisibleBy2(number) && isDivisibleBy3(number);
    }

    private static boolean isDivisibleBy7(String number) {
        int sum = 0;
        boolean add = false;

        for (int i = number.length() - 1; i >= 0; i -= 3) {
            int startIndex = Math.max(0, i - 2);
            int value = Integer.parseInt(number.substring(startIndex, i + 1));

            if (add) {
                sum += value;
            } else {
                sum -= value;
            }
            add = !add;
        }
        return sum % 7 == 0;
    }

    private static boolean isDivisibleBy8(String number) {
        int startIndex = Math.max(0, number.length() - 3);
        int last3Digits = Integer.parseInt(number.substring(startIndex));
        return last3Digits % 8 == 0;
    }

    private static boolean isDivisibleBy9(String number) {
        int digitSum = 0;
        for (char digit : number.toCharArray()) {
            digitSum += Character.getNumericValue(digit);
        }
        return digitSum % 9 == 0;
    }

    private static boolean isDivisibleBy10(String number) {
        int lastDigit = Character.getNumericValue(number.charAt(number.length() - 1));
        return lastDigit == 0;
    }

    private static boolean isDivisibleBy11(String number) {
        int digitSum = 0;
        boolean add = false;

        for (char digit : number.toCharArray()) {
            int value = Character.getNumericValue(digit);
            if (add) {
                digitSum += value;
            } else {
                digitSum -= value;
            }
            add = !add;
        }
        return digitSum % 11 == 0;
    }

    private static boolean isDivisibleBy12(String number) {
        return isDivisibleBy3(number) && isDivisibleBy4(number);
    }
}
