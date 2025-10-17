package algorithms.math.number.theory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 17/10/25.
 */
// Solves an equation in the form of:
// a * x + b * y = c
// Returns all solutions
public class DiophantineEquationsAllSolutions {

    private static class Solution {
        long x;
        long y;

        public Solution(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        List<Solution> diophantineEquations = diophantineEquation(20, 10, 100);

        if (diophantineEquations != null) {
            for (Solution solution : diophantineEquations) {
                System.out.println(solution.x + " " + solution.y);
            }
        }

        System.out.println("\nExpected:" );
        System.out.println("0 10\n" +
                "1 8\n" +
                "2 6\n" +
                "3 4\n" +
                "4 2\n" +
                "5 0");
    }

    private static List<Solution> diophantineEquation(long a, long b, long c) {
        List<Solution> solutions = new ArrayList<>();

        extendedEuclid(a, b);

        if (c % gcd != 0) {
            return null;
        }

        // First solutions are given by
        // x0 = bezoutCoefficient1 * (c / gcd)
        // y0 = bezoutCoefficient2 * (c / gcd)

        long x = bezoutCoefficient1 * (c / gcd);
        long y = bezoutCoefficient2 * (c / gcd);

        if (x < 0 && y < 0) {
            return null;
        }

        long deltaX = b / gcd;
        long deltaY = a / gcd;
        long delta = a * deltaX - b * deltaY;

        double low = Math.ceil((double) -x / deltaX);
        double high = Math.floor((double) y / deltaY);

        if (low > high) {
            return null;
        }
        if (delta >= 0) {
            x += low * deltaX;
            y -= low * deltaY;
        } else {
            x += high * deltaX;
            y -= high * deltaY;
        }

        solutions.add(new Solution(x, y));

        long nextX = x + b / gcd;
        long nextY = y - a / gcd;
        int multiplier = 1;

        while (nextY >= 0) {
            solutions.add(new Solution(nextX, nextY));

            multiplier++;
            nextX = x + b / gcd * multiplier;
            nextY = y - a / gcd * multiplier;
        }
        return solutions;
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;

    private static void extendedEuclid(long number1, long number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        long nextBezoutCoefficient1 = bezoutCoefficient2;
        long nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
    }
}
