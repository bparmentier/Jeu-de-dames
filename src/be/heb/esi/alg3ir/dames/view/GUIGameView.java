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

import be.heb.esi.alg3ir.dames.model.Game;
import be.heb.esi.alg3ir.dames.mvc.Observer;
import be.heb.esi.alg3ir.dames.model.GameImpl;
import be.heb.esi.alg3ir.dames.model.Piece;
import be.heb.esi.alg3ir.dames.model.Position;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 */
public class GUIGameView extends Application implements Observer {
    private enum MouseAction {
        CLICK1, CLICK2
    }

    private Game game;
    private Group root;
    private GridPane gridPane;
    private List<List<Square>> squaresBoard;
    private MouseAction mouseAction;
    private Position posPieceToMove;
    Color currentPlayerFXColor; // JavaFX color corresponding to our Color class

    @Override
    public void update() {
        System.out.println("update");
        if (game != null) {
            Piece[][] board = game.getBoard();

            for (int line = 0; line < 10; line++) {
                for (int column = 0; column < 10; column++) {
                    Square square = squaresBoard.get(line).get(column);

                    /* place piece */
                    if (board[line][column] == null) {
                        square.setPiece(null, null);
                    } else {
                        Piece piece = board[line][column];
                        square.setPiece(piece.getType(), piece.getColor());
                    }

                    /* set piece highlighting off */
                    currentPlayerFXColor = (game.currentPlayer() ==
                            be.heb.esi.alg3ir.dames.model.Color.WHITE) ?
                            Color.WHITE : Color.BLACK;
                    square.setPieceHighlighting(false);
                }
            }

            System.out.println("updating view");
        }
    }

    @Override
    public void stop() throws Exception {
        if (game != null) {
            game.removeObserver(this);
        }
        super.stop();
    }

    @Override
    public void start(Stage stage) {
        game = new GameImpl();
        mouseAction = MouseAction.CLICK1;
        root = new Group();
        gridPane = new GridPane();
        root.getChildren().add(gridPane);

        drawBoard();

        Scene scene = new Scene(root, 500, 500);

        stage.setScene(scene);
        stage.setTitle("Jeu de dames");
        stage.show();

        game.addObserver(this);

        update();
    }

    private void drawBoard() {
        squaresBoard = new ArrayList<>();
        for (int line = 0; line < 10; line++) {
            squaresBoard.add(new ArrayList<>());
            for (int column = 0; column < 10; column++) {
                Square square = new Square(((line + column) % 2 == 0) ?
                        Color.WHEAT : Color.BURLYWOOD);
                square.setOnMousePressed(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        int row = GridPane.getRowIndex(square);
                        int column = GridPane.getColumnIndex(square);
                        
                        if (mouseAction == MouseAction.CLICK1) {
                            posPieceToMove = new Position(row, column);
                            if (currentPlayerFXColor == square.getColor())
                                square.setPieceHighlighting(true);
                            mouseAction = MouseAction.CLICK2;
                        } else {
                            try {
                                game.movePiece(
                                        posPieceToMove,
                                        new Position(row, column));
                            } catch (IllegalArgumentException e) {
                                System.err.println(e.getMessage());
                            } finally {
                                mouseAction = MouseAction.CLICK1;
                            }
                        }
                    }
                });
                squaresBoard.get(line).add(square);
                gridPane.add(square, column, line);
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
