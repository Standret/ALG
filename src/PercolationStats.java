import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Map;

public class PercolationStatistics {

    private double[] _steepValue;
    private int _n;
    private int _t;

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStatistics stat = new PercolationStatistics(n, t);

        System.out.println("start");
        Stopwatch temp = new Stopwatch();
        stat.doSimulate();

        System.out.println("mean                    = " + stat.mean());
        System.out.println("stddev                  = " + stat.stdsev());
        System.out.println("95% confidence interval = [" + (stat.mean() - (1.96 * Math.sqrt(stat.stdsev())) / Math.sqrt(t)) + ", " + (stat.mean() + (1.96 * Math.sqrt(stat.stdsev())) / Math.sqrt(t)) + "]");
        System.out.println ("elaspes time: " + temp.elapsedTime());
    }

    public PercolationStatistics(int n, int t) {
        if (n < 0 || t < 0)
             throw new IllegalArgumentException(n + ", " + t);
        _steepValue = new double[t];
        _n = n;
        _t = t;
    }

    public void doSimulate() {
        for (int i = 0; i < _t; ++i) {

            Percolation perc = new Percolation(_n);

            int runs = 0;
            for (; i < _n * _n && !perc.percolates(); ++runs) {
                int column;
                int row;

                do {
                    column = 1 + StdRandom.uniform(_n);
                    row = 1 + StdRandom.uniform(_n);
                } while (perc.isOpen(row, column));

                perc.open(row, column);
            }

            _steepValue[i] = runs / (double)  (_n * _n);
        }
    }

    public double mean() {
        return StdStats.mean(_steepValue);
    }

    public double stdsev() {
        return StdStats.stddev(_steepValue);
    }


}
