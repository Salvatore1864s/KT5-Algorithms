import java.io.*;

public class Task2 {
    static final class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr, len;
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buffer); ptr = 0; if (len <= 0) return -1; }
            return buffer[ptr++];
        }
        String next() throws IOException {
            StringBuilder sb = new StringBuilder(); int c;
            do { c = read(); } while (c <= ' ' && c != -1);
            while (c > ' ') { sb.append((char)c); c = read(); }
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }

    static final class Trie {
        private final int[] firstChild, nextSibling, subtreeCount;
        private final char[] edgeChar;
        private final boolean[] terminal;
        private int nodes = 1;
        Trie(int capacity) {
            firstChild = new int[capacity]; nextSibling = new int[capacity];
            subtreeCount = new int[capacity]; edgeChar = new char[capacity]; terminal = new boolean[capacity];
        }
        private int findChild(int parent, char c) {
            for (int child = firstChild[parent]; child != 0; child = nextSibling[child])
                if (edgeChar[child] == c) return child;
            return 0;
        }
        private int getOrCreateChild(int parent, char c) {
            int child = findChild(parent, c);
            if (child != 0) return child;
            child = nodes++;
            edgeChar[child] = c; nextSibling[child] = firstChild[parent]; firstChild[parent] = child;
            return child;
        }
        void add(String word) {
            int[] path = new int[word.length() + 1]; int node = 0; path[0] = 0;
            for (int i = 0; i < word.length(); i++) { node = getOrCreateChild(node, word.charAt(i)); path[i + 1] = node; }
            terminal[node] = true;
            for (int id : path) subtreeCount[id]++;
        }
        void remove(String word) {
            int[] path = new int[word.length() + 1]; int node = 0; path[0] = 0;
            for (int i = 0; i < word.length(); i++) { node = findChild(node, word.charAt(i)); path[i + 1] = node; }
            terminal[node] = false;
            for (int id : path) subtreeCount[id]--;
        }
        int countPrefix(String prefix) {
            int node = 0;
            for (int i = 0; i < prefix.length(); i++) { node = findChild(node, prefix.charAt(i)); if (node == 0) return 0; }
            return subtreeCount[node];
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(); int q = fs.nextInt();
        Trie trie = new Trie(1_000_005); StringBuilder out = new StringBuilder();
        for (int i = 0; i < q; i++) {
            String op = fs.next(); String word = fs.next();
            if (op.equals("ADD")) trie.add(word);
            else if (op.equals("REMOVE")) trie.remove(word);
            else out.append(trie.countPrefix(word)).append('\n');
        }
        System.out.print(out);
    }
}
