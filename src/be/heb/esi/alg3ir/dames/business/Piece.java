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

/**
 * Class Piece represents a piece of the game
 * 
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class Piece {
    
    private Color color;
    private PieceType type;

    /**
     * Default Constructor Piece. A piece is, by default, empty and without any color.
     */
    public Piece() {
        this(Color.NO_COLOR, PieceType.EMPTY);
    }

    /**
     * Constructor Piece
     * 
     * @param color the color of the piece.
     * @param type the type of the piece.
     */
    public Piece(Color color, PieceType type) {
        if ((color == Color.NO_COLOR && type != PieceType.EMPTY)
                || (type == PieceType.EMPTY && color != Color.NO_COLOR)) {
            throw new IllegalArgumentException("Cannot create a piece of type "
                    + type + " and color " + color);
        }
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
        if ((type == PieceType.EMPTY && this.color != Color.NO_COLOR)
                || (this.color == Color.NO_COLOR && type != PieceType.EMPTY)) {
            throw new IllegalArgumentException("Cannot set type " + type
                    + " to piece of color " + this.color);
        }
        this.type = type;
    }
    
    /**
     * isEmpty method returns if the piece is empty or not
     * 
     * @return true if the piece is empty and has no color, else false 
     */
    public boolean isEmpty() {
        return color == Color.NO_COLOR && type == PieceType.EMPTY;
    }
    
    @Override
    public String toString() {
        String out;
        switch (color) {
            case NO_COLOR:
                out = " ";
                break;
            case WHITE:
                out = (type == PieceType.PION) ? "w" : "W";
                break;
            case BLACK:
                out = (type == PieceType.PION) ? "b" : "B";
                break;
            default:
                out = "?";
        }
        return out;
    }
}
