import java.io.*;

public class Task3 {
    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr, len;
        FastScanner(InputStream in) { this.in = in; }
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buffer); ptr = 0; if (len <= 0) return -1; }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c; do { c = read(); } while (c <= ' ' && c != -1);
            int value = 0; while (c > ' ') { value = value * 10 + c - '0'; c = read(); }
            return value;
        }
    }
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(new BufferedInputStream(System.in));
        int n = fs.nextInt(), xor = 0; int[] values = new int[n];
        for (int i = 0; i < n; i++) { values[i] = fs.nextInt(); xor ^= values[i]; }
        int bit = xor & -xor, first = 0;
        for (int value : values) if ((value & bit) != 0) first ^= value;
        int second = xor ^ first;
        if (first > second) { int temp = first; first = second; second = temp; }
        System.out.println(first + " " + second);
    }
}
