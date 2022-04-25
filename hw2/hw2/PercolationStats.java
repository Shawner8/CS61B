package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] percolationThresholds;

    private int[] getRandomSeq(int N) {
        int[] seq = new int[N];
        for (int i = 0; i < N; i += 1) {
            seq[i] = i;
        }
        StdRandom.shuffle(seq);
        return seq;
    }

    private double percolationExperiment(int N, PercolationFactory pf) {
        Percolation p = pf.make(N);
        int[] randSeq = getRandomSeq(N * N);
        int i = 0;
        while (!p.percolates()) {
            int row = p.ind2r(randSeq[i]);
            int col = p.ind2c(randSeq[i]);
            p.open(row, col);
            i += 1;
        }
        return ((double) i) / N / N;
    }

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        percolationThresholds = new double[T];
        for (int t = 0; t < T; t += 1) {
            percolationThresholds[t] = percolationExperiment(N, pf);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(percolationThresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(percolationThresholds.length);
    }
}
