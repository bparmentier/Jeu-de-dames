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

import java.util.List;

/**
 * Class Game. Contain all the methods to play a party. `
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public interface Game {

    /**
     * Method to move a piece from a position to another.
     *
     * @param posFrom the source position of the piece
     * @param posTo the destination position of the piece
     *
     * @throws IndexOutOfBoundsException if one of the positions is out of the
     * board
     * @throws IllegalArgumentException if the piece to move does not belong to
     * the currentPlayer
     */
    public void movePiece(Position posFrom, Position posTo);

    /**
     * Method to get the board.
     *
     * @return the game board
     */
    public Board board();

    /**
     * Method to get the board.
     *
     * @return the game board
     */
    public Piece[][] getBoard();

    /**
     * Method to know the state finished or not of the game
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished();

    /**
     * Method to get the color of the current player
     *
     * @return the color of the current player
     */
    public Color currentPlayer();
    
    /**
     * Method to know if a piece ate a piece
     * 
     * @return true if he has eaten a piece, false otherwise
     */
    public boolean getCanEatAgain();
    
    /**
     * Returns the winner if any, null otherwise
     * @return the winner if any, null otherwise
     */
    public Color getWinner();

    /**
     * Returns a list of pieces playable by the current player
     *
     * @return a list of pieces playable by the current player
     */
    public List<Position> getPlayablePieces();
    //public List<Position> getValidMoves(Position piecePosition);
}
