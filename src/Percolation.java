import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // perform trials independent experiments on an n-by-n grid

    private WeightedQuickUnionUF _unionUF;

    protected Percolation(int N) {
        _unionUF = new WeightedQuickUnionUF(N);
    }

    public static void main(String[] args) {
        System.out.println("Hi ");
    }
}
