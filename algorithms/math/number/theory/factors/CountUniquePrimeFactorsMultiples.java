package algorithms.math.number.theory.factors;

/**
 * Created by Rene Argento on 18/07/25.
 */
// Algorithm to count the number of unique prime factors of a range of numbers.
// O(n * log(log(n)))
public class CountUniquePrimeFactorsMultiples {

    public static void main(String[] args) {
        int[] uniquePFs = eratosthenesSieveCountUniquePFs(1000000);
        System.out.println("Unique PFs: " + uniquePFs[289384]);
        System.out.println("Expected: 3");
        System.out.println("\nUnique PFs: " + uniquePFs[692778]);
        System.out.println("Expected: 5");
    }

    private static int[] eratosthenesSieveCountUniquePFs(int maxNumber) {
        int[] uniquePFs = new int[maxNumber];

        for (int i = 2; i < uniquePFs.length; i++) {
            if (uniquePFs[i] == 0) {
                for (int j = i; j < uniquePFs.length; j += i) {
                    uniquePFs[j]++;
                }
            }
        }
        return uniquePFs;
    }
}
