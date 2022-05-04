package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private static final int BLANK = 0;
    private int[][] board;

    /** Constructs a board from an N-by-N array of tiles where
     *  tiles[i][j] = tile at row i, column j.
     */
    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles.length];
        for (int i = 0; i < board.length; i += 1) {
            System.arraycopy(tiles[i], 0, board[i], 0, board.length);
        }
    }

    /** Returns value of tile at row i, column j (or 0 if blank). */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= size() || j < 0 || j >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    /** Returns the board size N. */
    public int size() {
        return board.length;
    }

    /** Returns the neighbors of the current board.
     *  @source http://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    private int xyTo1D (int i, int j) {
        return i * size() + j + 1;
    }

    private int hammingDist (int i, int j, int n) {
        if (board[i][j] == n) {
            return 0;
        } else {
            return 1;
        }
    }

    /** Hamming estimate. */
    public int hamming() {
        int estimate = 0;
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (i != size() - 1 || j != size() - 1) {
                    estimate += hammingDist(i, j, xyTo1D(i, j));
                }
            }
        }
        return estimate;
    }

    private int IndexToX (int index) {
        return Math.floorMod(index - 1, 9) / size();
    }

    private int IndexToY (int index) {
        return Math.floorMod(index - 1, 9) % size();
    }

    private int manhattanDist (int i, int j) {
        return Math.abs(i - IndexToX(board[i][j])) + Math.abs(j - IndexToY(board[i][j]));
    }

    /** Manhattan estimate. */
    public int manhattan() {
        int estimate = 0;
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (i != size() - 1 || j != size() - 1) {
                    estimate += manhattanDist(i, j);
                }
            }
        }
        return estimate;
    }

    /** Estimated distance to goal. This method should
     *  simply return the results of manhattan() when submitted to
     *  Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Returns true if this board's tile values are the same
     *  position as y's.
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board o = (Board) y;
        if (size() != o.size()) {
            return false;
        }

        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (board[i][j] != o.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
