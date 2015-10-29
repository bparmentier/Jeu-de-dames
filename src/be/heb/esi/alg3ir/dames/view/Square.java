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
 *
 */
public class Square extends StackPane {
    private final Circle bigCircle;
    private final Circle smallCircle;

    public Square(Color color) {
        setBackground(new Background(new BackgroundFill(color, null, null)));
        setPrefSize(50, 50);
        bigCircle = new Circle();
        bigCircle.setRadius(20);
        smallCircle = new Circle();
        smallCircle.setRadius(10);
    }
    
    public void setPiece(PieceType type, be.heb.esi.alg3ir.dames.model.Color color) {
        getChildren().clear();
        if (type != null && color != null) {
            getChildren().add(bigCircle);
            bigCircle.setFill((color == be.heb.esi.alg3ir.dames.model.Color.BLACK)
                    ? Color.BLACK : Color.WHITE);
            bigCircle.setStroke((color == be.heb.esi.alg3ir.dames.model.Color.BLACK)
                    ? Color.WHITE : Color.BLACK);

            if (type == PieceType.QUEEN) {
                getChildren().add(smallCircle);
                smallCircle.setFill((color == be.heb.esi.alg3ir.dames.model.Color.BLACK)
                        ? Color.BLACK : Color.WHITE);
                smallCircle.setStroke((color == be.heb.esi.alg3ir.dames.model.Color.BLACK)
                        ? Color.WHITE : Color.BLACK);
            }
        }
    }
}
