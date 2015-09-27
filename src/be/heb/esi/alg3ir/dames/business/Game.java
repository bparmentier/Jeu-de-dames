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

import static java.lang.Math.abs;
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

        List<Position> listValidPositions = getValidPositions(posFrom);

        for (int i = 0; i < listValidPositions.size(); i++) {
            if (posTo.equals(listValidPositions.get(i))) {
                Piece pieceToMove = board[posFrom.getLine()][posFrom.getColumn()];
                if (pieceToMove != Piece.WHITE_DAME && pieceToMove != Piece.BLACK_DAME) {
                    if (posTo.getLine() == 0) {
                        pieceToMove = Piece.WHITE_DAME;
                    }
                    if (posTo.getLine() == 9) {
                        pieceToMove = Piece.BLACK_DAME;
                    }
                }
                board[posFrom.getLine()][posFrom.getColumn()] = Piece.EMPTY_SQUARE;
                board[posTo.getLine()][posTo.getColumn()] = pieceToMove;
                if (abs(posFrom.getLine() - posTo.getLine()) == 2) {
                    board[(posFrom.getLine() + posTo.getLine()) / 2][(posFrom.getColumn() + posTo.getColumn()) / 2] = Piece.EMPTY_SQUARE;
                }
                alternatePlayer();
            }
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

    public List<Position> getValidPositions(Position posPieceToMove) {

        List<Position> listPosition = new ArrayList<>();

        if (board[posPieceToMove.getLine()][posPieceToMove.getColumn()] == Piece.WHITE_PION) {
            if (posPieceToMove.getColumn() != 0) {
                if (board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() - 1] != Piece.EMPTY_SQUARE) {
                    if (board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() - 1] == Piece.BLACK_PION) {
                        if (posPieceToMove.getColumn() > 1) {
                            if (board[posPieceToMove.getLine() - 2][posPieceToMove.getColumn() - 2] == Piece.EMPTY_SQUARE) {
                                listPosition.add(new Position(posPieceToMove.getLine() - 2, posPieceToMove.getColumn() - 2));
                            }
                        }
                    }
                } else {
                    listPosition.add(new Position(posPieceToMove.getLine() - 1, posPieceToMove.getColumn() - 1));
                }
            }
            if (posPieceToMove.getColumn() != 9) {
                if (board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() + 1] != Piece.EMPTY_SQUARE) {
                    if (board[posPieceToMove.getLine() - 1][posPieceToMove.getColumn() + 1] == Piece.BLACK_PION) {
                        if (posPieceToMove.getColumn() < 8) {
                            if (board[posPieceToMove.getLine() - 2][posPieceToMove.getColumn() + 2] == Piece.EMPTY_SQUARE) {
                                listPosition.add(new Position(posPieceToMove.getLine() - 2, posPieceToMove.getColumn() + 2));
                            }
                        }
                    }
                } else {
                    listPosition.add(new Position(posPieceToMove.getLine() - 1, posPieceToMove.getColumn() + 1));
                }
            }
        } else {
            if (posPieceToMove.getColumn() != 0) {
                if (board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() - 1] != Piece.EMPTY_SQUARE) {
                    if (board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() - 1] == Piece.WHITE_PION) {
                        if (posPieceToMove.getColumn() > 1) {
                            if (board[posPieceToMove.getLine() + 2][posPieceToMove.getColumn() - 2] == Piece.EMPTY_SQUARE) {
                                listPosition.add(new Position(posPieceToMove.getLine() + 2, posPieceToMove.getColumn() - 2));
                            }
                        }
                    }
                } else {
                    listPosition.add(new Position(posPieceToMove.getLine() + 1, posPieceToMove.getColumn() - 1));
                }
            }
            if (posPieceToMove.getColumn() != 9) {
                if (board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() + 1] != Piece.EMPTY_SQUARE) {
                    if (board[posPieceToMove.getLine() + 1][posPieceToMove.getColumn() + 1] == Piece.WHITE_PION) {
                        if (posPieceToMove.getColumn() < 8) {
                            if (board[posPieceToMove.getLine() + 2][posPieceToMove.getColumn() + 2] == Piece.EMPTY_SQUARE) {
                                listPosition.add(new Position(posPieceToMove.getLine() + 2, posPieceToMove.getColumn() + 2));
                            }
                        }
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
        String out = "    0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 \n";
        out += "  ";
        for (int souligne = 0; souligne < 10; souligne++) {
            out += " ---";
        }
        out += "\n";
        for (int line = 0; line < 10; line++) {
            out += line + " | ";
            for (int column = 0; column < 10; column++) {
                out += board[line][column] + " | ";
            }
            out += "\n";
            out += "  ";
            for (int souligne = 0; souligne < 10; souligne++) {
                out += " ---";
            }
            out += "\n";
        }
        return out;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }
}
