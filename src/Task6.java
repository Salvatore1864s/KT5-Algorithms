import java.io.*;

public class Task6 {
    static final class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr, len;
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
    static final class BinaryTrie {
        private final int[] zero, one;
        private int nodes = 1;
        BinaryTrie(int maxValues) {
            int capacity = 1 + maxValues * 30;
            zero = new int[capacity]; one = new int[capacity];
        }
        void insert(int value) {
            int node = 0;
            for (int bit = 29; bit >= 0; bit--) {
                int current = (value >>> bit) & 1;
                if (current == 0) { if (zero[node] == 0) zero[node] = nodes++; node = zero[node]; }
                else { if (one[node] == 0) one[node] = nodes++; node = one[node]; }
            }
        }
        int maxXor(int value) {
            int node = 0, result = 0;
            for (int bit = 29; bit >= 0; bit--) {
                int current = (value >>> bit) & 1;
                if (current == 0 && one[node] != 0) { result |= 1 << bit; node = one[node]; }
                else if (current == 1 && zero[node] != 0) { result |= 1 << bit; node = zero[node]; }
                else node = current == 0 ? zero[node] : one[node];
            }
            return result;
        }
    }
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(); int n = fs.nextInt();
        BinaryTrie trie = new BinaryTrie(n + 1); trie.insert(0);
        int prefixXor = 0, answer = 0;
        for (int i = 0; i < n; i++) {
            prefixXor ^= fs.nextInt();
            answer = Math.max(answer, trie.maxXor(prefixXor));
            trie.insert(prefixXor);
        }
        System.out.println(answer);
    }
}
