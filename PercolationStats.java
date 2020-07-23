import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] prob;
    private int trials;

    public PercolationStats(int n, int t) {
        trials = t;
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("arguments must be above 0");
        prob = new double[trials];
        for (int m = 0; m < trials; m++) {
            Percolation test = new Percolation(n);
            double p = 0;
            int i, j;
            int percolated = 0;
            while (true) {
                do {
                    i = StdRandom.uniform(n) + 1;
                    j = StdRandom.uniform(n) + 1;
                } while (test.isOpen(i, j));
                p++;
                test.open(i, j);
                for (int k = 1; k <= n; k++) {
                    if (test.isFull(n, k)) {
                        percolated = 1;
                        break;
                    }
                }
                if (percolated == 1) break;

            }
            prob[m] = p / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(prob);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(prob);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double std = StdStats.stddev(prob);
        double mean = StdStats.mean(prob);
        return mean - (1.96 * std / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double std = StdStats.stddev(prob);
        double mean = StdStats.mean(prob);
        return mean + (1.96 * std / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int i, n = 0;
        int trials = 0;
        //while (!StdIn.isEmpty()) {
        //    n = StdIn.readInt();
        //    trials = StdIn.readInt();
        //}
        n = Integer.parseInt(args[0]);
        trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.print("mean = ");
        StdOut.print(stats.mean());
        StdOut.println();
        StdOut.print("stddev = ");
        StdOut.print(stats.stddev());
        StdOut.println();
        StdOut.print("95% confidence interval = [");
        StdOut.print(stats.confidenceLo());
        StdOut.print(stats.confidenceHi());
        StdOut.print("]");
    }

}
