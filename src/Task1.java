import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Task1 {
    static int n;
    static StringBuilder current = new StringBuilder();
    static StringBuilder output = new StringBuilder();

    static void generate(int open, int close) {
        if (current.length() == 2 * n) {
            output.append(current).append('\n');
            return;
        }
        if (open < n) {
            current.append('(');
            generate(open + 1, close);
            current.deleteCharAt(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            generate(open, close + 1);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        generate(0, 0);
        System.out.print(output);
    }
}
