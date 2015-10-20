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

import java.util.List;

/**
 * Class Piece represents a piece of the game
 * 
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public abstract class Piece {
    
    private final Color color;
    protected PieceType type;

    /**
     * Constructor Piece
     * 
     * @param color the color of the piece.
     * @param type the type of the piece.
     * @throws IllegalArgumentException if color is NO_COLOR and type is not EMPTY
     *                              or if type is EMPTY and color is not NO_COLOR
     */
    public Piece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    /**
     * getColor method returns the color of the piece.
     * 
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * getType method returns the type of the piece.
     * 
     * @return the type of the piece.
     */
    public PieceType getType() {
        return type;
    }

    /**
     * setType method. Can set the type of the piece.
     * 
     * @param type the new type for the piece.
     * @throws IllegalArgumentException if we change the color of an empty piece
     *                              or if we change the type of a NO_COLOR piece.
     */
    public void setType(PieceType type) {
        this.type = type;
    }
    
    public abstract List<Position> getValidPositions(Position posPieceToMove, Board board, Color currentPlayer);
    
    @Override
    public String toString() {
        String out;
        switch (color) {
            case WHITE:
                out = (type == PieceType.PAWN) ? "w" : "W";
                break;
            case BLACK:
                out = (type == PieceType.PAWN) ? "b" : "B";
                break;
            default:
                out = "?";
        }
        return out;
    }
}
