package datastructures.strings;

// Builds a circular suffix array in O(N lg N)
public class CircularSuffixArray {
    public int[] suffixArray;
    public int[] lcp;

    private final char[] string;
    private final int stringLength;

    public CircularSuffixArray(String string) {
        this.string = string.toCharArray();
        stringLength = this.string.length;
        constructSuffixArray();
        computeLcp();
    }

    private void countingSort(int[] ranks, int k) {
        int sum = 0;
        int[] tempSuffixArray = new int[stringLength];
        int maxi = Math.max(300, stringLength);
        int[] count = new int[maxi];
        for (int i = 0; i < stringLength; i++) {
            count[ranks[(i + k) % stringLength]]++;
        }
        for (int i = 0; i < maxi; i++) {
            int aux = count[i];
            count[i] = sum;
            sum += aux;
        }
        for (int i = 0; i < stringLength; i++) {
            tempSuffixArray[count[ranks[(suffixArray[i] + k) % stringLength]]++] = suffixArray[i];
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
            for (int i = 1; i < stringLength; i++) {
                tempRanks[suffixArray[i]] =
                            (ranks[suffixArray[i]] == ranks[suffixArray[i - 1]]
                                    && ranks[(suffixArray[i] + k) % stringLength]
                                        == ranks[(suffixArray[i - 1] + k) % stringLength]) ? rank : ++rank;
            }
            for (int i = 0; i < stringLength; i++) {
                ranks[i] = tempRanks[i];
            }
        }
    }

    private void computeLcp() {
        int length = 0;
        lcp = new int[stringLength];
        int[] plcp = new int[stringLength];
        int[] phi = new int[stringLength];
        phi[suffixArray[0]] = -1;
        for (int i = 1; i < stringLength; i++) {
            phi[suffixArray[i]] = suffixArray[i - 1];
        }
        for (int i = 0; i < stringLength; i++) {
            if (phi[i] == -1) {
                plcp[i] = 0;
                continue;
            }
            while (i + length < string.length
                    && phi[i] + length < string.length
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

    public static void main(String[] args){
        String string = "AAABAA";
        CircularSuffixArray suffixArray = new CircularSuffixArray(string);

        System.out.println("Circular Suffix Array:");
        System.out.println("i\tSA[i]\tSuffix");
        for (int i = 0; i < suffixArray.stringLength; i++) {
            String part1 = new String(suffixArray.string, suffixArray.suffixArray[i],
                    suffixArray.string.length - suffixArray.suffixArray[i]);
            String part2 = new String(suffixArray.string, 0, suffixArray.suffixArray[i]);
            System.out.printf("%2d\t%5d\t%s\n", i, suffixArray.suffixArray[i], part1 + part2);
        }
    }
}
