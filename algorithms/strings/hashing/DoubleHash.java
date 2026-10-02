package algorithms.strings.hashing;

public class DoubleHash {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE = 911382323L;

    private final long[] hash1;
    private final long[] hash2;
    private final long[] power1;
    private final long[] power2;

    DoubleHash(String string) {
        int n = string.length();
        hash1 = new long[n + 1];
        hash2 = new long[n + 1];
        power1 = new long[n + 1];
        power2 = new long[n + 1];
        power1[0] = 1;
        power2[0] = 1;

        for (int i = 0; i < n; i++) {
            int value = string.charAt(i);
            hash1[i + 1] = (hash1[i] * BASE + value) % MOD1;
            hash2[i + 1] = (hash2[i] * BASE + value) % MOD2;
            power1[i + 1] = (power1[i] * BASE) % MOD1;
            power2[i + 1] = (power2[i] * BASE) % MOD2;
        }
    }

    // Hash of s[l..r), normalized so equal strings have equal hashes.
    private long getHash1(int l, int r) {
        long result = hash1[r] - hash1[l] * power1[r - l] % MOD1;
        if (result < 0) {
            result += MOD1;
        }
        return result;
    }

    private long getHash2(int l, int r) {
        long result = hash2[r] - hash2[l] * power2[r - l] % MOD2;
        if (result < 0) {
            result += MOD2;
        }
        return result;
    }

    private boolean equals(DoubleHash other, int l1, int r1, int l2, int r2) {
        return r1 - l1 == r2 - l2
                && getHash1(l1, r1) == other.getHash1(l2, r2)
                && getHash2(l1, r1) == other.getHash2(l2, r2);
    }

    public static void main() {
        DoubleHash doubleHash1 = new DoubleHash("testRenestring");
        DoubleHash doubleHash2 = new DoubleHash("this is a test of Renethe double hash algorithm");

        boolean isEqual1 = doubleHash1.equals(doubleHash2, 4, 7, 7, 11);
        System.out.println("Is equal: " + isEqual1);
        System.out.println("Expected: false");

        boolean isEqual2 = doubleHash1.equals(doubleHash2, 4, 7, 18, 21);
        System.out.println("\nIs equal: " + isEqual2);
        System.out.println("Expected: true");
    }
}
