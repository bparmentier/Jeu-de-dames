/*
 * Copyright (C) 2015 gbps2
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }
    
    @Override
    public List<Position> getValidPositions(Position posPieceToMove, Board board, Color currentPlayer) {

        List<Position> listPosition = new ArrayList<>();
        
        updateListPositionsForPawn(posPieceToMove, board, listPosition, 0, currentPlayer); //LEFT
        updateListPositionsForPawn(posPieceToMove, board, listPosition, 9, currentPlayer); //RIGHT

        return listPosition;
    }

    private static void updateListPositionsForPawn(Position posPiece, Board board, 
            List<Position> posValid, int columnLimit, Color currentPlayer) {

        int line = posPiece.getLine();
        int column = posPiece.getColumn();
        
        int upOrDown = (board.getPiece(line, column).getColor() == Color.WHITE) ? -1 : 1;
        int leftOrRight = (columnLimit == 0) ? -1 : 1;

        line = line + upOrDown;
        column = column + leftOrRight;

        /* if not on left border */
        if ((column >= 0) && (column <= 9) && (line >= 0) && (line <= 9)) {
            /* if square is empty --> we can go */
            if (board.getPiece(line, column) == null) {
                posValid.add(new Position(line, column));
            } else {
                /* if square is opposite color */
                if ((board.getPiece(line, column).getColor() != currentPlayer)
                        && (column + leftOrRight >= 0) && (column + leftOrRight <= 9)
                        && (line + upOrDown >= 0) && (line + upOrDown <= 9)
                        /* if square after pion is empty --> then we can eat */
                        && (board.getPiece(line + upOrDown, column + leftOrRight) == null)) {
                    posValid.add(new Position(line + upOrDown, column + leftOrRight));
                }
            }
        }
    }
}
