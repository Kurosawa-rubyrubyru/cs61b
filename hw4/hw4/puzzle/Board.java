package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

/**
 * Returns the string representation of the board.
 * Uncomment this method.
 */
public class Board implements WorldState {

    private int[][] board;

    public Board(int[][] tiles) {
        board = new int[tiles[0].length][tiles[0].length];
        for (int i = 0; i < tiles[0].length; i += 1) {
            for (int j = 0; j < tiles[0].length; j += 1) {
                board[i][j] = tiles[i][j];
            }
        }
    }


    public int tileAt(int i, int j) {
        return board[i][j];
    }


    public int size() {
        return board[0].length;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
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
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int sumhamming = 0;
        for (int i = 0; i < board[0].length; i += 1) {
            for (int j = 0; j < board[0].length; j += 1) {
                if (i < board.length - 1 || j < board.length - 1) {
                    if ((board[i][j] - 1) != i * board[0].length + j) {
                        sumhamming += 1;
                    }
                }
            }
        }
        return sumhamming;
    }

    public int manhattan() {
        int summanhattan = 0;
        int nowx, nowy, truex, truey;
        for (int i = 0; i < board[0].length; i += 1) {
            for (int j = 0; j < board[0].length; j += 1) {
                if (board[i][j] != 0) {
                    nowx = i;
                    nowy = j;
                    truex = (board[i][j] - 1) / board[0].length;
                    truey = (board[i][j] - 1) % board[0].length;
                    summanhattan += (Math.abs(nowx - truex) + Math.abs(nowy - truey));
                }
            }
        }
        return summanhattan;

    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (y.getClass() == this.getClass()) {
            if (board[0].length == ((Board) y).board[0].length) {
                for (int i = 0; i < board[0].length; i += 1) {
                    for (int j = 0; j < board[0].length; j += 1) {
                        if (this.board[i][j] != ((Board) y).board[i][j]) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return (this.board.toString().equals(((Board) y).board.toString()));
        }
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

