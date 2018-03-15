package com.br.codefights.marathon.may2017;

/**
 * Created by rene on 27/05/17.
 */

/**
 * Alex often finds words that have letters both in upper and lower case, and they make him feel terrible.
 * He's so tired of this that he decides to convert all these words to one letter case.

 To choose the case, he calculates the number of lower case letters and the number of upper case letters.
 If these numbers have the same parity, Alex converts the word to lower case.
 Otherwise, he converts the word to upper case.
 Note that Alex doesn't convert words that only have letters that are one case.

 Given the word that Alex wants to convert, return the modified word.

 Example

 For word = "KeY", the output should be
 convertToOneCase(word) = "KEY".

 This word contains 2 upper case letters and 1 lower case letter.
 2 and 1 have the opposite parity, so this word should be converted to upper case.

 For word = "FOObar", the output should be
 convertToOneCase(word) = "foobar".

 This word contains 3 letters in both upper and lower case.
 3 and 3 have the same parity, so this word should be converted to lower case.

 For word = "chamomile", the output should be
 convertToOneCase(word) = "chamomile".

 The letters in this word are only in one case, so Alex doesn't convert it.

 Input/Output

 [time limit] 3000ms (java)
 [input] string word

 The word that Alex wants to convert. It contains only English letters.

 Guaranteed constraints:
 1 ≤ word.length ≤ 1000.

 [output] string
 The modified word.
 */
public class ConvertToOneCase {

    public static void main(String[] args) {
        System.out.println(convertToOneCase("KeY") + " Exptected: KEY");
        System.out.println(convertToOneCase("FOObar") + " Exptected: foobar");
        System.out.println(convertToOneCase("chamomile") + " Exptected: chamomile");
        System.out.println(convertToOneCase("DEVelopment") + " Exptected: DEVELOPMENT");
        System.out.println(convertToOneCase("qUeRimONy") + " Exptected: QUERIMONY");
        System.out.println(convertToOneCase("TiNNiEnT") + " Exptected: tinnient");
        System.out.println(convertToOneCase("OX") + " Exptected: OX");
    }

    private static String convertToOneCase(String word) {
        int countLower = 0;
        int countUpper = 0;

        for(int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == Character.toUpperCase(word.charAt(i))) {
                countUpper++;
            } else {
                countLower++;
            }
        }

        String newString;

        if (countLower == 0 || countUpper == 0) {
            return word;
        }

        if (countLower % 2 != countUpper % 2) {
            newString = word.toUpperCase();
        } else {
            newString = word.toLowerCase();
        }

        return newString;
    }


}
