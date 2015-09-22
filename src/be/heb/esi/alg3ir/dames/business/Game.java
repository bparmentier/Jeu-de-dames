/*
 * Copyright (C) 2015 Bruno Parmentier - Jonathan Wyckmans
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
 */
public class Game {
    private Piece[][] board;
    private Player currentPlayer;
    private final Player whitePlayer;
    private final Player blackPlayer;

    public Game() {
        this(new Player("whitePlayer"), new Player("blackPlayer"));
    }
    
    public Game(Player whitePlayer, Player blackPlayer) {
        if (whitePlayer.equals(blackPlayer)) {
            throw new IllegalArgumentException("Player names must be different");
        }
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
        setupBoard();
    }
    
    private void setupBoard() {
        board = new Piece[10][10];
        
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 10; column++) {
                if ((line + column) % 2 == 1) {
                    board[line][column] = Piece.BLACK_PION;
                } else {
                    board[line][column] = Piece.EMPTY_SQUARE;
                }
            }
        }
        
        for (int line = 4; line < 6; line++) {
            for (int column = 0; column < 10; column++) {
                board[line][column] = Piece.EMPTY_SQUARE;
            }
        }
        
        for (int line = 6; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                if ((line + column) % 2 == 1) {
                    board[line][column] = Piece.WHITE_PION;
                } else {
                    board[line][column] = Piece.EMPTY_SQUARE;
                }
            }
        }
    }
    
    public void start() {
        
    }
    
    public void movePiece(int fromLine, int fromColumn, int toLine, int toColumn) {
        if (isValidMove(fromLine, fromColumn, toLine, toColumn)) {
            Piece pieceToMove = board[fromLine][fromColumn];
            board[fromLine][fromColumn] = Piece.EMPTY_SQUARE;
            board[toLine][toColumn] = pieceToMove;
            alternatePlayer();
        }
    }
    
    private void alternatePlayer() {
        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }
    
    public boolean isFinished() {
        // TODO
        return false;
    }
    
    private boolean isValidMove(int fromLine, int fromColumn, int toLine, int toColumn) {
        // TODO
        return true;
    }

    @Override
    public String toString() {
        String out = "  0123456789\n";
        for (int line = 0; line < 10; line++) {
            out += line + " ";
            for (int column = 0; column < 10; column++) {
                out += board[line][column];
            }
            out += "\n";
        }
        return out;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }
}
