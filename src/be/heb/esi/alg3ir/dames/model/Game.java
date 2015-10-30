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

import be.heb.esi.alg3ir.dames.mvc.Observable;
import java.util.List;

/**
 *
 */
public interface Game extends Observable {
    public void movePiece(Position posFrom, Position posTo);
    public Piece[][] getBoard();
    public boolean isFinished();
    public Color currentPlayer();
    // TODO
    //public List<Position> getPlayablePieces();
    //public List<Position> getValidMoves(Position piecePosition);
}
