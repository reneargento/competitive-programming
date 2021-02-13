package algorithms.bitwise.operations;

/**
 * Created by Rene Argento on 17/01/21.
 */
public class DecimalToBinary {

    public static String decimalToBinary(long decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }
        StringBuilder binaryString = new StringBuilder();

        while (decimalValue > 0) {
            long remaining = decimalValue % 2;
            binaryString.insert(0, remaining);

            decimalValue /= 2;
        }
        return binaryString.toString();
    }
}
