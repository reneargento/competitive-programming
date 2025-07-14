package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 17/05/25.
 */
// Computes (a / b) % m
public class ModularMultiplicativeInverse {

    public static void main(String[] args) {
        int divMod1 = divMod(27, 3, 7);
        System.out.println("(27 / 3) % 7 = " + divMod1);
        System.out.println("Expected: 2");

        int divMod2 = divMod(27, 4, 7);
        System.out.println("\n(27 / 4) % 7 = " + divMod2);
        System.out.println("Expected: 5");

        int divMod3 = divMod(520, 25, 18);
        System.out.println("\n(520 / 25) % 18 = " + divMod3);
        System.out.println("Expected: 10");
    }

    // Computes (a / b) % m
    // It is the equivalent to ((a % m) * (b^(-1) % m)) % m
    private static int divMod(int a, int b, int m) {
        int modInverseB = (modularMultiplicativeInverse(b, m));
        if (modInverseB == -1) {
            return -1;
        }
        return mod(mod(a, m) * modInverseB, m);
    }

    // Computes b^(-1) % m
    private static int modularMultiplicativeInverse(int b, int m) {
        extendedEuclid(b, m);
        if (gcd != 1) {
            // b and m are not relatively prime, there is no solution
            return -1;
        }
        // b * bezoutCoefficient1 + m * bezoutCoefficient2 = 1, now apply mod(m) to get b * bezoutCoefficient1 = 1 % m
        return mod(bezoutCoefficient1, m);
    }

    private static int mod(int number, int mod) {
        return ((number % mod) + mod) % mod;
    }

    private static int bezoutCoefficient1;
    private static int bezoutCoefficient2;
    private static int gcd;

    private static void extendedEuclid(int number1, int number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        int nextBezoutCoefficient1 = bezoutCoefficient2;
        int nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
    }
}
