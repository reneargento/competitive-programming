package algorithms.math.number.theory;

/**
 * Created by rene on 08/09/17.
 */
//Solves a equation in the form of:
// a * x + b * y = c
public class DiophantineEquations {

    public static void main(String[] args) {
        int[] solution = diophantineEquation(25, 18, 839);
        //int[] solution = diophantineEquation(18, 25, 839);

        if (solution[0] == 0) {
            System.out.println(solution[1] + ", " + solution[2]);
            System.out.println("Expected: 17, 23");
        }
    }

    /**
     * Solves a diophantine equation with 2 unknowns
     * @return An int[] with 3 values:
     *  0: A value representing whether the equation has an integer solution or not
     *     0 -> Has an integer solution
     *     1 -> Does not have an integer solution (in this case, parameters 1 and 2 will be -1)
     *     2 -> Has an integer solution but both values are negative (in this case, parameters 1 and 2 will be -1)
     *  1: x, the solution to the first unknown
     *  2: y, the solution to the second unknown
     */
    private static int[] diophantineEquation(int a, int b, int c) {
        int[] solution = {1, -1, -1};

        extendedEuclid(a, b);

        //The equation only has integer solutions if gcd(a, b) divides c
        if (c % gcd != 0) {
            return solution;
        }

        //First solutions are given by
        // x0 = bezoutCoefficient1 * (c / gcd)
        // y0 = bezoutCoefficient2 * (c / gcd)

        int x = bezoutCoefficient1 * (c / gcd);
        int y = bezoutCoefficient2 * (c / gcd);

        // If both values are negative, return
        if (x < 0 && y < 0) {
            solution[0] = 2;
            return solution;
        }

        //Make sure there are no negative values
        if (x < 0 || y < 0) {
            boolean isXNegative = x < 0;

            int factorToGetAPositiveSolution;

            double equalOrHigherThan;
            double equalOrLowerThan;

            if (isXNegative) {
                equalOrHigherThan = Math.abs(x) / (double) b;
                equalOrLowerThan = Math.abs(y) / (double) a;
            } else {
                equalOrHigherThan = Math.abs(y) / (double) a;
                equalOrLowerThan = Math.abs(x) / (double) b;
            }

            if (equalOrHigherThan == (int) equalOrHigherThan) {
                factorToGetAPositiveSolution = (int) equalOrHigherThan;
            } else if (equalOrLowerThan == (int) equalOrLowerThan) {
                factorToGetAPositiveSolution = (int) equalOrLowerThan;
            } else {
                factorToGetAPositiveSolution = ((int) equalOrHigherThan) + 1;
            }

            if (isXNegative) {
                x += (b / gcd) * factorToGetAPositiveSolution;
                y -= (a / gcd) * factorToGetAPositiveSolution;
            } else {
                x -= (b / gcd) * factorToGetAPositiveSolution;
                y += (a / gcd) * factorToGetAPositiveSolution;
            }
        }

        solution[0] = 0;
        solution[1] = x;
        solution[2] = y;

        return solution;
    }

    // bezoutCoefficient1 * x + bezoutCoefficient2 * y = gcd(x, y)

    //Bezout identity
    private static int bezoutCoefficient1;
    private static int bezoutCoefficient2;
    private static int gcd;

    private static  void extendedEuclid(int number1, int number2) {
        //Base case
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;

            return;
        }

        //Just like euclid()
        extendedEuclid(number2, number1 % number2);

        int nextBezoutCoefficient1 = bezoutCoefficient2;
        int nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
    }

}
