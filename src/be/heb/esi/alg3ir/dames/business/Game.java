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

    private Piece[][] board; //@srv ajouter une classe représentant le damier.
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
        setupBoard();
    }

    private void setupBoard() {
        board = new Piece[10][10];

        setupBlacks();
        setupEmpty();
        setupWhites();
    }

    private void setupBlacks() {
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
    }

    private void setupEmpty() {
        /* Empty squares */
        for (int line = 4; line < 6; line++) {
            for (int column = 0; column < 10; column++) {
                board[line][column] = new Piece();
            }
        }
    }

    private void setupWhites() {
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

        if (board[fromLine][fromColumn].getColor() != currentPlayer) {
            throw new IllegalArgumentException("Bad Color! It's " + currentPlayer + "'s turn!");
        }

        List<Position> listValidPositions;

        if (board[fromLine][fromColumn].getType() == PieceType.PION) {
            listValidPositions = getValidPositionsForPawn(posFrom);
        } else {
            listValidPositions = getValidPositionsForQueen(posFrom);
        }

        Piece pieceToMove = board[fromLine][fromColumn];

        /* Check if a pawn should become a queen */
        if (pieceToMove.getType() == PieceType.PION
                && (toLine == 0 || toLine == 9)) {
            pieceToMove.setType(PieceType.DAME);
        }

        if (canEatAgain) {
            // if we can eat an other pawn, we must change the list of valid 
            // positions because the pawn can eat either going up or going down

            listValidPositions.removeAll(listValidPositions);
            canEatAgain(posFrom, listValidPositions);
        }

        for (Position pos : listValidPositions) {
            if (posTo.equals(pos)) {
                board[fromLine][fromColumn] = new Piece();
                board[toLine][toColumn] = pieceToMove;
                removeEatenPieces(posFrom, posTo);
                if (!canEatAgain) {
                    // MUST IMPLEMENT HERE !
                } else {
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

        final int fromLine = posFrom.getLine();
        final int fromColumn = posFrom.getColumn();
        final int toLine = posTo.getLine();
        final int toColumn = posTo.getColumn();

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

        for (int j = 0; j < nbCasesDeplacees - 1; j++) {
            if (!board[fromLine + upOrDown][fromColumn + leftOrRight].isEmpty()) {
                board[fromLine + upOrDown][fromColumn + leftOrRight] = new Piece();
            }
            upOrDown = upOrDown + upOrDown;
            leftOrRight = leftOrRight + leftOrRight;
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
//@srv bcp trop long, séparer en plusieurs méthodes.
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

        if (board[line][column].getColor() == Color.WHITE) {
            upOrDown = -1;
        } else {
            upOrDown = 1;
        }

        line = line + upOrDown;
        column = column + leftOrRight;

        /* if not on left border */
        if ((column >= 0) && (column <= 9) && (line >= 0) && (line <= 9)) {
            /* if square is empty --> we can go */
            if (board[line][column].isEmpty()) {
                posValid.add(new Position(line, column));
            } else {
                /* if square is opposite color */
                if ((board[line][column].getColor() != currentPlayer)
                        && (column + leftOrRight >= 0) && (column + leftOrRight <= 9)
                        && (line + upOrDown >= 0) && (line + upOrDown <= 9)
                        /* if square after pion is empty --> then we can eat */
                        && (board[line + upOrDown][column + leftOrRight].isEmpty())) {
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
            if (board[line][column].isEmpty()) {
                posValid.add(new Position(line, column));
            } else {
                /* if pawn is opposite color */
                if (board[line][column].getColor() != currentPlayer
                        && line >= 1 && line <= 9
                        && column >= 1 && column <= 9
                        && !canEat
                        /* if square after pawn is empty --> then we can eat */
                        && board[line + upOrDown][column + leftOrRight].isEmpty()) {
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
            if (!board[line][column].isEmpty()
                    /* if square is opposite color */
                    && (board[line][column].getColor() != currentPlayer)
                    /* if square after pion is empty --> then we can eat */
                    && (board[line + upOrDown][column + leftOrRight].isEmpty())) {
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
