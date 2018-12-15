package algorithms.strings;

/**
 * Created by rene on 27/01/18.
 */
// Based on https://www.geeksforgeeks.org/find-given-string-can-represented-substring-iterating-substring-n-times/
public class MinimalStringPeriod {

    // Check if a string is 'n' times repetition of one of its substrings

    // Utility function to fill lps[] or compute prefix function used in KMP string matching algorithm.
    private static void computeLPSArray(String str, int M, int lps[]) {
        // lenght of the previous
        // longest prefix suffix
        int len = 0;

        int i;

        lps[0] = 0; // lps[0] is always 0
        i = 1;

        // the loop calculates lps[i]
        // for i = 1 to M-1
        while (i < M) {
            if (str.charAt(i) == str.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {// (pat[i] != pat[len])
                if (len != 0) {
                    // This is tricky. Consider the
                    // example AAACAAAA and i = 7.
                    len = lps[len - 1];

                    // Also, note that we do not increment i here
                } else { // if (len == 0)
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    // Returns true if string is repetition of one of its substrings else return false.
    private static boolean isRepeat(String string) {
        // Find length of string and create
        // an array to store lps values used in KMP
        int n = string.length();
        int lps[] = new int[n];

        // Preprocess the pattern (calculate lps[] array)
        computeLPSArray(string, n, lps);

        // Find length of longest suffix
        // which is also prefix of string.
        int len = lps[n - 1];

        // If there exist a suffix which is also
        // prefix AND Length of the remaining substring
        // divides total length, then string[0..n-len-1]
        // is the substring that repeats n/(n-len)
        // times (Readers can print substring and
        // value of n/(n-len) for more clarity.
        if (len > 0) {
            System.out.println(string.substring(0, n - len));
        } else {
            System.out.println(string);
        }

        return (len > 0 && n % (n - len) == 0);
    }

    // Driver program to test above function
    public static void main(String[] args) {
        String txt[] = {"ABCABC", "ABABAB", "ABCDABCD",
                "GEEKSFORGEEKS", "GEEKGEEK",
                "AAAACAAAAC", "ABCDABC", "abcab", "aaaaaa"};

        for (int i = 0; i < txt.length; i++) {
            System.out.println(isRepeat(txt[i]));
        }
    }


}
