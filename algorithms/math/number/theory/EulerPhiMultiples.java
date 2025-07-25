package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 21/07/25.
 */
// Algorithm to compute the number of co-primes to the number that are less than the number.
// Useful when there are many queries.
// O(n * log(log(n)))
public class EulerPhiMultiples {

    private static int[] eratosthenesSieveEulerPhi(int maxNumber) {
        int[] eulerPhi = new int[maxNumber + 1];
        for (int i = 0; i < eulerPhi.length; i++) {
            eulerPhi[i] = i;
        }

        for (int i = 2; i < eulerPhi.length; i++) {
            if (eulerPhi[i] == i) {
                for (int j = i; j < eulerPhi.length; j += i) {
                    eulerPhi[j] = (eulerPhi[j] / i) * (i - 1);
                }
            }
        }
        return eulerPhi;
    }
}
