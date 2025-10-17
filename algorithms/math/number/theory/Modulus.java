package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 16/10/25.
 */
// Computes the result of number % mod
// Note that this is different from the remainder operation done with the % operator in Java.
// The results are the same, but differ when computing the modulus of a negative number
public class Modulus {

    public static void main(String[] args) {
        int mod1 = mod(-4, 3);
        System.out.println("Mod: " + mod1);
        System.out.println("Expected: 2");

        int mod2 = mod(-5, 3);
        System.out.println("\nMod: " + mod2);
        System.out.println("Expected: 1");
    }

    private static int mod(int number, int mod) {
        return ((number % mod) + mod) % mod;
    }
}
