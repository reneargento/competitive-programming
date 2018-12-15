package algorithms.number.theory;

/**
 * Created by rene on 08/09/17.
 */
public class ExtendedEuclid {

    public static void main(String[] args) {
        extendedEuclid(25, 18);

        System.out.println("Bezout coefficient1: " + bezoutCoefficient1 + " Expected: -5");
        System.out.println("Bezout coefficient2: " + bezoutCoefficient2 + " Expected: 7");
        System.out.println("GCD: " + gcd + " Expected: 1");
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
