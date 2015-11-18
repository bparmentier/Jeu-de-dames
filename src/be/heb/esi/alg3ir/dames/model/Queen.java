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
 * Class Queen. Implements a queen, this class extends the Piece class
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class Queen extends Piece {

    /**
     * Constructor of Queen. Create a new queen
     *
     * @param color the color of the queen
     */
    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public List<Position> getValidPositions(Position posPieceToMove, Board board, Color currentPlayer, boolean canEatAgain) {
        List<Position> listPosition = new ArrayList<>();

        updateListPositionsQueens(posPieceToMove, board, listPosition, 0, 0, currentPlayer); //TOP LEFT       
        updateListPositionsQueens(posPieceToMove, board, listPosition, 0, 9, currentPlayer); //TOP RIGHT
        updateListPositionsQueens(posPieceToMove, board, listPosition, 9, 0, currentPlayer); //BOTTOM LEFT
        updateListPositionsQueens(posPieceToMove, board, listPosition, 9, 9, currentPlayer); //BOTTOM RIGHT

        return listPosition;
    }

    private void updateListPositionsQueens(Position posPiece, Board board, List<Position> posValid, int lineLimit, int columnLimit, Color currentPlayer) {

        int upOrDown = (lineLimit == 0) ? -1 : 1;
        int leftOrRight = (columnLimit == 0) ? -1 : 1;

        int line = posPiece.getLine() + upOrDown;
        int column = posPiece.getColumn() + leftOrRight;

        boolean canEat = false;
        boolean canGoFurther = true;

        while ((line >= 0) && (line <= 9) && (column >= 0) && (column <= 9) && (canGoFurther)) {
            /* if case is empty --> we can go */
            if (board.getPiece(line, column) == null) {
                posValid.add(new Position(line, column));
            } else {
                /* if pawn is opposite color */
                if (board.getPiece(line, column).getColor() != currentPlayer
                        && (line >= 1) && (line <= 9)
                        && (column >= 1) && (column <= 9)
                        && !canEat
                        /* if square after pawn is empty --> then we can eat */
                        && board.getPiece(line + upOrDown, column + leftOrRight) == null) {
                    canEat = true;
                    line = line + upOrDown;
                    column = column + leftOrRight;
                    posValid.add(new Position(line, column));
                } else {
                    canGoFurther = false;
                }
            }
            line = line + upOrDown;
            column = column + leftOrRight;
        }
    }

    @Override
    public boolean canEatAgain(Position posPiece, List<Position> posValid, Board board, Color currentPlayer) {
        return updateListCanEatAgain(posPiece, posValid, 0, 0, board, currentPlayer) // TOP LEFT
                || updateListCanEatAgain(posPiece, posValid, 0, 9, board, currentPlayer) // TOP RIGHT
                || updateListCanEatAgain(posPiece, posValid, 9, 0, board, currentPlayer) // BOTTOM LEFT
                || updateListCanEatAgain(posPiece, posValid, 9, 9, board, currentPlayer); // BOTTOM RIGHT
    }

    private boolean updateListCanEatAgain(Position posPiece, List<Position> posValid, int lineLimit, int columnLimit, Board board, Color currentPlayer) {

        int upOrDown = (lineLimit == 0) ? -1 : 1;
        int leftOrRight = (columnLimit == 0) ? -1 : 1;

        int line = posPiece.getLine() + upOrDown;
        int column = posPiece.getColumn() + leftOrRight;

        boolean canEat = false;
        boolean canGoFurther = true;

        while ((line >= 0) && (line <= 9) && (column >= 0) && (column <= 9) && (canGoFurther)) {
            /* if case is empty --> we can go */
            if (board.getPiece(line, column) == null) {
                posValid.add(new Position(line, column));
            } else {
                /* if pawn is opposite color */
                if (board.getPiece(line, column).getColor() != currentPlayer
                        && (line >= 1) && (line <= 9)
                        && (column >= 1) && (column <= 9)
                        && !canEat
                        /* if square after pawn is empty --> then we can eat */
                        && board.getPiece(line + upOrDown, column + leftOrRight) == null) {
                    canEat = true;
                    line = line + upOrDown;
                    column = column + leftOrRight;
                    posValid.add(new Position(line, column));
                } else {
                    canGoFurther = false;
                }
            }
            line = line + upOrDown;
            column = column + leftOrRight;
        }
        return canEat;
    }
}
