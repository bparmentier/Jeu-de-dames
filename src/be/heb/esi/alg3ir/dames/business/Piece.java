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
 *
 */
public enum Piece {

    EMPTY_SQUARE,
    WHITE_PION,
    BLACK_PION,
    WHITE_DAME,
    BLACK_DAME;

    @Override
    public String toString() {
        String out;
        switch (this) {
            case EMPTY_SQUARE:
                out = " ";
                break;
            case WHITE_PION:
                out = "w";
                break;
            case BLACK_PION:
                out = "b";
                break;
            case WHITE_DAME:
                out = "W";
                break;
            case BLACK_DAME:
                out = "B";
                break;
            default:
                out = "?";
        }
        return out;
    }
}
