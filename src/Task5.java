import java.io.*;

public class Task5 {
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
            child = nodes++; edgeChar[child] = c;
            nextSibling[child] = firstChild[parent]; firstChild[parent] = child;
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
        String kth(String prefix, int k) {
            int node = 0;
            for (int i = 0; i < prefix.length(); i++) { node = findChild(node, prefix.charAt(i)); if (node == 0) return null; }
            if (k > subtreeCount[node]) return null;
            StringBuilder answer = new StringBuilder(prefix);
            while (true) {
                if (terminal[node]) { if (k == 1) return answer.toString(); k--; }
                boolean moved = false;
                for (char c = 'a'; c <= 'z'; c++) {
                    int child = findChild(node, c);
                    if (child == 0 || subtreeCount[child] == 0) continue;
                    if (k > subtreeCount[child]) k -= subtreeCount[child];
                    else { answer.append(c); node = child; moved = true; break; }
                }
                if (!moved) return null;
            }
        }
    }
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(); int q = fs.nextInt();
        Trie trie = new Trie(1_000_005); StringBuilder out = new StringBuilder();
        for (int i = 0; i < q; i++) {
            String op = fs.next();
            if (op.equals("ADD")) trie.add(fs.next());
            else if (op.equals("REMOVE")) trie.remove(fs.next());
            else {
                String prefix = fs.next(); int k = fs.nextInt();
                String answer = trie.kth(prefix, k);
                out.append(answer == null ? "NONE" : answer).append('\n');
            }
        }
        System.out.print(out);
    }
}
