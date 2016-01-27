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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * A square of the board Can be empty or containing a piece (pawn/queen).
 */
public class SquareBean extends StackPane {

    private PieceBean piece;
    
    private BooleanProperty isHighlighted = new SimpleBooleanProperty(false);

    /**
     * Constructs a Square with the given background color
     *
     * @param backgroundColor the background color
     */
    public SquareBean(Color backgroundColor) {
        setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        setPrefSize(50, 50);
        setMinSize(50, 50);
    }

    public void setPiece(PieceBean piece) {
        this.piece = piece;
        getChildren().clear();
        if (piece != null) {
            getChildren().add(piece);
        }
    }

    public boolean hasPiece() {
        return piece != null;
    }
    
    public PieceBean getPiece() {
        return piece;
    }
    
    public final void setHighlighted(boolean highlight){
        isHighlighted.set(highlight);
        if (highlight) {
            this.setStyle("-fx-background-color: #acdd87;"); // kind of green
        } else {
            this.setStyle("");
        }
    }
     
    public final boolean isHighlighted(){
        return isHighlighted.get();
    }
     
    public final BooleanProperty highlightedProperty(){
        return isHighlighted;
    }
}
