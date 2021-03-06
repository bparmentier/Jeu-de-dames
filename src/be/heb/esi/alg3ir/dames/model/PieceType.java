/*
 * Copyright (C) 2015 bp
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
 * Class enum who represents the type that a pawn can have.
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public enum PieceType {

    /**
     * Represents a pawn
     */
    /**
     * Represents a pawn
     */
    PAWN,
    /**
     * Represents a queen
     */
    QUEEN;

    @Override
    public String toString() {
        String out;
        switch (this) {
            case PAWN:
                out = "PION";
                break;
            case QUEEN:
                out = "DAME";
                break;
            default:
                out = "?";
        }
        return out;
    }
}
