import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStatistics {

    private double[] _steepValue;
    private int _n;
    private int _t;

    public static void main(String[] args) {
        int t = 100;
        PercolationStatistics stat = new PercolationStatistics(200, t);
        stat.doSimulate();

        System.out.println("mean                    = " + stat.mean());
        System.out.println("stddev                  = " + stat.stdsev());
        System.out.println("95% confidence interval = [" + (stat.mean() - (1.96 * Math.sqrt(stat.stdsev())) / Math.sqrt(t)) + ", " + (stat.mean() + (1.96 * Math.sqrt(stat.stdsev())) / Math.sqrt(t)) + "]");
    }

    public PercolationStatistics(int n, int t) {
        _steepValue = new double[t];
        _n = 200;
        _t = 100;
    }

    public void doSimulate() {
        for (int i = 0; i < _t; ++i) {
            Percolation perc = new Percolation(_n);
            for (int j = 1; j <= _n; ++j) {
                perc.open(StdRandom.uniform(1, _n), StdRandom.uniform(1, _n));
                if (perc.percolates()) {
                    _steepValue[i] = _n / j;
                    break;
                }
            }
        }
    }

    public double mean() {
        return StdStats.mean(_steepValue);
    }

    public double stdsev() {
        return StdStats.stddev(_steepValue);
    }


}
