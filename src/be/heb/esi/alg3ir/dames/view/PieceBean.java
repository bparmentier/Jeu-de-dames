/*
 * Copyright (C) 2016 bp
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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author bp
 */
public class PieceBean extends Parent {
    
    private final Circle bigCircle;
    private final Circle smallCircle;
    private Color pieceColor;
    private Color strokeColor;
    
    private BooleanProperty isDame = new SimpleBooleanProperty(false);
    private ObjectProperty<Color> color = new ObjectPropertyBase<Color>() {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "Color property";
        }
    };

    public PieceBean() {
        bigCircle = new Circle(20);
        smallCircle = new Circle(10);
        getChildren().addAll(bigCircle, smallCircle);
    }
    
    public final void setIsDame(boolean isDame) {
        this.isDame.set(isDame);
        smallCircle.setVisible(isDame);
    }
    
    public final boolean isDame() {
        return isDame.get();
    }
    
    public final BooleanProperty isDameProperty() {
        return isDame;
    }
    
    public final void setColor(Color color) {
        this.color.set(color);
        if (color != null) {
            pieceColor = color;
            strokeColor = (color == Color.BLACK)
                    ? Color.WHITE
                    : Color.BLACK;
            
            bigCircle.setFill(pieceColor);
            bigCircle.setStroke(strokeColor);

            if (isDame()) {
                smallCircle.setFill(pieceColor);
                smallCircle.setStroke(strokeColor);
            }
        }
    }
    
    public void setHighlighted(boolean highlight) {
        if (highlight) {
            bigCircle.setStyle("-fx-effect: dropshadow(three-pass-box, "
                    + "rgba(0,0,0,0.8), 10, 0, 0, 0);");
        } else {
            bigCircle.setStyle("");
        }
    }
    
    public final Color getColor() {
        return color.get();
    }
    
    public final ObjectProperty<Color> colorProperty() {
        return color;
    }
}
