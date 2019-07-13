package percolationproject;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *
 * @author Nebiyu Alemu
 */
public class Percolation {

    private int[][] grid;

    //dimension of the grid
    private int dimen;

    //number of open sites
    private int numOpenSites;

    private WeightedQuickUnionUF uf;

    //rank of an imaginary point above the top row of the grid
    private int topRank;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        dimen = n;
        topRank = (dimen * dimen) + 2;
        uf = new WeightedQuickUnionUF(topRank + 2);

        //plus one to make the grid start from one
        grid = new int[dimen + 1][dimen + 1];

        //initialize all points as closed
        for (int i = 1; i < dimen + 1; i++) {
            for (int j = 0; j < dimen + 1; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            //if this is the top row, then union it with the imaginary point above it
            if (row == 1) {
                uf.union(topRank, toWQUF(row, col));
            }
            if (row == dimen) {
                uf.union(topRank - 1, toWQUF(row, col));
            }
            //union this point with its neighbors if it has them, has not 
            //already connected with them and the neighbors are open
            if (row > 1 && !uf.connected(toWQUF(row, col), toWQUF(row - 1, col))
                    && isOpen(row - 1, col)) {
                uf.union(toWQUF(row, col), toWQUF(row - 1, col));
            }
            if (col > 1 && !uf.connected(toWQUF(row, col), toWQUF(row, col - 1))
                    && isOpen(row, col - 1)) {
                uf.union(toWQUF(row, col), toWQUF(row, col - 1));
            }
            if (row < dimen && !uf.connected(toWQUF(row, col), toWQUF(row + 1, col))
                    && isOpen(row + 1, col)) {
                uf.union(toWQUF(row, col), toWQUF(row + 1, col));
            }
            if (col < dimen && !uf.connected(toWQUF(row, col), toWQUF(row, col + 1))
                    && isOpen(row, col + 1)) {
                uf.union(toWQUF(row, col), toWQUF(row, col + 1));
            }

            grid[row][col] = uf.find(toWQUF(row, col));
            numOpenSites++;

        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] != 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.connected(topRank,toWQUF(row,col));
    }

    // number of open sites
    public int numberOfOpenSites() {

        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        //if ay of the bottom points have the same rank as the top, it percolates
        return uf.connected(topRank, topRank - 1);
    }

    //uses row and column values to give the Weighted Quick Union identification number
    private int toWQUF(int row, int col) {
        return ((row - 1) * dimen) + col;
    }

    private void full(int row, int col) {
        validate(row, col);
        if (grid[row - 1][col - 1] == 0) {
            grid[row - 1][col - 1] = 1;

            if (isOpen(row, col - 1)) {
                uf.union(toWQUF(row, col), toWQUF(row, col - 1));
                full(row, col - 1);
            }
            if (isOpen(row, col + 1)) {
                uf.union(toWQUF(row, col), toWQUF(row, col + 1));
                full(row, col + 1);
            }
            if (isOpen(row + 1, col)) {
                uf.union(toWQUF(row + 1, col), toWQUF(row + 1, col));
                full(row + 1, col);
            }
            if (isOpen(row - 1, col)) {
                uf.union(toWQUF(row, col), toWQUF(row - 1, col));
                full(row - 1, col);
            }
        } else {
            return;
        }
    }

    private void validate(int row, int col) {
        if (row < 1 || row > dimen) {
            throw new IllegalArgumentException("row " + row + " is not between 1 and " + dimen);
        }
        if (col < 1 || col > dimen) {
            throw new IllegalArgumentException("column " + col + " is not between 1 and " + dimen);
        }
    }
}
