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

import be.heb.esi.alg3ir.dames.db.DBManager;
import be.heb.esi.alg3ir.dames.model.Move;
import be.heb.esi.alg3ir.dames.mvc.Observer;
import be.heb.esi.alg3ir.dames.model.Piece;
import be.heb.esi.alg3ir.dames.model.Position;
import be.heb.esi.alg3ir.dames.mvc.ObservableGame;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Class GUIGameView. Implements the view of the game
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class GUIGameView extends Application implements Observer {

    private enum MouseAction {

        CLICK1, CLICK2
    }

    private ObservableGame game;
    private BorderPane mainLayout;
    private HBox menuLayout;
    private GridPane gridPane;
    private HBox statusBarLayout;
    private Label statusText;
    private List<List<Square>> squaresBoard;
    private List<Position> listPosition;
    private MouseAction mouseAction;
    private Position posPieceToMove;
    Color currentPlayerFXColor; // JavaFX color corresponding to our Color class

    @Override
    public void update() {
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
                    currentPlayerFXColor = (game.currentPlayer()
                            == be.heb.esi.alg3ir.dames.model.Color.WHITE)
                                    ? Color.WHITE : Color.BLACK;
                    square.setPieceHighlighting(false);
                }
            }

            if (game.isFinished()) {
                statusText.setText("Congratulations! " + game.getWinner() + " player wins!");
            } else {
                statusText.setText(game.currentPlayer().toString() + " player's turn");
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        if (game != null) {
            game.removeObserver(this);
        }
        super.stop();
    }

    /**
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws SQLException {

        game = new ObservableGame();

        mouseAction = MouseAction.CLICK1;

        mainLayout = new BorderPane();
        menuLayout = new HBox();
        gridPane = new GridPane();
        statusBarLayout = new HBox();

        setupMenuBar(stage);
        setupBoard();
        setupStatusBar();

        mainLayout.setTop(menuLayout);
        mainLayout.setCenter(gridPane);
        mainLayout.setBottom(statusBarLayout);

        Scene scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Jeu de dames");
        stage.show();

        game.addObserver(this);

        update();
    }

    private void setupMenuBar(Stage stage) {
        final MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        /* Menu: File */
        final Menu fileMenu = new Menu("File");

        /* Menu item: New */
        final MenuItem newItem = new MenuItem("New");
        newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                newGame();
            }
        });

        /* Menu item: New */
        final MenuItem restoreItem = new MenuItem("Restore");
        restoreItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        restoreItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                restoreGame();
            }
        });

        /* Menu item: Exit */
        final MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage.close();
            }
        });

        fileMenu.getItems().add(newItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(restoreItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);
        menuLayout.getChildren().add(menuBar);
    }

    private void newGame() {
        if (game != null) {
            game.removeObserver(GUIGameView.this);
        }
        game = new ObservableGame();
        game.addObserver(this);
    }

    private void restoreGame() {
        DBManager bdDames = game.getBD();

        JComboBox games = new JComboBox();

        List<Timestamp> allTimeStamps = new ArrayList<>();
        allTimeStamps = bdDames.getTimeStampGame();

        for (int i = 0; i < allTimeStamps.size(); i++) {
            games.addItem(allTimeStamps.get(i).toString());
        }

        final JComponent[] inputs = new JComponent[]{
            new JLabel("Select the game you want to continue : "),
            games
        };
        JOptionPane.showMessageDialog(null, inputs, "Restore a game", JOptionPane.PLAIN_MESSAGE);
        
        List<Move> moves = bdDames.getMovesOfGame(games.getSelectedIndex());
        newGame();
        
        for (Move move : moves) {
            game.movePiece(new Position(move.getFromLine(),move.getFromColumn()), 
                    new Position(move.getToLine(),move.getToColumn()));
        }
        
    }

    private void setupBoard() {
        squaresBoard = new ArrayList<>();

        for (int line = 0; line < 10; line++) {
            squaresBoard.add(new ArrayList<>());
            for (int column = 0; column < 10; column++) {
                Square square = new Square(((line + column) % 2 == 0)
                        ? Color.WHEAT : Color.BURLYWOOD);
                square.setOnMousePressed(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {

                        int row = GridPane.getRowIndex(square);
                        int column = GridPane.getColumnIndex(square);

                        if (mouseAction == MouseAction.CLICK1) {
                            posPieceToMove = new Position(row, column);
                            if (currentPlayerFXColor == square.getColor()) {
                                listPosition = game.getBoard()[row][column].getValidPositions(posPieceToMove, game.board(), game.currentPlayer(), game.getCanEatAgain());
                                if (!listPosition.isEmpty()) {
                                    square.setPieceHighlighting(true);
                                    for (Position pos : listPosition) {
                                        squaresBoard.get(pos.getLine()).get(pos.getColumn()).setSquareHighligthing(true);
                                    }
                                    mouseAction = MouseAction.CLICK2;
                                }
                            }
                        } else {
                            try {
                                game.movePiece(posPieceToMove, new Position(row, column));
                                for (Position pos : listPosition) {
                                    squaresBoard.get(pos.getLine()).get(pos.getColumn()).setSquareHighligthing(false);
                                }
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

        /* Set size constraints on grid cells */
        ColumnConstraints cc = new ColumnConstraints();
        RowConstraints rc = new RowConstraints();
        cc.setPercentWidth(10);
        rc.setPercentHeight(10);
        for (int i = 0; i < 10; i++) {
            gridPane.getColumnConstraints().add(cc);
            gridPane.getRowConstraints().add(rc);
        }
    }

    private void setupStatusBar() {
        statusText = new Label();
        statusBarLayout.getChildren().add(statusText);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
