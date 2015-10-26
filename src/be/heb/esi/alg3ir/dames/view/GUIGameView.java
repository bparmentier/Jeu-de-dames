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

import be.heb.esi.alg3ir.dames.model.GameView;
import be.heb.esi.alg3ir.dames.model.Game;
import be.heb.esi.alg3ir.dames.model.Piece;
import be.heb.esi.alg3ir.dames.model.Position;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 */
public class GUIGameView extends Application implements GameView {

    private Game game;
    private Group root;
    private GridPane gridPane;

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        System.out.println("setGame");
        removeGameListener();
        this.game = game;
        addGameListener();
        //update();
    }

    private void removeGameListener() {
        if (game != null) {
            System.out.println("removing listener");
            game.removeListener(this);
        }
    }

    private void addGameListener() {
        System.out.println("adding listener");
        this.game.addListener(this);
    }

    @Override
    public void update() {
        // Interrogation du modele afin d'obtenir sont etat
        // ... modele ...
        // Mise à jour de la vue sur base de l'etat du modele
        System.out.println("update");
        if (game != null) {
            Piece[][] board = game.getBoard();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j].getColor() == be.heb.esi.alg3ir.dames.model.Color.BLACK) {
                        Rectangle rect = new Rectangle();
                        rect.setFill(Color.BLUE);
                        gridPane.add(rect, i, j);
                    }
                }
            }

            System.err.println("updating view");

        }
    }

    @Override
    public void start(Stage stage) {
        root = new Group();
        gridPane = new GridPane();
        root.getChildren().add(gridPane);
        
        System.out.println("drawing board");
        drawBoard();

        Scene scene = new Scene(root, 500, 500);

        stage.setScene(scene);
        stage.setTitle("Jeu de dames");
        stage.show();

        System.out.println("starting view");

        update();
    }

    private void drawBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // TODO: use something else than Rectangle
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(((i + j) % 2 == 0) ? Color.BLANCHEDALMOND : Color.GOLDENROD);
                rectangle.setWidth(50);
                rectangle.setHeight(50);
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        int row = GridPane.getRowIndex(rectangle);
                        int column = GridPane.getColumnIndex(rectangle);
                        System.out.println("Hello from" + row + " " + column);
                        
                        // TODO: handle from/to click
                        game.movePiece(new Position(row, column), new Position(row - 1, column - 1));

                    }
                });
                gridPane.add(rectangle, i, j);
            }
        }
    }
}
