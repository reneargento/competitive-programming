package algorithms.recursion.permutations;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 04/09/25.
 */
// Given a number n and a permutation index k, computes the kth permutation of the sequence 1 2 3 ... n
// n <= 50
public class PermutationByIndex {

    public static void main(String[] args) {
        BigInteger[] factorials = computeFactorials();

        int[] targetPermutation1 = computePermutation(factorials, 3, BigInteger.valueOf(4));
        printPermutation(targetPermutation1);
        System.out.println("Expected: 3 1 2\n");

        int[] targetPermutation2 = computePermutation(factorials, 10, BigInteger.valueOf(3));
        printPermutation(targetPermutation2);
        System.out.println("Expected: 1 2 3 4 5 6 7 9 10 8");
    }

    private static void printPermutation(int[] permutation) {
        System.out.print(permutation[0]);
        for (int i = 1; i < permutation.length; i++) {
            System.out.print(" " + permutation[i]);
        }
        System.out.println();
    }

    private static int[] computePermutation(BigInteger[] factorials , int n, BigInteger k) {
        int[] targetPermutation = new int[n];
        Set<Integer> usedValues = new HashSet<>();
        computePermutation(factorials, usedValues, n, targetPermutation, k, 0);
        return targetPermutation;
    }

    private static void computePermutation(BigInteger[] factorials, Set<Integer> usedValues, int n,
                                           int[] targetPermutation, BigInteger k, int index) {
        if (index == n) {
            return;
        }
        int size = n - index;
        BigInteger factorial = factorials[size];

        BigInteger sectionSize = factorial.divide(BigInteger.valueOf(size));
        int sectionValue = k.divide(sectionSize).intValue() + 1;

        int finalValue = getFinalValue(usedValues, sectionValue);
        usedValues.add(finalValue);
        targetPermutation[index] = finalValue;

        BigInteger nextK = k.mod(sectionSize);
        computePermutation(factorials, usedValues, n, targetPermutation, nextK, index + 1);
    }

    private static int getFinalValue(Set<Integer> usedValues, int sectionValue) {
        int usedValuesCount = 0;
        int notUsedValuesCount = 0;
        int currentValue = 1;

        while (notUsedValuesCount < sectionValue) {
            if (usedValues.contains(currentValue)) {
                usedValuesCount++;
            } else {
                notUsedValuesCount++;
            }
            currentValue++;
        }
        return sectionValue + usedValuesCount;
    }

    private static BigInteger[] computeFactorials() {
        BigInteger[] factorials = new BigInteger[51];
        factorials[0] = BigInteger.ONE;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
        }
        return factorials;
    }
}
