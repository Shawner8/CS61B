package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private WeightedQuickUnionUF uf;
    private boolean[] openArray;
    private boolean[] connectWithTopArray;
    private boolean[] connectWithBottomArray;
    private int numOfOpen;
    private boolean percolated;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        uf = new WeightedQuickUnionUF(N * N);
        numOfOpen = 0;
        percolated = false;
        openArray = new boolean[N * N];
        connectWithTopArray = new boolean[N * N];
        connectWithBottomArray = new boolean[N * N];
        for (int i = 0; i < N; i += 1) {
            connectWithTopArray[rc2Ind(0, i)] = true;
        }
        for (int i = 0; i < N; i += 1) {
            connectWithBottomArray[rc2Ind(N - 1, i)] = true;
        }
    }

    /* checks whether the index is valid. */
    private void check(int index) {
        if (index < 0 || index > N - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    /** converts (ROW, COL) coordinate into 1-dim index. */
    public int rc2Ind(int row, int col) {
        return row * N + col;
    }

    /** converts 1-dim index into ROW. */
    public int ind2r(int N) {
        return N / this.N;
    }

    /** converts 1-dim index into COL. */
    public int ind2c(int N) {
        return N % this.N;
    }

    /** returns whether (ROW, COL) connects with the top line. */
    private boolean connectWithTop(int row, int col) {
        return connectWithTopArray[uf.find(rc2Ind(row, col))];
    }

    /** returns whether (ROW, COL) connects with the bottom line. */
    private boolean connectWithBottom(int row, int col) {
        return connectWithBottomArray[uf.find(rc2Ind(row, col))];
    }

    /** connects (ROW, COL) with (ROW + rOffset, COL + cOffset). */
    private void connectWithSite(int row, int col, int rOffset, int cOffset) {
        assert isOpen(row, col);
        if (isOpen(row + rOffset, col + cOffset)) {
            boolean top = connectWithTop(row, col) || connectWithTop(row + rOffset, col + cOffset);
            boolean bottom = connectWithBottom(row, col) || connectWithBottom(row + rOffset, col + cOffset);
            uf.union(rc2Ind(row, col), rc2Ind(row + rOffset, col + cOffset));
            connectWithTopArray[uf.find(rc2Ind(row, col))] = top;
            connectWithBottomArray[uf.find(rc2Ind(row, col))] = bottom;
        }
    }

    private void connectWithUpSite(int row, int col) {
        connectWithSite(row, col, -1, 0);
    }

    private void connectWithDownSite(int row, int col) {
        connectWithSite(row, col, 1, 0);
    }

    private void connectWithLeftSite(int row, int col) {
        connectWithSite(row, col, 0, -1);
    }

    private void connectWithRightSite(int row, int col) {
        connectWithSite(row, col, 0, 1);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row);
        check(col);
        if (!isOpen(row, col)) {
            int ind = rc2Ind(row, col);
            openArray[ind] = true;
            numOfOpen += 1;
            if (row != 0) {
                connectWithUpSite(row, col);
            }
            if (row != N - 1) {
                connectWithDownSite(row, col);
            }
            if (col != 0) {
                connectWithLeftSite(row, col);
            }
            if (col != N - 1) {
                connectWithRightSite(row, col);
            }
            if (connectWithTop(row, col) && connectWithBottom(row, col)) {
                percolated = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row);
        check(col);
        return openArray[rc2Ind(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row);
        check(col);
        return isOpen(row, col) && connectWithTop(row, col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }
}
