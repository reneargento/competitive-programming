package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 08/09/17.
 */
// Computes:
// bezoutCoefficient1 * x + bezoutCoefficient2 * y = gcd(x, y)
public class ExtendedEuclid {

    public static void main(String[] args) {
        extendedEuclid(25, 18);

        System.out.println("Bezout coefficient1: " + bezoutCoefficient1 + " Expected: -5");
        System.out.println("Bezout coefficient2: " + bezoutCoefficient2 + " Expected: 7");
        System.out.println("GCD: " + gcd + " Expected: 1");

        Result result = new Result();
        extendedEuclidIterative(25, 18, result);

        System.out.println("\nBezout coefficient1: " + result.bezoutCoefficient1 + " Expected: -5");
        System.out.println("Bezout coefficient2: " + result.bezoutCoefficient2 + " Expected: 7");
        System.out.println("GCD: " + result.gcd + " Expected: 1");
    }

    // Bezout identity
    private static int bezoutCoefficient1;
    private static int bezoutCoefficient2;
    private static int gcd;

    private static void extendedEuclid(int number1, int number2) {
        // Base case
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        // Just like euclid()
        extendedEuclid(number2, number1 % number2);

        int nextBezoutCoefficient1 = bezoutCoefficient2;
        int nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
    }

    // Iterative version
    private static class Result {
        int bezoutCoefficient1;
        int bezoutCoefficient2;
        int gcd;
    }

    private static void extendedEuclidIterative(int number1, int number2, Result result) {
        int bezoutCoefficient1 = 1;
        int bezoutCoefficient2 = 0;
        int x = 0;
        int y = 1;

        while (number2 > 0) {
            int q = number1 / number2;

            int aux = number2;
            number2 = number1 % number2;
            number1 = aux;

            aux = x;
            x = bezoutCoefficient1 - q * x;
            bezoutCoefficient1 = aux;

            aux = y;
            y = bezoutCoefficient2 - q * y;
            bezoutCoefficient2 = aux;
        }

        result.bezoutCoefficient1 = bezoutCoefficient1;
        result.bezoutCoefficient2 = bezoutCoefficient2;
        result.gcd = number1;
    }
}
