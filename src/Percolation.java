import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // perform trials independent experiments on an n-by-n grid

    private WeightedQuickUnionUF _unionUF;
    private boolean[][] _openSite;
    private int _n;
    private int _openSites;

    protected Percolation(int n) {
        _n = n;
        _unionUF = new WeightedQuickUnionUF((n * n) + 1);
        _openSite = new boolean[n][n];
    }

    public void open(int row, int col) {
        ++_openSites;
        if (!_openSite[row - 1][col - 1]) {
            _openSite[row - 1][col - 1] = true;

            // top
            if (row == 1) {
                _unionUF.union(getIndex(row, col), 0);
            }
            else if (row > 1 && _openSite[row - 2][col - 1]) {
                _unionUF.union(getIndex(row, col), getIndex(row - 1, col));
            }

            // left
            if (col > 1 && _openSite[row - 1][col - 2]) {
                _unionUF.union(getIndex(row, col), getIndex(row, col - 1));
            }

            // bottom
//            if (row == _n) {
//                _unionUF.union(getIndex(row, col), _n * _n + 1);
//            }
            /*else*/ if (row < _n && _openSite[row][col - 1]) {
                _unionUF.union(getIndex(row, col), getIndex(row + 1, col));
            }

            // right
            if (col < _n && _openSite[row - 1][col]) {
                _unionUF.union(getIndex(row, col), getIndex(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return _openSite[row - 1][col - 1];
    }
    public boolean isFull(int row, int col) {
        return _unionUF.connected(getIndex(row, col), 0);
    }

    private int getIndex(int row, int col) {
        return ((row - 1) * _n)  + col;
    }
    public int numberOfOpenSites() {
        return _openSites;
    }
    public boolean percolates() {
        for (int i = 0; i < _n; ++i) {
            if (_unionUF.connected(0, i * _n + _n))
                return true;
        }
        return false;
    }
}
