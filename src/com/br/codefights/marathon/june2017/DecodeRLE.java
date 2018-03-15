package com.br.codefights.marathon.june2017;

/**
 * Created by rene on 24/06/17.
 */

/**
 * You like learning about different encoding algorithms.
 * Yesterday you read about run-length encoding (RLE), which works as follows:
 * Run the string data and change all substrings of consecutive equal characters to this character and its number of occurrences.
 * For example, "aabccc" will be changed to "a2b1c3" and "aaaaabcabb" to "a5b1c1a1b2".
 * But just encoding strings is too boring for you! So you decided to decode strings that were encoded using RLE.
 * Given a string code, which is the result of RLE, determine the length of the initial string.

 Example

 For code = "a2b1c3", the output should be
 decodeRLE(code) = 6;

 For code = "a5b1c1a1b2", the output should be
 decodeRLE(code) = 10.

 Input/Output
 [time limit] 3000ms (java)

 [input] string code
 A string, containing lowercase English letters and digits.
 It is guaranteed that this string was obtained using RLE for string, consisting only from small English letters.

 Guaranteed constraints:
 2 ≤ code.length ≤ 105.

 [output] integer
 The length of the string that was initially encoded. It is guaranteed that the answer fits in a 32-bit type.
 */
public class DecodeRLE {

    public static void main(String[] args) {
        System.out.println(decodeRLE("a2b1c3") + " Expected: 6");
        System.out.println(decodeRLE("a5b1c1a1b2") + " Expected: 10");
        System.out.println(decodeRLE("a1") + " Expected: 1");
        System.out.println(decodeRLE("z9") + " Expected: 9");
        System.out.println(decodeRLE("a239") + " Expected: 239");
        System.out.println(decodeRLE("a23b19z18d108j1208z28a19d48") + " Expected: 1471");
        System.out.println(decodeRLE("h484958049") + " Expected: 484958049");
    }

    private static int decodeRLE(String code) {
        int count = 0;

        for(int i = 0; i < code.length(); i++) {
            String currentNumber = "";

            while(code.charAt(i) >= '0' && code.charAt(i) <= '9') {
                currentNumber += code.charAt(i);
                i++;

                if (i == code.length()) {
                    break;
                }
            }

            if (!currentNumber.equals("")) {
                int currentValue = Integer.parseInt(currentNumber);
                count += currentValue;
            }
        }

        return count;
    }

}
