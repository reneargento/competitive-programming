package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by rene.argento on 30/05/17.
 */
// Based on https://www.cpe.ku.ac.th/~jim/java-io.html
public class FastReader {
    private static BufferedReader reader;
    private static StringTokenizer tokenizer;

    static void init(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
    }

    private static String next() throws IOException {
        while (!tokenizer.hasMoreTokens() ) {
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }

    private static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    private static double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    private static long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    // Used to check EOF
    // If getLine() == null, it is a EOF
    // Otherwise, it returns the next line
    private static String getLine() throws IOException {
        return reader.readLine();
    }
}