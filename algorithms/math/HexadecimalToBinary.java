package algorithms.math;

/**
 * Created by Rene Argento on 12/03/23.
 */
public class HexadecimalToBinary {

    public static void main(String[] args) {
        String hexString = "AA2F";
        String binaryString = hexadecimalToBinary(hexString);

        System.out.printf("%-15s %s\n", "Binary string:", binaryString);
        System.out.printf("%-15s %s\n", "Expected:", "1010101000101111");
    }

    private static String hexadecimalToBinary(String hexString){
        hexString = hexString.replaceAll("0", "0000");
        hexString = hexString.replaceAll("1", "0001");
        hexString = hexString.replaceAll("2", "0010");
        hexString = hexString.replaceAll("3", "0011");
        hexString = hexString.replaceAll("4", "0100");
        hexString = hexString.replaceAll("5", "0101");
        hexString = hexString.replaceAll("6", "0110");
        hexString = hexString.replaceAll("7", "0111");
        hexString = hexString.replaceAll("8", "1000");
        hexString = hexString.replaceAll("9", "1001");
        hexString = hexString.replaceAll("A", "1010");
        hexString = hexString.replaceAll("B", "1011");
        hexString = hexString.replaceAll("C", "1100");
        hexString = hexString.replaceAll("D", "1101");
        hexString = hexString.replaceAll("E", "1110");
        hexString = hexString.replaceAll("F", "1111");
        return hexString;
    }
}
