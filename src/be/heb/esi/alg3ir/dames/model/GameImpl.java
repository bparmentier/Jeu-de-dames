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
 * Class that implements Game.
 */
public class GameImpl implements Game {

    private final Board board;
    private Color currentPlayer;
    private final Color whitePlayer;
    private final Color blackPlayer;
    private boolean canEatAgain;
    private final List<Observer> observers;

    /**
     * Default constructor
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
        observers = new ArrayList<>();

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
        if (!board.isValidPosition(posFrom) || !board.isValidPosition(posTo)) {
            throw new IllegalArgumentException("Invalid position");
        }

        if (board.getPiece(posFrom) == null) {
            throw new IllegalArgumentException("No piece to move here");
        } else if (board.getPiece(posFrom).getColor() != currentPlayer) {
            throw new IllegalArgumentException("Bad Color! It's " + currentPlayer + "'s turn!");
        }

        List<Position> listValidPositions = new ArrayList<>();

        if (canEatAgain) {
            board.getPiece(posFrom).canEatAgain(posFrom, listValidPositions, board, currentPlayer);
        } else {
            listValidPositions = board.getPiece(posFrom).getValidPositions(posFrom, board, currentPlayer);
        }

        for (Position pos : listValidPositions) {
            if (posTo.equals(pos)) {

                Piece pieceToMove = board.getPiece(posFrom);

                /* Check if a pawn should become a queen */
                if (board.getPiece(posFrom).getType() == PieceType.PAWN
                        && (posTo.getLine() == 0 || posTo.getLine() == 9)) {
                    pieceToMove = new Queen(pieceToMove.getColor());
                }

                List<Position> listCanEatAgain = new ArrayList<>();

                if (removeEatenPieces(posFrom, posTo) && board.getPiece(posFrom).canEatAgain(posTo, listCanEatAgain, board, currentPlayer)) {
                    canEatAgain = true;
                } else {
                    canEatAgain = false;
                    alternatePlayer();
                }
                
                board.setPiece(null, posFrom);
                board.setPiece(pieceToMove, posTo);

            }
        }

        notifyChange();
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
                board.setPiece(null, fromLine, fromColumn);
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
    public void addObserver(Observer observer) {
        observers.add(observer);
        notifyChange();
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        notifyChange();
    }

    private void notifyChange() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
