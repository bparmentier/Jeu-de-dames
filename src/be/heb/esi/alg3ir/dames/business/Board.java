/*
 * Copyright (C) 2015 gbps2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.heb.esi.alg3ir.dames.business;

/**
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class Board {

    private Piece[][] board;

    public Board() {
        board = new Piece[10][10];

        setupBlacks();
        setupEmpty();
        setupWhites();
    }

    public Board(Piece[][] board) {
        this.board = board;
    }

    private void setupBlacks() {
        /* Black pions */
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 10; column++) {
                if ((line + column) % 2 == 1) {
                    board[line][column] = new Pawn(Color.BLACK);
                } else {
                    board[line][column] = null;
                }
            }
        }
    }

    private void setupEmpty() {
        /* Empty squares */
        for (int line = 4; line < 6; line++) {
            for (int column = 0; column < 10; column++) {
                board[line][column] = null;
            }
        }
    }

    private void setupWhites() {
        /* White pions */
        for (int line = 6; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                if ((line + column) % 2 == 1) {
                    board[line][column] = new Pawn(Color.WHITE);
                } else {
                    board[line][column] = null;
                }
            }
        }
    }

    public Piece[][] getBoard() {
        return board;
        //TODO return copy of board
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece getPiece(int line, int column) {
        return board[line][column];
    }

    public void setPiece(int line, int column, Piece piece) {
        board[line][column] = piece;
    }
}
