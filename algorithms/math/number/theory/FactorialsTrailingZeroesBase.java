package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 25/09/25.
 */
// Computes the number of trailing zeroes of a factorial of a number in a target base
// Example: 5! in base 10 is 120 = 1 trailing zero
// O (b * lg(n))
public class FactorialsTrailingZeroesBase {

    public static void main(String[] args) {
        int trailingZeroes1 = computeTrailingZeroes(23233, 344);
        System.out.println("Zeroes: " + trailingZeroes1);
        System.out.println("Expected: 552");

        int trailingZeroes2 = computeTrailingZeroes(1048575, 799);
        System.out.println("\nZeroes: " + trailingZeroes2);
        System.out.println("Expected: 22794");
    }

    private static int computeTrailingZeroes(int number, int base) {
        int trailingZeroes = Integer.MAX_VALUE;

        for (int i = 2; i <= base; i++) {
            int exponent = 0;

            while (base % i == 0) {
                exponent++;
                base /= i;
            }

            if (exponent > 0) {
                int trailingZeroesCandidate = 0;

                int primeCopy = i;
                while (primeCopy <= number) {
                    trailingZeroesCandidate += number / primeCopy;
                    primeCopy *= i;
                }
                trailingZeroesCandidate /= exponent;
                trailingZeroes = Math.min(trailingZeroes, trailingZeroesCandidate);
            }
        }
        return trailingZeroes;
    }
}
