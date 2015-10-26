/*
 * Copyright (C) 2015 G36546
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

/**
 * Class Position
 * 
 * Class who represents the position in the board
 */
public class Position {

    private final int line;
    private final int column;

    /**
     * Constructor of Position
     * 
     * @param line the line in the board
     * @param column the column in the board
     */
    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * getLine method returns the line of the position
     * 
     * @return the line of the position
     */
    public int getLine() {
        return line;
    }

    /**
     * getColumn method returns the column of the position
     * 
     * @return the column of the position
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * outOfBounds method 
     * 
     * @return true if the line or the column is out of bounds, false otherwise
     */  
    public boolean outOfBounds () {
        return (line < 0)
                || (line > 9)
                || (column < 0)
                || (column > 9);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && (obj.getClass().equals(this.getClass()))) {
            Position pos = (Position) obj;
            return (this.line == pos.getLine() && this.column == pos.getColumn());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.line;
        hash = 47 * hash + this.column;
        return hash;
    }
}
