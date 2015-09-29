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
    private Color currentPlayer;
    private final Color whitePlayer;
    private final Color blackPlayer;

    public Game() {
        
        whitePlayer = Color.WHITE;
        blackPlayer = Color.BLACK;
        currentPlayer = whitePlayer;
        setupBoard();
    }

    private void setupBoard() {
        board = new Piece[10][10];

        /* Black pions */
        for (int line = 0; line < 4; line++) {
            for (int column = 0; column < 10; column++) {
                if ((line + column) % 2 == 1) {
                    board[line][column] = new Piece(Color.BLACK, PieceType.PION);
                } else {
                    board[line][column] = new Piece();
                }
            }
        }

        /* Empty squares */
        for (int line = 4; line < 6; line++) {
            for (int column = 0; column < 10; column++) {
                board[line][column] = new Piece();
            }
        }

        /* White pions */
        for (int line = 6; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                if ((line + column) % 2 == 1) {
                    board[line][column] = new Piece(Color.WHITE, PieceType.PION);
                } else {
                    board[line][column] = new Piece();
                }
            }
        }
    }

    public void start() {

    }

    public void movePiece(Position posFrom, Position posTo) {
        if ((posFrom.getLine() < 0)
                || (posFrom.getLine() > 9)
                || (posFrom.getColumn() < 0)
                || (posFrom.getColumn() > 9)
                || (posTo.getLine() < 0)
                || (posTo.getLine() > 9)
                || (posTo.getColumn() < 0)
                || (posTo.getColumn() > 9)) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        
        List<Position> listValidPositions = getValidPositions(posFrom);

        for (int i = 0; i < listValidPositions.size(); i++) {
            if (posTo.equals(listValidPositions.get(i))) {
                Piece pieceToMove = board[posFrom.getLine()][posFrom.getColumn()];
                
                /* Check if pion should become a dame */
                if (pieceToMove.getType() == PieceType.PION
                        && (posTo.getLine() == 0 || posTo.getLine() == 9)) {
                    pieceToMove.setType(PieceType.DAME);
                }
                
                
                board[posFrom.getLine()][posFrom.getColumn()] = new Piece();
                board[posTo.getLine()][posTo.getColumn()] = pieceToMove;
                
                /* if a pion has been eaten */
                if (abs(posFrom.getLine() - posTo.getLine()) == 2) {
                    board[(posFrom.getLine() + posTo.getLine()) / 2][(posFrom.getColumn() + posTo.getColumn()) / 2] = new Piece();
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
        final int line = posPieceToMove.getLine();
        final int column = posPieceToMove.getColumn();

        /* if white player */
        if (board[line][column].getColor() == Color.WHITE) {
            /* if not on left border */
            if (column != 0) {
                /* if top-left square not empty */
                if (!board[line - 1][column - 1].isEmpty()) {
                    /* if top-left square is opposite color */
                    if ((board[line - 1][column - 1].getColor() == Color.BLACK)
                            && (column > 1)
                            && (board[line - 2][column - 2].isEmpty())) {
                        listPosition.add(new Position(line - 2, column - 2));
                    }
                } else {
                    listPosition.add(new Position(line - 1, column - 1));
                }
            }
            /* if not on right border */
            if (column != 9) {
                /* if top-right square not empty */
                if (!board[line - 1][column + 1].isEmpty()) {
                    /* if top-right square is opposite color */
                    if ((board[line - 1][column + 1].getColor() == Color.BLACK)
                        && (column < 8)
                        && (board[line - 2][column + 2].isEmpty())) {
                                listPosition.add(new Position(line - 2, column + 2));
                    }
                } else {
                    listPosition.add(new Position(line - 1, column + 1));
                }
            }
        /* if black player */
        } else {
            /* if not on left border */
            if (column != 0) {
                /* if bottom-left square not empty */
                if (!board[line + 1][column - 1].isEmpty()) {
                    /* if bottom-left square is opposite color */
                    if ((board[line + 1][column - 1].getColor() == Color.WHITE)
                        && (column > 1)
                        && (board[line + 2][column - 2].isEmpty())) {
                                listPosition.add(new Position(line + 2, column - 2));
                    }
                } else {
                    listPosition.add(new Position(line + 1, column - 1));
                }
            }
            /* if not on right border */
            if (column != 9) {
                /* if bottom-right square not empty */
                if (!board[line + 1][column + 1].isEmpty()) {
                    /* if bottom-right square is opposite color */
                    if ((board[line + 1][column + 1].getColor() == Color.WHITE)
                        && (column < 8)
                        && (board[line + 2][column + 2].isEmpty())) {
                                listPosition.add(new Position(line + 2, column + 2));
                    }
                } else {
                    listPosition.add(new Position(line + 1, column + 1));
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

    public Color currentPlayer() {
        return this.currentPlayer;
    }
}
