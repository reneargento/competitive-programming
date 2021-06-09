package io;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class RemoveNonAlphanumericCharacters {

    public static void main(String[] args) {
        String string = "This is an \"original\" string with non alphanumeric characters, 31numbers and punctuation.";
        String convertedString = removeNonAlphanumericCharactersExceptSpaces(string);
        System.out.println(convertedString);
    }

    private static String removeNonAlphanumericCharactersExceptSpaces(String string) {
        return string.replaceAll("[^a-zA-Z ]", "");
    }
}
