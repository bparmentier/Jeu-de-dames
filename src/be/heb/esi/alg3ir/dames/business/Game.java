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
 * Class Game. 
 * 
 * Class that implements the game.
 * 
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class Game {

    private Piece[][] board;
    private Color currentPlayer;
    private final Color whitePlayer;
    private final Color blackPlayer;
    private boolean canEatAgain;

    /**
     * Default Constructor Game. 
     * 
     * The white player always starts and starts on the bottom of the board.
     * The black player is always second to play and starts on the top of the board.
     */
    public Game() {

        whitePlayer = Color.WHITE;
        blackPlayer = Color.BLACK;
        currentPlayer = whitePlayer;
        canEatAgain = false;
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

    /**
     * movePiece method. Move a piece from a position to an other following some restriction.
     * 
     * @param posFrom the position of the piece to move
     * @param posTo the position where to move the piece
     * @throws IndexOutOfBoundsException if one of the positions is out of the board
     * @throws IllegalArgumentException if the piece to move does not belong to the currentPlayer
     */
    public void movePiece(Position posFrom, Position posTo) throws IllegalArgumentException {
        final int fromLine = posFrom.getLine();
        final int fromColumn = posFrom.getColumn();
        final int toLine = posTo.getLine();
        final int toColumn = posTo.getColumn();

        if ((fromLine < 0)
                || (fromLine > 9)
                || (fromColumn < 0)
                || (fromColumn > 9)
                || (toLine < 0)
                || (toLine > 9)
                || (toColumn < 0)
                || (toColumn > 9)) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        if (board[fromLine][fromColumn].getColor() != currentPlayer) {
            throw new IllegalArgumentException("Bad Color! It's " + currentPlayer + "'s turn!");
        }

        List<Position> listValidPositions = getValidPositions(posFrom);;

        if (canEatAgain) {
            // if we can eat an other pawn, we must change the list of valid 
            // positions because the pawn can eat either going up or going down

            listValidPositions.removeAll(listValidPositions);
            canEatAgain(posFrom, listValidPositions); 
        }
        
        Piece pieceToMove = board[fromLine][fromColumn];

        /* Check if pion should become a dame */
        if (pieceToMove.getType() == PieceType.PION
                && (toLine == 0 || toLine == 9)) {
            pieceToMove.setType(PieceType.DAME);
        }

        for (int i = 0; i < listValidPositions.size(); i++) {
            if (posTo.equals(listValidPositions.get(i))) {
                board[fromLine][fromColumn] = new Piece();
                board[toLine][toColumn] = pieceToMove;

                /* if a pion has been eaten */
                if (abs(fromLine - toLine) == 2) {
                    board[(fromLine + toLine) / 2][(fromColumn + toColumn) / 2] = new Piece();
                    listValidPositions.removeAll(listValidPositions);
                    canEatAgain = canEatAgain(posTo, listValidPositions);
                    if (!canEatAgain) {
                        alternatePlayer();
                    }
                } else {
                    alternatePlayer();
                }
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

    /**
     * isFinished method returns the status ended or not of the game.
     * 
     * @return true if the game is finish, else false
     */
    public boolean isFinished() {
        // TODO
        return false;
    }

    /**
     * getValidPositions method returns the list of all the valid positions where a pawn can go
     * 
     * @param posPieceToMove the position of the pawn
     * 
     * @return the list of valid positions for the pawn to move.
     */
    public List<Position> getValidPositions(Position posPieceToMove) {

        List<Position> listPosition = new ArrayList<>();
        final int line = posPieceToMove.getLine();
        final int column = posPieceToMove.getColumn();

        int upOrDown;

        if (board[line][column].getColor() == Color.WHITE) {
            upOrDown = -1;
        } else {
            upOrDown = 1;
        }

        /* if not on left border */
        if ((column != 0) && (line+upOrDown >= 0)  && (line+upOrDown <= 9)) {
            /* if top-left square not empty */
            if (!board[line + upOrDown][column - 1].isEmpty()) {
                /* if top-left square is opposite color */
                if ((board[line + upOrDown][column - 1].getColor() != currentPlayer)
                        && (column > 1) && (line + (upOrDown * 2) >= 0)
                        && (line + (upOrDown * 2) <= 9)
                        /* if square after pion is empty --> then we can eat */
                        && (board[line + (upOrDown * 2)][column - 2].isEmpty())) {
                    listPosition.add(new Position(line + (upOrDown * 2), column - 2));
                }
            } else {
                listPosition.add(new Position(line + upOrDown, column - 1));
            }
        }
        /* if not on right border */
        if ((column != 9) && (line+upOrDown >= 0) && (line+upOrDown <= 9)) {
            /* if top-right square not empty */
            if (!board[line + upOrDown][column + 1].isEmpty()) {
                /* if top-right square is opposite color */
                if ((board[line + upOrDown][column + 1].getColor() != currentPlayer)
                        && (column < 8) && (line + (upOrDown * 2) >= 0)
                        && (line + (upOrDown * 2) <= 9)
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

    /**
     * canEatAgain method verify if the pawn just moved can eat an other pawn
     * 
     * @param posPiece the position of the pawn
     * @param posValid the list of the valid positions where the pawn can go
     * 
     * @return true if the pawn can eat again, else false
     */
    public boolean canEatAgain(Position posPiece, List<Position> posValid) {
        return topLeft(posPiece, posValid)
                || topRight(posPiece, posValid)
                || bottomLeft(posPiece, posValid)
                || bottomRight(posPiece, posValid);

    }

    private boolean topLeft(Position posPiece, List<Position> posValid) {
        final int line = posPiece.getLine();
        final int column = posPiece.getColumn();

        /* if place to eat - without going out of bounds */
        if ((line - 2) >= 0 && (column - 2) >= 0) {
            /* if top-left square not empty */
            if (!board[line - 1][column - 1].isEmpty()
                    /* if top-left square is opposite color */
                    && (board[line - 1][column - 1].getColor() != currentPlayer)
                    /* if square after pion is empty --> then we can eat */
                    && (board[line - 2][column - 2].isEmpty())) {
                posValid.add(new Position(line - 2, column - 2));
                return true;
            }
        }
        return false;
    }

    private boolean topRight(Position posPiece, List<Position> posValid) {
        final int line = posPiece.getLine();
        final int column = posPiece.getColumn();

        /* if place to eat - without going out of bounds */
        if ((line - 2) >= 0 && (column + 2) <= 9) {
            /* if top-right square not empty */
            if (!board[line - 1][column + 1].isEmpty()
                    /* if top-right square is opposite color */
                    && (board[line - 1][column + 1].getColor() != currentPlayer)
                    /* if square after pion is empty --> then we can eat */
                    && (board[line - 2][column + 2].isEmpty())) {
                posValid.add(new Position(line - 2, column + 2));
                return true;
            }
        }
        return false;
    }

    private boolean bottomLeft(Position posPiece, List<Position> posValid) {
        final int line = posPiece.getLine();
        final int column = posPiece.getColumn();

        /* if place to eat - without going out of bounds */
        if ((line + 2) <= 9 && (column - 2) >= 0) {
            /* if bottom-left square not empty */
            if (!board[line + 1][column - 1].isEmpty()
                    /* if bottom-left square is opposite color */
                    && (board[line + 1][column - 1].getColor() != currentPlayer)
                    /* if square after pion is empty --> then we can eat */
                    && (board[line + 2][column - 2].isEmpty())) {
                posValid.add(new Position(line + 2, column - 2));
                return true;
            }
        }
        return false;
    }

    private boolean bottomRight(Position posPiece, List<Position> posValid) {
        final int line = posPiece.getLine();
        final int column = posPiece.getColumn();

        /* if place to eat - without going out of bounds */
        if ((line + 2) <= 9 && (column + 2) <= 9) {
            /* if bottom-right square not empty */
            if (!board[line + 1][column + 1].isEmpty()
                    /* if bottom-right square is opposite color */
                    && (board[line + 1][column + 1].getColor() != currentPlayer)
                    /* if square after pion is empty --> then we can eat */
                    && (board[line + 2][column + 2].isEmpty())) {
                posValid.add(new Position(line + 2, column + 2));
                return true;
            }
        }
        return false;
    }

    /**
     * getBoard method. Getter for the board of the game
     * 
     * @return the board of the game
     */
    public Piece[][] getBoard() {
        Piece[][] boardCopy = new Piece[10][10];

        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, board[i].length);
        }
        return boardCopy;
    }

    /**
     * currentPlayer getter returns the current player.
     * 
     * @return the current player
     */
    public Color currentPlayer() {
        return this.currentPlayer;
    }
}