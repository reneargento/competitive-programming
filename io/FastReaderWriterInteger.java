package io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class FastReaderWriterInteger {
    private static final class FastInputStream {
        private static final int BUF_SIZE = 1 << 16;
        private final InputStream in;
        private final byte[] buf = new byte[BUF_SIZE];
        private int pos;
        private int count;

        FastInputStream(InputStream in) {
            this.in = in;
        }

        private void readBuf() throws IOException {
            pos = 0;
            count = in.read(buf);
        }

        private void skipUnprintable() throws IOException {
            while (true) {
                while (pos < count && buf[pos] <= ' ') {
                    pos++;
                }
                if (pos < count) {
                    return;
                }

                readBuf();
                if (count <= 0) {
                    throw new NoSuchElementException();
                }
            }
        }

        int nextInt() throws IOException {
            skipUnprintable();

            int sign = 1;
            if (buf[pos] == '-') {
                sign = -1;
                pos++;
            }

            int value = 0;
            while (true) {
                while (pos < count) {
                    byte c = buf[pos];

                    if (c < '0' || c > '9') {
                        return value * sign;
                    }
                    value = value * 10 + c - '0';
                    pos++;
                }

                readBuf();
                if (count <= 0) {
                    return value * sign;
                }
            }
        }
    }

    private static final class FastWriter {
        private static final int BUFFER_SIZE = 1 << 15;
        private static final DataOutputStream out = new DataOutputStream(System.out);
        private static final byte[] buffer = new byte[BUFFER_SIZE];
        private static final byte[] number = new byte[11];
        private static int ptr;

        static void print(int value) throws IOException {
            if (value == 0) {
                print('0');
                return;
            }
            if (value < 0) {
                print('-');
                value = -value;
            }

            int size = 0;
            while (value > 0) {
                number[size++] = (byte) ('0' + value % 10);
                value /= 10;
            }
            while (size > 0) {
                print((char) number[--size]);
            }
        }

        static void print(char value) throws IOException {
            if (ptr == BUFFER_SIZE) {
                writeBuffer();
            }
            buffer[ptr++] = (byte) value;
        }

        static void println(int value) throws IOException {
            print(value);
            print('\n');
        }

        static void println() throws IOException {
            print('\n');
        }

        static void flush() throws IOException {
            writeBuffer();
            out.flush();
        }

        private static void writeBuffer() throws IOException {
            if (ptr > 0) {
                out.write(buffer, 0, ptr);
                ptr = 0;
            }
        }
    }
}
