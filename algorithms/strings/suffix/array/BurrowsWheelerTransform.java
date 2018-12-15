package algorithms.strings.suffix.array;

/**
 * Created by Rene Argento on 16/09/18.
 */
// Generates both Burrows Wheeler Transform and Burrows Wheeler Inverse Transform in O(N)
// Assumes that the last character in the string is a special delimiter, smaller than all other characters.
// Based on https://www.coursera.org/lecture/algorithms-on-strings/inverting-burrows-wheeler-transform-C0opC
public class BurrowsWheelerTransform {

    private static final int ALPHABET_SIZE = 256;

    public String burrowsWheelerTransform(String string) {
        int[] circularSuffixArray = CircularSuffixArrayLinearTime.buildCircularSuffixArray(string);
        StringBuilder burrowsWheelerTransform = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            if (circularSuffixArray[i] == 0) {
                burrowsWheelerTransform.append(string.charAt(string.length() - 1));
            } else {
                burrowsWheelerTransform.append(string.charAt(circularSuffixArray[i] - 1));
            }
        }

        return burrowsWheelerTransform.toString();
    }

    public String burrowsWheelerInverseTransform(String burrowsWheelerTransform) {
        char[] firstColumn = new char[burrowsWheelerTransform.length()];
        int[] next = new int[firstColumn.length];

        // Counting sort
        int[] count = new int[ALPHABET_SIZE + 1];
        for (int i = 0; i < burrowsWheelerTransform.length(); i++) {
            count[burrowsWheelerTransform.charAt(i) + 1]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Finish sort and build next[] array
        for (int i = 0; i < burrowsWheelerTransform.length(); i++) {
            char currentChar = burrowsWheelerTransform.charAt(i);
            int index = count[currentChar]++;
            firstColumn[index] = currentChar;
            next[index] = i;
        }

        // Generate Burrows Wheeler Inverse transform
        StringBuilder burrowsWheelerInverseTransform = new StringBuilder();
        int index = next[0];

        for (int i = 0; i < next.length; i++) {
            burrowsWheelerInverseTransform.append(firstColumn[index]);
            index = next[index];
        }

        return burrowsWheelerInverseTransform.toString();
    }

    public static void main(String[] args) {
        BurrowsWheelerTransform burrowsWheelerTransform = new BurrowsWheelerTransform();

        System.out.println("Burrows Wheeler Transform:");

        String string1 = "mississippi$";
        String bwt1 = burrowsWheelerTransform.burrowsWheelerTransform(string1);
        System.out.println("BWT:      " + bwt1);
        System.out.println("Expected: ipssm$pissii");

        String string2 = "barcelona$";
        String bwt2 = burrowsWheelerTransform.burrowsWheelerTransform(string2);
        System.out.println("\nBWT:      " + bwt2);
        System.out.println("Expected: anb$rceola");

        String string3 = "banana$";
        String bwt3 = burrowsWheelerTransform.burrowsWheelerTransform(string3);
        System.out.println("\nBWT:      " + bwt3);
        System.out.println("Expected: annb$aa");

        System.out.println("\nBurrows Wheeler Inverse Transform:");

        String burrowsWheelerInverseTransform1 = burrowsWheelerTransform.burrowsWheelerInverseTransform(bwt1);
        System.out.println("BWT Inverse: " + burrowsWheelerInverseTransform1);
        System.out.println("Expected:    mississippi$");

        String burrowsWheelerInverseTransform2 = burrowsWheelerTransform.burrowsWheelerInverseTransform(bwt2);
        System.out.println("\nBWT Inverse: " + burrowsWheelerInverseTransform2);
        System.out.println("Expected:    barcelona$");

        String burrowsWheelerInverseTransform3 = burrowsWheelerTransform.burrowsWheelerInverseTransform(bwt3);
        System.out.println("\nBWT Inverse: " + burrowsWheelerInverseTransform3);
        System.out.println("Expected:    banana$");
    }

}
