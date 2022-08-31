package hw2;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private int sumopen;

    private boolean[][] openclosesystem;

    private boolean[][] fullsystem;

    private int N;

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
        fullsystem = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                fullsystem[i][j] = false;
            }
        }
        sumopen = 0;
        this.N = N;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (openclosesystem[row][col] == false) {
            openclosesystem[row][col] = true;
            sumopen += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openclosesystem[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return fullsystem[row][col];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return sumopen;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < N; i += 1) {
            if (fullsystem[N - 1][i]) {
                return true;
            }

        }
        return false;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
    }
}
