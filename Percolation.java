//import edu.princeton.cs.algs4.WeightedQuickUnionUF;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int sites;
    private WeightedQuickUnionUF a;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");
        sites = n;
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
        a = new WeightedQuickUnionUF((n * n) + 2);
        for (int k = 0; k < n; k++) {
            a.union(k, n * n);
            a.union((n * n) - k - 1, (n * n) + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > sites || row < 1 || col > sites || col < 1)
            throw new IllegalArgumentException("row or column must be within range");
        row--;
        col--;
        grid[row][col] = 1;
        if (row > 0) {
            if (grid[row - 1][col] == 1) a.union((sites * (row - 1)) + col, (sites * row) + col);
        }
        if (col > 0) {
            if (grid[row][col - 1] == 1) a.union((sites * row) + col - 1, (sites * row) + col);
        }
        if (col < sites - 1) {
            if (grid[row][col + 1] == 1) a.union((sites * row) + col + 1, (sites * row) + col);
        }
        if (row < sites - 1) {
            if (grid[row + 1][col] == 1) a.union((sites * (row + 1)) + col, (sites * row) + col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > sites || row < 1 || col > sites || col < 1)
            throw new IllegalArgumentException("row or column must be within range");
        row--;
        col--;
        if (grid[row][col] == 1) return true;
        else return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > sites || row < 1 || col > sites || col < 1)
            throw new IllegalArgumentException("row or column must be within range");
        row--;
        col--;
        if (a.connected(sites * sites, (sites * row) + col) && grid[row][col] == 1) return true;
        else return false;
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        int noofopen = 0;
        for (int i = 0; i < sites; i++) {
            for (int j = 0; j < sites; j++) {
                if (grid[i][j] == 1) noofopen++;
            }
        }
        return noofopen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (a.connected(sites * sites, (sites * sites) + 1)) return true;
        else return false;
    }

}
