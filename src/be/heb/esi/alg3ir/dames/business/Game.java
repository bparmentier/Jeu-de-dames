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
        final int line = posFrom.getLine();
        final int column = posFrom.getColumn();

        if ((line < 0)
                || (line > 9)
                || (column < 0)
                || (column > 9)
                || (posTo.getLine() < 0)
                || (posTo.getLine() > 9)
                || (posTo.getColumn() < 0)
                || (posTo.getColumn() > 9)) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        if (board[line][column].getColor() != currentPlayer) {
            throw new IllegalArgumentException("Bad Color! It's " + currentPlayer + "'s turn!");
        }

        List<Position> listValidPositions = getValidPositions(posFrom);

        for (int i = 0; i < listValidPositions.size(); i++) {
            if (posTo.equals(listValidPositions.get(i))) {
                Piece pieceToMove = board[line][column];

                /* Check if pion should become a dame */
                if (pieceToMove.getType() == PieceType.PION
                        && (posTo.getLine() == 0 || posTo.getLine() == 9)) {
                    pieceToMove.setType(PieceType.DAME);
                }

                board[line][column] = new Piece();
                board[posTo.getLine()][posTo.getColumn()] = pieceToMove;

                /* if a pion has been eaten */
                if (abs(line - posTo.getLine()) == 2) {
                    board[(line + posTo.getLine()) / 2][(column + posTo.getColumn()) / 2] = new Piece();
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

        int upOrDown = 0;

        if (board[line][column].getColor() == Color.WHITE) {
            upOrDown = -1;
        } else {
            upOrDown = 1;
        }

        /* if not on left border */
        if (column != 0) {
            /* if top-left square not empty */
            if (!board[line + upOrDown][column - 1].isEmpty()) {
                /* if top-left square is opposite color */
                if ((board[line + upOrDown][column - 1].getColor() != currentPlayer)
                        && (column > 1)
                        /* if square after pion is empty --> then we can eat */
                        && (board[line + (upOrDown * 2)][column - 2].isEmpty())) {
                    listPosition.add(new Position(line + (upOrDown * 2), column - 2));
                }
            } else {
                listPosition.add(new Position(line - 1, column - 1));
            }
        }
        /* if not on right border */
        if (column != 9) {
            /* if top-right square not empty */
            if (!board[line + upOrDown][column + 1].isEmpty()) {
                /* if top-right square is opposite color */
                if ((board[line + upOrDown][column + 1].getColor() != currentPlayer)
                        && (column < 8)
                        /* if square after pion is empty --> then we can eat */
                        && (board[line + (upOrDown * 2)][column + 2].isEmpty())) {
                    listPosition.add(new Position(line + (upOrDown * 2), column + 2));
                }
            } else {
                listPosition.add(new Position(line + upOrDown, column + 1));
            }
        }
        return listPosition;
    }
    
    public Piece[][] getBoard() {
        Piece[][] boardCopy = new Piece[10][10];

        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, board[i].length);
        }
        return boardCopy;
    }

    public Color currentPlayer() {
        return this.currentPlayer;
    }
}
