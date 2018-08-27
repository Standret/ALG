import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceHi;
    private double confidenceLo;

    public PercolationStats(int n, int t) {
        if (n < 1 || t < 1)
            throw new IllegalArgumentException(n + ", " + t);

        doSimulate(n, t);
    }

    public static void main(String[] args) {
        System.out.println("start");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        Stopwatch st = new Stopwatch();

        PercolationStats stat = new PercolationStats(n, t);

        System.out.println("mean                    = " + stat.mean());
        System.out.println("stddev                  = " + stat.stddev());
        System.out.println("95% confidence interval = " + stat.confidenceLo() + ", " + stat.confidenceHi());
        System.out.println(st.elapsedTime());
    }

    private void doSimulate(int n, int t) {
        double[] steepValue = new double[t];
        for (int i = 0; i < t; ++i) {

            Percolation perc = new Percolation(n);

            int runs = 0;
            for (; !perc.percolates(); ++runs) {
                int column;
                int row;

                do {
                    column = 1 + StdRandom.uniform(n);
                    row = 1 + StdRandom.uniform(n);
                } while (perc.isOpen(row, column));

                perc.open(row, column);
            }

            steepValue[i] = runs / (double)  (n * n);
        }

        mean = StdStats.mean(steepValue);
        stddev = StdStats.stddev(steepValue);
        double confidenceFraction = (1.96 * stddev) / Math.sqrt(t);
        confidenceLo = mean - confidenceFraction;
        confidenceHi = mean + confidenceFraction;
    }

    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceHi() {
        return confidenceHi;
    }
    public double confidenceLo() {
        return confidenceLo;
    }
}
