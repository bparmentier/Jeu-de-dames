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
package be.heb.esi.alg3ir.dames.model;

import be.heb.esi.alg3ir.dames.mvc.Observer;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Observable.
 *
 * Class that implements the game.
 */
public class GameImpl implements Game {

    private final Board board;
    private Color currentPlayer;
    private final Color whitePlayer;
    private final Color blackPlayer;
    private boolean canEatAgain;
    private final List<Observer> listeners;

    /**
     * Default Constructor Observable.
     *
     * The white player always starts and starts on the bottom of the board. The
     * black player is always second to play and starts on the top of the board.
     */
    public GameImpl() {

        whitePlayer = Color.WHITE;
        blackPlayer = Color.BLACK;
        currentPlayer = whitePlayer;
        canEatAgain = false;
        board = new Board();
        listeners = new ArrayList<>();
                
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
    @Override
    public void movePiece(Position posFrom, Position posTo) throws IllegalArgumentException {

        final int fromLine = posFrom.getLine();
        final int fromColumn = posFrom.getColumn();
        final int toLine = posTo.getLine();
        final int toColumn = posTo.getColumn();

        if (posFrom.outOfBounds() || posTo.outOfBounds()) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        
        if (board.getPiece(fromLine, fromColumn) == null) {
            throw new IllegalArgumentException("No piece to move here");
        } else if (board.getPiece(fromLine, fromColumn).getColor() != currentPlayer) {
            throw new IllegalArgumentException("Bad Color! It's " + currentPlayer + "'s turn!");
        }

        List<Position> listValidPositions = new ArrayList<>();

        if (canEatAgain) {
            canEatAgain(posFrom, listValidPositions);
        } else {
            listValidPositions = board.getBoard()[fromLine][fromColumn].getValidPositions(posFrom, board, currentPlayer);
        }

        Piece pieceToMove = board.getPiece(fromLine, fromColumn);

        /* Check if a pawn should become a queen */
        if (pieceToMove.getType() == PieceType.PAWN
                && (posTo.getLine() == 0 || posTo.getLine() == 9)) {
            pieceToMove.setType(PieceType.QUEEN);
        }

        for (Position pos : listValidPositions) {
            if (posTo.equals(pos)) {
                board.setPiece(fromLine, fromColumn, null);
                board.setPiece(toLine, toColumn, pieceToMove);
                List<Position> listCanEatAgain = new ArrayList<>();

                if (removeEatenPieces(posFrom, posTo) && canEatAgain(posTo, listCanEatAgain)) {
                    canEatAgain = true;
                } else {
                    canEatAgain = false;
                    alternatePlayer();
                }
            }
        }
        
        System.out.println("fireChange from movePiece");
        fireChange();
    }

    private void alternatePlayer() {
        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }

    private boolean removeEatenPieces(Position posFrom, Position posTo) {

        int fromLine = posFrom.getLine();
        int fromColumn = posFrom.getColumn();
        int toLine = posTo.getLine();
        int toColumn = posTo.getColumn();

        int upOrDown = (fromLine < toLine) ? 1 : -1;
        int leftOrRight = (fromColumn < toColumn) ? 1 : -1;
        int nbCasesDeplacees = abs(toLine - fromLine);

        fromLine = fromLine + upOrDown;
        fromColumn = fromColumn + leftOrRight;

        boolean hasEaten = false;

        for (int j = 0; j < nbCasesDeplacees - 1; j++) {
            if (board.getPiece(fromLine, fromColumn) != null) {
                board.setPiece(fromLine, fromColumn, null);
                hasEaten = true;
            }
            fromLine = fromLine + upOrDown;
            fromColumn = fromColumn + leftOrRight;
        }

        return hasEaten;
    }

    /**
     * isFinished method returns the status ended or not of the game.
     *
     * @return true if the game is finish, else false
     */
    @Override
    public boolean isFinished() {
        // TODO
        return false;
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

        int upOrDown = (lineLimit == 0) ? -1 : 1;
        int leftOrRight = (columnLimit == 0) ? -1 : 1;

        int line = posPiece.getLine() + upOrDown;
        int column = posPiece.getColumn() + leftOrRight;

        /* if place to eat - without going out of bounds */
        if ((line + upOrDown) >= 0 && (column + leftOrRight) >= 0) {
            /* if square not empty */
            if ((board.getPiece(line, column) != null)
                    /* if square is opposite color */
                    && (board.getPiece(line, column).getColor() != currentPlayer)
                    && (line + upOrDown >= 0) && (line + upOrDown <= 9)
                    /* if square after pion is empty --> then we can eat */
                    && (board.getPiece(line + upOrDown, column + leftOrRight) == null)) {
                posValid.add(new Position(line + upOrDown, column + leftOrRight));
                return true;
            }
        }
        return false;
    }

    @Override
    public Piece[][] getBoard() {
        return board.getBoard();
    }

    /**
     * currentPlayer getter returns the current player.
     *
     * @return the current player
     */
    @Override
    public Color currentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public void addListener(Observer gameView) {
        listeners.add(gameView);
        System.out.println("listener added");
        //fireChange();
    }

    @Override
    public void removeListener(Observer gameView) {
        listeners.remove(gameView);
        //fireChange();
    }
    
    private void fireChange() {
        for (Observer view : listeners) {
            System.out.println("fireChange for view " + view.hashCode());
            view.update();
        }
    }
}
