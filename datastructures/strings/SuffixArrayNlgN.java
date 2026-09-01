package datastructures.strings;

// Builds a suffix array in O(N lg N)
// Based on https://github.com/stevenhalim/cpbook-code/blob/master/ch6/sa_lcp.java
public class SuffixArrayNlgN {
    public int[] suffixArray;
    public int[] lcp;  // lcp[i] stores the LCP between previous suffix "T + SA[i-1]" and current suffix "T + SA[i]"

    private char[] string;
    private int stringLength;

    public SuffixArrayNlgN(String string) {
        this.string = string.toCharArray();
        stringLength = this.string.length;
        constructSuffixArray(); // O(N lg N)
        computeLcp();           // O(N)
    }

    private void countingSort(int[] ranks, int k) {
        int sum = 0;
        int[] tempSuffixArray = new int[stringLength];
        int maxi = Math.max(300, stringLength);                // up to 255 ASCII chars or length of n
        // for counting/radix sort
        int[] count = new int[maxi];
        for (int i = 0; i < stringLength; i++) {               // count the frequency of each rank
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

    private void constructSuffixArray() {            // this version can go up to 100000 characters
        suffixArray = new int[stringLength];
        int[] ranks = new int[stringLength];
        int[] tempRanks = new int[stringLength];

        for (int i = 0; i < stringLength; i++) {     // initial rankings
            ranks[i] = string[i];
        }
        for (int i = 0; i < stringLength; i++) {     // initial SA: { 0, 1, 2, ..., n-1 }
            suffixArray[i] = i;
        }
        for (int k = 1; k < stringLength; k <<= 1) {     // repeat sorting process log n times
            countingSort(ranks, k);                      // actually radix sort: sort based on the second item
            countingSort(ranks, 0);                   // then (stable) sort based on the first item

            int rank = 0;
            for (int i = 1; i < stringLength; i++) {     // compare adjacent suffixes
                tempRanks[suffixArray[i]] =       // if same pair => same rank; otherwise, increase rank
                        (ranks[suffixArray[i]] == ranks[suffixArray[i - 1]] && ranks[suffixArray[i] + k] == ranks[suffixArray[i - 1] + k]) ? rank : ++rank;
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
        phi[suffixArray[0]] = -1;                      // default value
        for (int i = 1; i < stringLength; i++) {
            phi[suffixArray[i]] = suffixArray[i - 1];  // remember which suffix is previous to this suffix
        }
        for (int i = 0; i < stringLength; i++) {       // compute permuted lcp in O(n)
            if (phi[i] == -1) {                        // special case
                plcp[i] = 0;
                continue;
            }
            while (i + length < string.length
                    && phi[i] + length < string.length
                    && string[i + length] == string[phi[i] + length]) {
                length++;                               // length will be increased at maximum n times
            }
            plcp[i] = length;
            length = Math.max(length - 1, 0);           // length will be decreased at maximum n times
        }
        for (int i = 1; i < stringLength; i++) {
            lcp[i] = plcp[suffixArray[i]];              // put the permuted LCP back to the correct position
        }
    }

    public static void main(String[] args){
        String string = "COMPETITIVE$";
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(string);

        System.out.printf("The Suffix Array of string T = '%s' is shown below (O(n log n) version):\n", string);
        System.out.println("i\tSA[i]\tSuffix\n");
        for (int i = 0; i < suffixArray.stringLength; i++) {
            System.out.printf("%2d\t%5d\t%s\n", i, suffixArray.suffixArray[i],
                    new String(suffixArray.string, suffixArray.suffixArray[i],
                            suffixArray.string.length - suffixArray.suffixArray[i]));
        }
    }
}
