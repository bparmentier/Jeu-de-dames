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

import java.util.ArrayList;
import java.util.List;

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

    public void movePiece(Position posFrom, Position posTo) {
        if ((posFrom.getLine() < 0) || (posFrom.getLine() > 9) || (posFrom.getColumn() < 0) || (posFrom.getColumn() > 9)
                || (posTo.getLine() < 0) || (posTo.getLine() > 9) || (posTo.getColumn() < 0) || (posTo.getColumn() > 9)) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        List<Position> listPositionPossible = getPositionPossible(posFrom);
        
        for (int i = 0; i < listPositionPossible.size(); i++) {
            if (posTo == listPositionPossible.get(i)) {
                Piece pieceToMove = board[posFrom.getLine()][posFrom.getColumn()];
                board[posFrom.getLine()][posFrom.getColumn()] = Piece.EMPTY_SQUARE;
                board[posTo.getLine()][posTo.getColumn()] = pieceToMove;
                alternatePlayer();
            }
        }
        /*if (isValidMove(fromLine, fromColumn, toLine, toColumn)) {
            Piece pieceToMove = board[fromLine][fromColumn];
            board[fromLine][fromColumn] = Piece.EMPTY_SQUARE;
            board[toLine][toColumn] = pieceToMove;
            alternatePlayer();
        }*/
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

    private boolean isValidMove(Position fromPosition, Position toPosition) {
        if (currentPlayer == whitePlayer) {
            if (board[fromPosition.getLine()][fromPosition.getColumn()] != Piece.WHITE_PION) {
                return false;
            }
            if ((toPosition.getLine() >= fromPosition.getLine()) || (toPosition.getColumn() != fromPosition.getColumn() - 1)
                    || (toPosition.getColumn() != fromPosition.getColumn() + 1) || board[toPosition.getLine()][toPosition.getColumn()] == Piece.WHITE_PION) {
                return false;
            } else {
                if (board[toPosition.getLine()][toPosition.getColumn()] == Piece.BLACK_PION) {
                    return false;
                } else {
                    if (toPosition.getLine() == fromPosition.getLine() - 2) {
                        return true;
                    }
                }
            }
        } else {
            if (board[fromPosition.getLine()][fromPosition.getColumn()] != Piece.BLACK_PION) {
                return false;
            }
            if ((toPosition.getLine() <= fromPosition.getLine()) || (toPosition.getColumn() != fromPosition.getColumn() - 1)
                    || (toPosition.getColumn() != fromPosition.getColumn() + 1) || board[toPosition.getLine()][toPosition.getColumn()] == Piece.BLACK_PION) {
                return false;
            } else {
                if (board[toPosition.getLine()][toPosition.getColumn()] == Piece.WHITE_PION) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    public List<Position> getPositionPossible(Position posPieceToMove) {

        List<Position> listPosition = new ArrayList<>();

        if (board[posPieceToMove.getLine()][posPieceToMove.getColumn()] == Piece.WHITE_PION) {
            if ((posPieceToMove.getLine() > 0) && posPieceToMove.getColumn() > 0) {
                //TODO GERE OUT OF RANGE
            }
            if (board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() - 1] != Piece.EMPTY_SQUARE) {
                if ((board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() - 1] == Piece.BLACK_PION)
                        && (board[posPieceToMove.getLine() - 2][posPieceToMove.getColumn() - 2] == Piece.EMPTY_SQUARE)) {
                    listPosition.add(new Position(posPieceToMove.getLine() - 2, posPieceToMove.getColumn() - 2));
                }
            } else {
                listPosition.add(new Position(posPieceToMove.getLine() - 1, posPieceToMove.getColumn() - 1));
                if (board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() + 1] != Piece.EMPTY_SQUARE) {
                    if ((board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() + 1] == Piece.BLACK_PION)
                            && (board[posPieceToMove.getLine() - 2][posPieceToMove.getColumn() + 2] == Piece.EMPTY_SQUARE)) {
                        listPosition.add(new Position(posPieceToMove.getLine() - 2, posPieceToMove.getColumn() + 2));
                    }
                } else {
                    listPosition.add(new Position(posPieceToMove.getLine() - 1, posPieceToMove.getColumn() + 1));
                }
            }
        } else {
            //TODO GERE OUT OF RANGE
            if (board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() - 1] != Piece.EMPTY_SQUARE) {
                if ((board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() - 1] == Piece.WHITE_PION)
                        && (board[posPieceToMove.getLine() + 2][posPieceToMove.getColumn() - 2] == Piece.EMPTY_SQUARE)) {
                    listPosition.add(new Position(posPieceToMove.getLine() + 2, posPieceToMove.getColumn() - 2));
                }
            } else {
                listPosition.add(new Position(posPieceToMove.getLine() + 1, posPieceToMove.getColumn() - 1));
                if (board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() + 1] != Piece.EMPTY_SQUARE) {
                    if ((board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() + 1] == Piece.WHITE_PION)
                            && (board[posPieceToMove.getLine() + 2][posPieceToMove.getColumn() + 2] == Piece.EMPTY_SQUARE)) {
                        listPosition.add(new Position(posPieceToMove.getLine() + 2, posPieceToMove.getColumn() + 2));
                    }
                } else {
                    listPosition.add(new Position(posPieceToMove.getLine() + 1, posPieceToMove.getColumn() + 1));
                }
            }
        }
        return listPosition;
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
