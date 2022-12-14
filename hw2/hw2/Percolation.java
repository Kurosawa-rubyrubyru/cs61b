package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private int sumopen;

    private boolean[][] openclosesystem;

    private int N;

    private WeightedQuickUnionUF union;

    private WeightedQuickUnionUF unionbottom;

//    private int[] unionToArray(int id) {
//        int[] ans = new int[2];
//        ans[0] = (id - 1) / N;
//        ans[1] = (id - 1) % N;
//
//    }

    private int arrayToUnion(int row, int col) {
        return row * N + col + 1;
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N<=0");
        }
        openclosesystem = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                openclosesystem[i][j] = false;
            }
        }

        union = new WeightedQuickUnionUF(1 + N * N);

        unionbottom = new WeightedQuickUnionUF(2 + N * N);

        sumopen = 0;
        this.N = N;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!openclosesystem[row][col]) {
            openclosesystem[row][col] = true;
            sumopen += 1;
        }
        if (row == 0) {
            union.union(arrayToUnion(row, col), 0);
            unionbottom.union(arrayToUnion(row, col), 0);

        }
        if (row > 0 && isOpen(row - 1, col)) {
            union.union(arrayToUnion(row, col), arrayToUnion(row - 1, col));
            unionbottom.union(arrayToUnion(row, col), arrayToUnion(row - 1, col));

        }
        if (col > 0 && isOpen(row, col - 1)) {
            union.union(arrayToUnion(row, col), arrayToUnion(row, col - 1));
            unionbottom.union(arrayToUnion(row, col), arrayToUnion(row, col - 1));
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            union.union(arrayToUnion(row, col), arrayToUnion(row + 1, col));
            unionbottom.union(arrayToUnion(row, col), arrayToUnion(row + 1, col));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            union.union(arrayToUnion(row, col), arrayToUnion(row, col + 1));
            unionbottom.union(arrayToUnion(row, col), arrayToUnion(row, col + 1));
        }
        if (row == N - 1) {
            unionbottom.union(arrayToUnion(row, col), N * N + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openclosesystem[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (union.connected(0, arrayToUnion(row, col)) && isOpen(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return sumopen;
    }

    // does the system percolate?
    public boolean percolates() {
        //????????????????????????????????????
//        for (int i = 0; i < N; i += 1) {
//            if (isFull(N - 1, i)) {
//                return true;
//            }
//
//        }
//        return false;
        return unionbottom.connected(0, N * N + 1);
    }

    public static void main(String[] args) {
    }

}
