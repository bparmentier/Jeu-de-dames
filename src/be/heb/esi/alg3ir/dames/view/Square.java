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
package be.heb.esi.alg3ir.dames.view;

import be.heb.esi.alg3ir.dames.model.PieceType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A square of the board
 * Can be empty or containing a piece (pawn/queen).
 * 
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class Square extends StackPane {
    
    private final Circle bigCircle;
    private final Circle smallCircle;
    private Color pieceColor;
    
    /**
     * Constructs a Square with the given background color
     * @param backgroundColor the background color
     */
    public Square(Color backgroundColor) {
        setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        setPrefSize(50, 50);
        bigCircle = new Circle();
        bigCircle.setRadius(20);
        smallCircle = new Circle();
        smallCircle.setRadius(10);
    }
    
    /**
     * Set a Piece of the given type and color on the Square
     * If one of the parameters is null, the piece is removed.
     * @param type the type of the Piece
     * @param color the color of the Piece
     */
    public void setPiece(PieceType type, be.heb.esi.alg3ir.dames.model.Color color) {
        getChildren().clear();
        if (type != null && color != null) {
            pieceColor = (color == be.heb.esi.alg3ir.dames.model.Color.BLACK) ?
                    Color.BLACK : Color.WHITE;
            getChildren().add(bigCircle);
            bigCircle.setFill(pieceColor);
            bigCircle.setStroke((pieceColor == Color.BLACK) ?
                    Color.WHITE : Color.BLACK);

            if (type == PieceType.QUEEN) {
                getChildren().add(smallCircle);
                smallCircle.setFill(pieceColor);
                smallCircle.setStroke((pieceColor == Color.BLACK) ?
                    Color.WHITE : Color.BLACK);
            }
        }
    }
    
    /**
     * Highlight the Piece on the Square
     * @param highlight if the Piece should be highlighted or not
     */
    public void setPieceHighlighting(boolean highlight) {
        if (highlight) {
            bigCircle.setStyle("-fx-effect: dropshadow(three-pass-box, "
                    + "rgba(0,0,0,0.8), 10, 0, 0, 0);");
        } else {
            bigCircle.setStyle("");
        }
    }
    
    /**
     * Highlight the Square
     * @param highlight if the Square should be highlighted or not
     */
    void setSquareHighligthing(boolean highlight) {
        if (highlight) {
            this.setStyle("-fx-effect: dropshadow(three-pass-box, "
                    + "rgba(0,0,0,0.8), 10, 0, 0, 0);");
        } else {
            this.setStyle("");
        }    
    }

    /**
     * Returns the color of the Piece
     * @return the color of the Piece
     */
    Color getColor() {
        return pieceColor;
    }

    
}
