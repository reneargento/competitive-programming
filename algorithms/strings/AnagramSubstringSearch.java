package algorithms.strings;

/**
 * Created by Rene Argento on 10/03/18.
 */
// Given a text string of length N and a pattern string of length M, determine whether the pattern or
// any of its anagrams (any of its M! permutations) appears in the text.
public class AnagramSubstringSearch {

    // Runs in O(N * M)
    public static boolean isAnagramInText(String text, String pattern) {

        if (pattern.length() > text.length()) {
            return false;
        }

        int alphabetSize = 256;
        int patternLength = pattern.length();

        int[] patternHistogram = new int[alphabetSize];
        int[] textHistogram = new int[alphabetSize];

        for (int i = 0; i < pattern.length(); i++) {
            patternHistogram[pattern.charAt(i)]++;
            textHistogram[text.charAt(i)]++;
        }

        for (int i = patternLength; i < text.length(); i++) {
            if (i > 0) {
                textHistogram[text.charAt(i - patternLength)]--;
                textHistogram[text.charAt(i)]++;
            }

            if (compareHistograms(patternHistogram, textHistogram, pattern)) {
                return true;
            }
        }

        return false;
    }

    private static boolean compareHistograms(int[] patternHistogram, int[] textHistogram, String pattern) {
        boolean isMatch = true;

        for (int i = 0; i < pattern.length(); i++) {
            char currentChar = pattern.charAt(i);

            if (textHistogram[currentChar] != patternHistogram[currentChar]) {
                isMatch = false;
                break;
            }
        }

        return isMatch;
    }

    public static void main(String[] args) {
        String pattern = "RENE";

        String text1 = "ABCENRENEC";
        boolean result1 = AnagramSubstringSearch.isAnagramInText(text1, pattern);
        System.out.println("Result 1: " + result1 + " Expected: true");

        String text2 = "ABCENERANC";
        boolean result2 = AnagramSubstringSearch.isAnagramInText(text2, pattern);
        System.out.println("Result 2: " + result2 + " Expected: true");

        String text3 = "ABCENEEERNANC";
        boolean result3 = AnagramSubstringSearch.isAnagramInText(text3, pattern);
        System.out.println("Result 3: " + result3 + " Expected: true");

        String text4 = "ABCENNRANC";
        boolean result4 = AnagramSubstringSearch.isAnagramInText(text4, pattern);
        System.out.println("Result 4: " + result4 + " Expected: false");

        String text5 = "REN";
        boolean result5 = AnagramSubstringSearch.isAnagramInText(text5, pattern);
        System.out.println("Result 5: " + result5 + " Expected: false");
    }

}
