package algorithms.strings.suffix.array;

import java.io.IOException;
import java.util.*;

/**
 * Created by Rene Argento on 10/09/26.
 */
// Computes the longest common substring between k or more of N strings
// Uses an adapted Suffix Array for the computation
// Time complexity: O(N lg N)
public class LongestCommonSubstringNStrings {

    public static void main(String[] args) throws IOException {
        String[] strings = { "abcdefg", "bcdefgh", "cdefghi" };
        List<String> lcs = computeLongestCommonSubstring(strings, 2);
        System.out.println("LCS:");
        for (String string : lcs) {
            System.out.println(string);
        }
        System.out.println("\nExpected:\nbcdefg\ncdefgh");
    }

    private static class SuffixData {
        int index;
        int length;

        public SuffixData(int index, int length) {
            this.index = index;
            this.length = length;
        }
    }

    // Must be lower than all characters in the strings
    private static final char SEPARATOR = '$';

    private static List<String> computeLongestCommonSubstring(String[] strings, int numberOfStrings) {
        String allStrings = concatenateStrings(strings);
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(allStrings);
        int[] owners = computeOwners(suffixArray, allStrings, strings);
        return computeLongestCommonSubstring(strings, suffixArray, owners, numberOfStrings);
    }

    private static String concatenateStrings(String[] strings) {
        StringBuilder allStrings = new StringBuilder();
        for (String string : strings) {
            allStrings.append(string).append(SEPARATOR);
        }
        return allStrings.toString();
    }

    private static int[] computeOwners(SuffixArrayNlgN suffixArray, String allStrings, String[] strings) {
        int[] ownerAtIndex = new int[allStrings.length()];
        int index = 0;
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                ownerAtIndex[index] = i;
                index++;
            }
            ownerAtIndex[index] = -1;
            index++;
        }

        int[] owners = new int[strings.length];
        for (int i = 0; i < owners.length; i++) {
            owners[i] = ownerAtIndex[suffixArray.suffixArray[i]];
        }
        return owners;
    }

    private static List<String> computeLongestCommonSubstring(String[] strings, SuffixArrayNlgN suffixArray,
                                                              int[] owners, int numberOfStrings) {
        List<SuffixData> suffixDataList = new ArrayList<>();
        int bestLength = 0;

        Deque<Integer> minDeque = new ArrayDeque<>();
        int[] ownersCount = new int[strings.length];
        int distinctOwners = 0;
        int left = 0;

        for (int right = 0; right < suffixArray.lcp.length; right++) {
            int ownerId = owners[right];
            if (ownerId >= 0) {
                if (ownersCount[ownerId] == 0) {
                    distinctOwners++;
                }
                ownersCount[ownerId]++;
            }

            if (right > 0) {
                int currentLcp = suffixArray.lcp[right];
                while (!minDeque.isEmpty()
                        && suffixArray.lcp[minDeque.peekLast()] >= currentLcp) {
                    minDeque.pollLast();
                }
                minDeque.offerLast(right);
            }

            while (distinctOwners >= numberOfStrings) {
                int lcp = minDeque.isEmpty() ? 0 : suffixArray.lcp[minDeque.peekFirst()];
                if (lcp > bestLength) {
                    bestLength = lcp;
                    suffixDataList = new ArrayList<>();
                    suffixDataList.add(new SuffixData(suffixArray.suffixArray[right], lcp));
                } else if (lcp == bestLength) {
                    suffixDataList.add(new SuffixData(suffixArray.suffixArray[right], lcp));
                }

                int ownerLeft = owners[left];
                if (ownerLeft >= 0) {
                    ownersCount[ownerLeft]--;
                    if (ownersCount[ownerLeft] == 0) {
                        distinctOwners--;
                    }
                }

                int leftLcpIndex = left + 1;
                if (!minDeque.isEmpty()
                        && minDeque.peekFirst() == leftLcpIndex) {
                    minDeque.pollFirst();
                }
                left++;
            }
        }

        if (bestLength == 0) {
            return new ArrayList<>();
        }

        Set<String> lcsSet = new HashSet<>();
        for (SuffixData suffixData : suffixDataList) {
            lcsSet.add(new String(suffixArray.string, suffixData.index, suffixData.length));
        }
        List<String> lcsList = new ArrayList<>(lcsSet);
        Collections.sort(lcsList);
        return lcsList;
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;

        private final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
            this.string = string.toCharArray();
            stringLength = this.string.length;
            constructSuffixArray();
            computeLcp();
        }

        private void countingSort(int[] ranks, int k) {
            int sum = 0;
            int[] tempSuffixArray = new int[stringLength];
            int maxi = Math.max(301, stringLength + 1);
            int[] count = new int[maxi];
            for (int i = 0; i < stringLength; i++) {
                count[i + k < stringLength ? ranks[i + k] : 0]++;
            }
            for (int i = 0; i < maxi; i++) {
                int aux = count[i];
                count[i] = sum;
                sum += aux;
            }
            for (int i = 0; i < stringLength; i++) {
                tempSuffixArray[count[suffixArray[i] + k < stringLength ? ranks[suffixArray[i] + k] : 0]++] = suffixArray[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = tempSuffixArray[i];
            }
        }

        private void constructSuffixArray() {
            suffixArray = new int[stringLength];
            int[] ranks = new int[stringLength];
            int[] tempRanks = new int[stringLength];

            for (int i = 0; i < stringLength; i++) {
                ranks[i] = string[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = i;
            }
            for (int k = 1; k < stringLength; k <<= 1) {
                countingSort(ranks, k);
                countingSort(ranks, 0);

                int rank = 0;
                tempRanks[suffixArray[0]] = 1;
                for (int i = 1; i < stringLength; i++) {
                    boolean areTheSame =
                            ranks[suffixArray[i]] == ranks[suffixArray[i - 1]]
                                    && (suffixArray[i] + k < stringLength ? ranks[suffixArray[i] + k] : 0)
                                        == (suffixArray[i - 1] + k < stringLength ? ranks[suffixArray[i - 1] + k] : 0);
                    if (!areTheSame) {
                        rank++;
                    }
                    tempRanks[suffixArray[i]] = rank + 1;
                }
                for (int i = 0; i < stringLength; i++) {
                    ranks[i] = tempRanks[i];
                }
                if (rank == stringLength - 1) {
                    break;
                }
            }
        }

        private void computeLcp() {
            int length = 0;
            lcp = new int[stringLength];
            int[] plcp = new int[stringLength];
            int[] phi = new int[stringLength];
            phi[suffixArray[0]] = -1;
            int[] remainingCharacters = computeRemainingCharactersBeforeSeparator();

            for (int i = 1; i < stringLength; i++) {
                phi[suffixArray[i]] = suffixArray[i - 1];
            }
            for (int i = 0; i < stringLength; i++) {
                if (phi[i] == -1) {
                    plcp[i] = 0;
                    continue;
                }
                int maxLength = Math.min(remainingCharacters[i], remainingCharacters[phi[i]]);
                while (length < maxLength
                        && string[i + length] == string[phi[i] + length]) {
                    length++;
                }
                plcp[i] = length;
                length = Math.max(length - 1, 0);
            }
            for (int i = 1; i < stringLength; i++) {
                lcp[i] = plcp[suffixArray[i]];
            }
        }

        private int[] computeRemainingCharactersBeforeSeparator() {
            int[] remainingCharacters = new int[string.length];
            int characters = 0;
            for (int i = string.length - 1; i >= 0; i--) {
                if (string[i] == SEPARATOR) {
                    characters = 0;
                } else {
                    characters++;
                }
                remainingCharacters[i] = characters;
            }
            return remainingCharacters;
        }
    }
}
