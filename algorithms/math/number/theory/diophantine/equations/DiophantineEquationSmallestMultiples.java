package algorithms.math.number.theory.diophantine.equations;

/**
 * Created by Rene Argento on 19/10/25.
 */
// Given a, b and c, solves an equation in the form of:
// a * x + b * y = c
// Where x is the smallest positive number possible. If there is a tie, return the solution with the smallest y.
// Based on the problem https://open.kattis.com/problems/wipeyourwhiteboards
public class DiophantineEquationSmallestMultiples {

    public static void main(String[] args) {
        Solution solution1 = diophantineEquationSmallest(3, -5, 15);
        System.out.println("Solution: " + solution1.x + " " + solution1.y);
        System.out.println("Expected: 10 3");

        Solution solution2 = diophantineEquationSmallest(110, -29, 1);
        System.out.println("\nSolution: " + solution2.x + " " + solution2.y);
        System.out.println("Expected: 24 91");
    }

    private static class Solution {
        long x;
        long y;

        public Solution(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static Solution diophantineEquationSmallest(long a, long b, long c) {
        extendedEuclid(a, b);

        long x = bezoutCoefficient1 * (c / gcd);
        long y = bezoutCoefficient2 * (c / gcd);

        long deltaX = b / gcd;
        long deltaY = a / gcd;

        long moveXForwardL = deltaX < 0 ? deltaX * -1 : deltaX;
        long moveXForwardR = deltaX < 0 ? deltaY * -1 : deltaY;
        long moveYForwardL = deltaY < 0 ? deltaX * -1 : deltaX;
        long moveYForwardR = deltaY < 0 ? deltaY * -1 : deltaY;

        if (x < 1) {
            if (x == 0) {
                x += moveXForwardL;
                y -= moveXForwardR;
            } else {
                long multiplier = Math.abs(x) / moveXForwardL + 1;
                x += moveXForwardL * multiplier;
                y -= moveXForwardR * multiplier;
            }
        }
        if (x > 1) {
            long multiplier = (x - 1) / moveXForwardL;
            x -= moveXForwardL * multiplier;
            y += moveXForwardR * multiplier;
        }

        if (y < 1) {
            if (y == 0) {
                x -= moveYForwardL;
                y += moveYForwardR;
            } else {
                long multiplier = Math.abs(y) / moveYForwardR + 1;
                x -= moveYForwardL * multiplier;
                y += moveYForwardR * multiplier;
            }
        }
        return new Solution(x, y);
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
