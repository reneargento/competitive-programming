package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 14/01/22.
 */
// Checks if a big number represented by a digits array is divisible by an integer X
public class IsBigNumberDivisibleByX {

    private static boolean isDivisible(int[] number, int length, int lastDigit, int xDivisor) {
        int currentNumber = 0;

        for (int i = 0; i < length; i++) {
            currentNumber = currentNumber * 10 + number[i];
            currentNumber %= xDivisor;
        }
        currentNumber = currentNumber * 10 + lastDigit;

        return currentNumber % xDivisor == 0;
    }
}
