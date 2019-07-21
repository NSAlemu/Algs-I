/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sliderproject;

import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author nsalemu2019
 */
public class Board {

    private final int[][] tiles;
    private Integer hamming;
    private Integer manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, tiles.length);
        }

    }

    // string representation of this board
    public String toString() {
        String output = Integer.toString(tiles.length) + "\n";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                output += " " + tiles[i][j];

            }
            output += "\n";
        }
        return output;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming != null) {
            return hamming;
        }
        int hamming = -1;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] != (i * dimension()) + j + 1) {
                    hamming++;
                }
            }

        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan != null) {
            return manhattan;
        }
        int manhattan = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if(tiles[i][j] != 0)
                manhattan += Math.abs(i - ((tiles[i][j]-1) / dimension())) +  Math.abs(j - ((tiles[i][j]-1) % dimension()));

            }

        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                int exp = (i * tiles.length) + j + 1;
                if (i == tiles.length - 1 && j == tiles.length - 1) {
                    return true;
                }
                if (exp != tiles[i][j]) {
                    return false;
                }

            }
        }
        return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        if (tiles.length != ((Board) y).tiles.length) {
            return false;
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != ((Board) y).tiles[i][j]) {
                    return false;
                }

            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return () -> new NeighborIterator();

    }

    private int[][] exch(final int[][] tiles, int orgRow, int orgCol, int newRow, int newCol) {
        int[][] tempTiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, tempTiles[i], 0, tiles.length);
        }

        int temp = tempTiles[orgRow][orgCol];
        tempTiles[orgRow][orgCol] = tempTiles[newRow][newCol];
        tempTiles[newRow][newCol] = temp;

        return tempTiles;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length - 1; col++) {
                if (tiles[row][col] != 0 && tiles[row][(col + 1)] != 0) {
                    return new Board(exch(tiles, row, col, row, col + 1));
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {

    }

    private class NeighborIterator implements Iterator<Board> {

        List<Board> list = new ArrayList<>();
        private int index = 0;

        public NeighborIterator() {
            int k = -1, l = -1;
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (tiles[i][j] == 0) {
                        k = i;
                        l = j;
                        break;
                    }

                }
            }
            if (k > 0) {
                list.add(new Board(exch(tiles, k, l, k - 1, l)));
            }
            if (k < tiles.length - 1) {
                list.add(new Board(exch(tiles, k, l, k + 1, l)));
            }
            if (l > 0) {
                list.add(new Board(exch(tiles, k, l, k, l - 1)));
            }
            if (l < tiles.length - 1) {
                list.add(new Board(exch(tiles, k, l, k, l + 1)));
            }
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return list.get(index++);
            } else {
                throw new NoSuchElementException("There is no next neighbor.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
        }
    }
}
