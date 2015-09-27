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
package be.heb.esi.alg3ir.dames.business;

/**
 *
 * @author G36546
 */
public class Position {

    private int line;
    private int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
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
