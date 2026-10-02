package algorithms.strings.hashing;

public class SubstringHash {

    private static final long PRIME_NUMBER = 1000000007L;
    private static final int ALPHABET_SIZE = 256;

    public static long getSubstringHash(String string, int startIndex, int endIndex) {
        long[] prefixHash = computePrefixHash(string);
        long[] powers = computePowers(string.length());
        return getHash(prefixHash, powers, startIndex, endIndex);
    }

    private static long[] computePrefixHash(String string) {
        long[] prefixHash = new long[string.length() + 1];
        for (int i = 0; i < string.length(); i++) {
            prefixHash[i + 1] = (prefixHash[i] * ALPHABET_SIZE + string.charAt(i)) % PRIME_NUMBER;
        }
        return prefixHash;
    }

    private static long[] computePowers(int length) {
        long[] powers = new long[length + 1];
        powers[0] = 1;
        for (int i = 1; i < powers.length; i++) {
            powers[i] = (powers[i - 1] * ALPHABET_SIZE) % PRIME_NUMBER;
        }
        return powers;
    }

    private static long getHash(long[] prefixHash, long[] powers, int left, int right) {
        return (prefixHash[right]
                - prefixHash[left] * powers[right - left] % PRIME_NUMBER
                + PRIME_NUMBER) % PRIME_NUMBER;
    }

    public static void main() {
        String string = "TestRene";
        long hash = getSubstringHash(string, 3, 6);
        System.out.println("Hash: " + hash);
    }
}
