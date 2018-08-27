import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF unionUF;
    private final WeightedQuickUnionUF unionPercUF;
    private boolean[][] openSite;
    private final int n;
    private int openSites;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();
        this.n = n;
        unionUF = new WeightedQuickUnionUF((n * n) + 1);
        unionPercUF = new WeightedQuickUnionUF((n * n) + 2);
        openSite = new boolean[n][n];
    }

    public void open(int row, int col) {
        if (checkRange(row, col))
            throw new IllegalArgumentException(row + ", " + col);
        if (!openSite[row - 1][col - 1]) {
            openSite[row - 1][col - 1] = true;
            ++openSites;

            // top
            if (row == 1) {
                unionPercUF.union(0, getIndex(row, col));
                unionUF.union(0, getIndex(row, col));
            }
            else if (row > 1 && openSite[row - 2][col - 1]) {
                unionPercUF.union(getIndex(row, col), getIndex(row - 1, col));
                unionUF.union(getIndex(row, col), getIndex(row - 1, col));
            }

            // left
            if (col > 1 && openSite[row - 1][col - 2]) {
                unionPercUF.union(getIndex(row, col), getIndex(row, col - 1));
                unionUF.union(getIndex(row, col), getIndex(row, col - 1));
            }

            // bottom
            if (row == n) {
                unionPercUF.union(n * n + 1, getIndex(row, col));
            }
            else if (row < n && openSite[row][col - 1]) {
                unionPercUF.union(getIndex(row, col), getIndex(row + 1, col));
                unionUF.union(getIndex(row, col), getIndex(row + 1, col));
            }

            // right
            if (col < n && openSite[row - 1][col]) {
                unionPercUF.union(getIndex(row, col), getIndex(row, col + 1));
                unionUF.union(getIndex(row, col), getIndex(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (checkRange(row, col))
            throw new IllegalArgumentException(row + ", " + col);

        return openSite[row - 1][col - 1];
    }
    public boolean isFull(int row, int col) {
        if (checkRange(row, col))
            throw new IllegalArgumentException(row + ", " + col);

        return unionUF.connected(getIndex(row, col), 0);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return unionPercUF.connected(0, n * n + 1);
    }

    private boolean checkRange(int row, int col) {
        return row < 1 || row > n || col < 1 || col > n;
    }
    private int getIndex(int row, int col) {
        return ((row - 1) * n)  + col;
    }
}
