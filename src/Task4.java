import java.io.*;

public class Task4 {
    static int n, allColumns;
    static int[] allowedColumns;
    static long solutions;

    static void search(int row, int columns, int diagonalLeft, int diagonalRight) {
        if (row == n) { solutions++; return; }
        int available = allowedColumns[row] & allColumns & ~(columns | diagonalLeft | diagonalRight);
        while (available != 0) {
            int queen = available & -available;
            available -= queen;
            search(row + 1, columns | queen,
                    ((diagonalLeft | queen) << 1) & allColumns,
                    (diagonalRight | queen) >>> 1);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] first = br.readLine().trim().split("\\s+");
        n = Integer.parseInt(first[0]); int b = Integer.parseInt(first[1]);
        allColumns = (1 << n) - 1; allowedColumns = new int[n];
        for (int r = 0; r < n; r++) allowedColumns[r] = allColumns;
        for (int i = 0; i < b; i++) {
            String[] cell = br.readLine().trim().split("\\s+");
            int row = Integer.parseInt(cell[0]) - 1, col = Integer.parseInt(cell[1]) - 1;
            allowedColumns[row] &= ~(1 << col);
        }
        search(0, 0, 0, 0);
        System.out.println(solutions);
    }
}
