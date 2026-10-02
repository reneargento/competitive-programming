package algorithms.strings.hashing;

/**
 * Created by Rene Argento on 20/09/26.
 */
// Computes the longest palindromic suffix using string hashing.
// Time complexity: O(N)
public class LongestPalindromicSuffix {

    private static final long PRIME_NUMBER = 1000000007L;
    private static final int ALPHABET_SIZE = 256;

    private static String computeLongestPalindromicSuffix(String string) {
        long[] prefixHashString = computePrefixHash(string);
        long[] prefixHashReverseString = computePrefixHash(new StringBuilder(string).reverse().toString());
        long[] powers = computePowers(string.length() + 1);

        for (int length = string.length(); length >= 1; length--) {
            int hash1StartIndex = string.length() - length;
            long hash1 = getHash(prefixHashString, powers, hash1StartIndex, string.length());
            long hash2 = prefixHashReverseString[length];

            if (hash1 == hash2) {
                int palindromeStartIndex = string.length() - length;
                return string.substring(palindromeStartIndex);
            }
        }
        return "";
    }

    private static long[] computePrefixHash(String string) {
        long[] prefixHash = new long[string.length() + 1];
        for (int i = 0; i < string.length(); i++) {
            prefixHash[i + 1] = (prefixHash[i] * ALPHABET_SIZE + string.charAt(i)) % PRIME_NUMBER;
        }
        return prefixHash;
    }

    private static long[] computePowers(int length) {
        long[] powers = new long[length];
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
        String string = "amanaplanacanal";
        String longestPalindromicSuffix = computeLongestPalindromicSuffix(string);
        System.out.printf("%-9s %s\n", "LPS:", longestPalindromicSuffix);
        System.out.println("Expected: lanacanal");
    }
}
