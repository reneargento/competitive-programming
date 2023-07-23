package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rene Argento on 30/05/17.
 */
public class FastReaderInteger {
    private static final InputStream in = System.in;
    private static final int bufferSize = 30000;
    private static final byte[] buffer = new byte[bufferSize];
    private static int position = 0;
    private static int byteCount = bufferSize;
    private static byte character;

    FastReaderInteger() throws IOException {
        fill();
    }

    private void fill() throws IOException {
        byteCount = in.read(buffer, 0, bufferSize);
    }

    private int nextInt() throws IOException {
        while (character < '-') {
            character = readByte();
        }
        boolean isNegative = (character == '-');
        if (isNegative) {
            character = readByte();
        }
        int value = character - '0';
        while ((character = readByte()) >= '0' && character <= '9') {
            value = value * 10 + character - '0';
        }
        return isNegative ? -value : value;
    }

    private byte readByte() throws IOException {
        if (position == byteCount) {
            fill();
            position = 0;
        }
        return buffer[position++];
    }
}