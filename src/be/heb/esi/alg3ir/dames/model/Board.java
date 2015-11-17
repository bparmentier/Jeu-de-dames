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
package be.heb.esi.alg3ir.dames.model;

import java.util.Arrays;

/**
 * Class Board. Represent the game board.
 */
public class Board {

    private final Piece[][] board;

    /**
     * Constructor board by default
     *
     * Create a new board of 10x10 
     */
    public Board() {
        board = new Piece[10][10];

        setupBlacks();
        setupEmpty();
        setupWhites();
    }

    /**
     * Board constructor with argument
     *
     * @param board the game board.
     */
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

    /**
     * Returns the game board
     *
     * @return the game board
     */
    public Piece[][] getBoard() {
        final Piece[][] boardCopy = new Piece[board.length][];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return boardCopy;
    }

    /**
     * Returns the piece of the board at the given position 
     *
     * @param position the position of the piece in the board
     *
     * @return the piece
     * @see #getPiece(int, int) 
     */
    public Piece getPiece(Position position) {
        return board[position.getLine()][position.getColumn()];
    }

    /**
     * Returns the piece of the board at the given position 
     *
     * @param line the line of the position
     * @param column the column of the position
     *
     * @return the piece at the given position
     * @see #getPiece(be.heb.esi.alg3ir.dames.model.Position) 
     */
    public Piece getPiece(int line, int column) {
        return board[line][column];
    }

    /**
     * Method to replace a piece of the board
     * 
     * @param piece the new piece to put on the board
     * @param position the position of the piece to replace 
     */
    public void setPiece(Piece piece, Position position) {
        board[position.getLine()][position.getColumn()] = piece;
    }

    /**
     * Method to replace a piece of the board
     * 
     * @param piece the new piece to put on the board
     * @param line the line of the piece that should be replace
     * @param column the column of the piece that should be replace
     */
    public void setPiece(Piece piece, int line, int column) {
        board[line][column] = piece;
    }

    /**
     * isValidPosition method
     *
     * @param position the Position
     * @return true if position exists in the board, false otherwise
     */
    public boolean isValidPosition(Position position) {
        return (position.getLine() >= 0) && (position.getLine() <= 9)
                && (position.getColumn() >= 0) && (position.getColumn() <= 9);
    }
}
