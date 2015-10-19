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

    private final Board board; 
    private Color currentPlayer; //@srv ajouter une classe gérant les joueurs.
    private final Color whitePlayer;
    private final Color blackPlayer;
    private boolean canEatAgain; //@srv est-ce vraiment un attribut et non une variable local d'une méthode?

    /**
     * Default Constructor Game.
     *
     * The white player always starts and starts on the bottom of the board. The
     * black player is always second to play and starts on the top of the board.
     */
    public Game() {

        whitePlayer = Color.WHITE;
        blackPlayer = Color.BLACK;
        currentPlayer = whitePlayer;
        canEatAgain = false;
        board = new Board();
    }

    /**
     * movePiece method. Move a piece from a position to an other following some
     * restriction.
     *
     * @param posFrom the position of the piece to move
     * @param posTo the position where to move the piece
     * @throws IndexOutOfBoundsException if one of the positions is out of the
     * board
     * @throws IllegalArgumentException if the piece to move does not belong to
     * the currentPlayer
     */
    //@srv méthode bcp trop longue: séparer en plusieurs méthodes.
    public void movePiece(Position posFrom, Position posTo) throws IllegalArgumentException {

        final int fromLine = posFrom.getLine();
        final int fromColumn = posFrom.getColumn();
        final int toLine = posTo.getLine();
        final int toColumn = posTo.getColumn();

        if (posFrom.outOfBounds() || posTo.outOfBounds()) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        if (board.getPiece(fromLine, fromColumn).getColor() != currentPlayer) {
            throw new IllegalArgumentException("Bad Color! It's " + currentPlayer + "'s turn!");
        }

        List<Position> listValidPositions = new ArrayList<>();

        if (canEatAgain) {
            canEatAgain(posFrom, listValidPositions);
        } else {
            if (board.getPiece(fromLine, fromColumn).getType() == PieceType.PION) {
                listValidPositions = getValidPositionsForPawn(posFrom);
            } else {
                listValidPositions = getValidPositionsForQueen(posFrom);
            }
        }

        Piece pieceToMove = board.getPiece(fromLine, fromColumn);

        /* Check if a pawn should become a queen */
        if (pieceToMove.getType() == PieceType.PION
                && (posTo.getLine() == 0 || posTo.getLine() == 9)) {
            pieceToMove.setType(PieceType.DAME);
        }

        for (Position pos : listValidPositions) {
            if (posTo.equals(pos)) {
                board.setPiece(fromLine, fromColumn, new Piece());
                board.setPiece(toLine, toColumn, pieceToMove);
                removeEatenPieces(posFrom, posTo);
                List<Position> listCanEatAgain = new ArrayList<>();
                if (canEatAgain(posTo, listCanEatAgain)) {
                    canEatAgain = true;
                } else {
                    canEatAgain = false;
                    alternatePlayer();
                }
            }
        }
    }

    private void alternatePlayer() { //@srv dans une classe gérant les joueurs.
        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }

    private void removeEatenPieces(Position posFrom, Position posTo) {

        int fromLine = posFrom.getLine();
        int fromColumn = posFrom.getColumn();
        int toLine = posTo.getLine();
        int toColumn = posTo.getColumn();

        int upOrDown;
        int leftOrRight;
        int nbCasesDeplacees = abs(toLine - fromLine);

        if (fromLine < toLine) {
            upOrDown = 1;
        } else {
            upOrDown = -1;
        }

        if (fromColumn < toColumn) {
            leftOrRight = 1;
        } else {
            leftOrRight = -1;
        }

        fromLine = fromLine + upOrDown;
        fromColumn = fromColumn + leftOrRight;

        for (int j = 0; j < nbCasesDeplacees - 1; j++) {
            if (!board.getPiece(fromLine, fromColumn).isEmpty()) {
                board.setPiece(fromLine, fromColumn, new Piece());
            }
            fromLine = fromLine + upOrDown;
            fromColumn = fromColumn + leftOrRight;
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
     * getValidPositions method returns the list of all the valid positions
     * where a pawn can go
     *
     * @param posPieceToMove the position of the pawn
     *
     * @return the list of valid positions for the pawn to move.
     */
    public List<Position> getValidPositionsForPawn(Position posPieceToMove) {

        List<Position> listPosition = new ArrayList<>();

        updateListPositionsForPawn(posPieceToMove, listPosition, 0); //LEFT
        updateListPositionsForPawn(posPieceToMove, listPosition, 9); //RIGHT

        return listPosition;
    }

    private void updateListPositionsForPawn(Position posPiece, List<Position> posValid, int columnLimit) {

        int upOrDown;
        int leftOrRight = leftOrRight(columnLimit);

        int line = posPiece.getLine();
        int column = posPiece.getColumn();

        if (board.getPiece(line, column).getColor() == Color.WHITE) {
            upOrDown = -1;
        } else {
            upOrDown = 1;
        }

        line = line + upOrDown;
        column = column + leftOrRight;

        /* if not on left border */
        if ((column >= 0) && (column <= 9) && (line >= 0) && (line <= 9)) {
            /* if square is empty --> we can go */
            if (board.getPiece(line, column).isEmpty()) {
                posValid.add(new Position(line, column));
            } else {
                /* if square is opposite color */
                if ((board.getPiece(line, column).getColor() != currentPlayer)
                        && (column + leftOrRight >= 0) && (column + leftOrRight <= 9)
                        && (line + upOrDown >= 0) && (line + upOrDown <= 9)
                        /* if square after pion is empty --> then we can eat */
                        && (board.getPiece(line + upOrDown, column + leftOrRight).isEmpty())) {
                    posValid.add(new Position(line + upOrDown, column + leftOrRight));
                }
            }
        }
    }

    /**
     * getValidPositionsForQueen method returns the list of all the valid
     * positions where a queen can go
     *
     * @param posPieceToMove the position of the queen
     *
     * @return the list of all the valid positions for a queen to move.
     */
    public List<Position> getValidPositionsForQueen(Position posPieceToMove) {
// @srv ajouter une lcasse Queen et une class Pawn/Piece, contenant la logique de déplacement ?
        List<Position> listPosition = new ArrayList<>();

        updateListPositionsQueens(posPieceToMove, listPosition, 0, 0); //TOP LEFT       
        updateListPositionsQueens(posPieceToMove, listPosition, 0, 9); //TOP RIGHT
        updateListPositionsQueens(posPieceToMove, listPosition, 9, 0); //BOTTOM LEFT
        updateListPositionsQueens(posPieceToMove, listPosition, 9, 9); //BOTTOM RIGHT

        return listPosition;
    }

    private void updateListPositionsQueens(Position posPiece, List<Position> posValid, int lineLimit, int columnLimit) {

        int upOrDown = upOrDown(lineLimit);
        int leftOrRight = leftOrRight(columnLimit);

        int line = posPiece.getLine() + upOrDown;
        int column = posPiece.getColumn() + leftOrRight;

        boolean canEat = false;
        boolean canGoFurther = true;

        while ((line >= 0) && (line <= 9) && (column >= 0) && (column <= 9) && (canGoFurther)) {
            /* if case is empty --> we can go */
            if (board.getPiece(line, column).isEmpty()) {
                posValid.add(new Position(line, column));
            } else {
                /* if pawn is opposite color */
                if (board.getPiece(line, column).getColor() != currentPlayer
                        && (line >= 1) && (line <= 9)
                        && (column >= 1) && (column <= 9)
                        && !canEat
                        /* if square after pawn is empty --> then we can eat */
                        && board.getPiece(line + upOrDown, column + leftOrRight).isEmpty()) {
                    canEat = true;
                    line = line + upOrDown;
                    column = column + leftOrRight;
                    posValid.add(new Position(line, column));
                } else {
                    canGoFurther = false;
                }
            }
            line = line + upOrDown;
            column = column + leftOrRight;
        }
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
        return updateListCanEatAgain(posPiece, posValid, 0, 0) // TOP LEFT
                || updateListCanEatAgain(posPiece, posValid, 0, 9) // TOP RIGHT
                || updateListCanEatAgain(posPiece, posValid, 9, 0) // BOTTOM LEFT
                || updateListCanEatAgain(posPiece, posValid, 9, 9); // BOTTOM RIGHT
    }

    private boolean updateListCanEatAgain(Position posPiece, List<Position> posValid, int lineLimit, int columnLimit) {

        int upOrDown = upOrDown(lineLimit);
        int leftOrRight = leftOrRight(columnLimit);

        int line = posPiece.getLine() + upOrDown;
        int column = posPiece.getColumn() + leftOrRight;

        /* if place to eat - without going out of bounds */
        if ((line + upOrDown) >= 0 && (column + leftOrRight) >= 0) {
            /* if square not empty */
            if (!board.getPiece(line,column).isEmpty()
                    /* if square is opposite color */
                    && (board.getPiece(line,column).getColor() != currentPlayer)
                    /* if square after pion is empty --> then we can eat */
                    && (board.getPiece(line + upOrDown, column + leftOrRight).isEmpty())) {
                posValid.add(new Position(line + upOrDown, column + leftOrRight));
                return true;
            }
        }
        return false;
    }

    private int upOrDown(int line) {
        if (line == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    private int leftOrRight(int column) {
        if (column == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public Piece[][] getBoard() {
        return board.getBoard();
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
