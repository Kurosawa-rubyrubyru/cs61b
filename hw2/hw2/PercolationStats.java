package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;
    private double[] ans;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (T <= 0 || N <= 0) {
            throw new IllegalArgumentException("T<=0||N<=0");
        }
        ans = new double[T];
        for (int times = 0; times < T; times += 1) {
            Percolation p = pf.make(N);
            int sumsites = 0;
            while (!p.percolates()) {
                int randrow;
                int randcol;
                while (true) {
                    randrow = StdRandom.uniform(N);
                    randcol = StdRandom.uniform(N);
                    if (!p.isOpen(randrow, randcol)) {
                        break;
                    }
                }
                p.open(randrow, randcol);
                sumsites += 1;
            }
            ans[times] = (double) sumsites / (double) (N * N);
        }
        mean = StdStats.mean(ans);
        stddev = StdStats.stddev(ans);
        confidenceLow = mean - 1.96 * (stddev / Math.sqrt((double) T));
        confidenceHigh = mean + 1.96 * (stddev / Math.sqrt((double) T));

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return confidenceLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confidenceHigh;
    }


}
