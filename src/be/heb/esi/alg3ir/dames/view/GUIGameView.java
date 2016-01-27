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
import be.heb.esi.alg3ir.dames.model.PieceType;
import be.heb.esi.alg3ir.dames.model.Position;
import be.heb.esi.alg3ir.dames.mvc.ObservableGame;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
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
    private List<List<SquareBean>> squaresBoard;
    private List<Position> listPosition;
    private MouseAction mouseAction;
    private Position posPieceToMove;
    private Color currentPlayerFXColor; // JavaFX color corresponding to our Color class    
    
    @Override
    public void update() {
        if (game != null) {
            Piece[][] board = game.getBoard();

            for (int line = 0; line < 10; line++) {
                for (int column = 0; column < 10; column++) {
                    SquareBean square = squaresBoard.get(line).get(column);

                    /* place piece */
                    if (board[line][column] == null) {
                        square.setPiece(null);
                    } else {
                        Piece piece = board[line][column];
                        
                        PieceBean pieceBean = new PieceBean();// = square.getPiece();

                        boolean isDame = (piece.getType() == PieceType.QUEEN);
                        pieceBean.setIsDame(isDame);
                        square.setPiece(pieceBean);
                        
                        Color pieceColor = (piece.getColor() == be.heb.esi.alg3ir.dames.model.Color.BLACK) ?
                                Color.BLACK : Color.WHITE;
                        pieceBean.setColor(pieceColor);
                    }

                    /* set piece highlighting off */
                    currentPlayerFXColor = (game.currentPlayer()
                            == be.heb.esi.alg3ir.dames.model.Color.WHITE)
                                    ? Color.WHITE : Color.BLACK;
                    if (square.hasPiece()) {
                        square.getPiece().setHighlighted(false);
                    }
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
        
        /* Menu item: Save */
        final MenuItem saveItem = new MenuItem("Save");
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                saveGame();
            }
        });

        /* Menu item: Restore */
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
        fileMenu.getItems().add(saveItem);
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Restore confirmation");
        alert.setHeaderText("Restore confirmation");
        alert.setContentText("Are you sure you want to restore a previous game?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            //game.saveGame();
            DBManager damesDb = game.getBD();

            List<Timestamp> allTimeStamps = damesDb.getTimeStampGame();
            List<String> games = new ArrayList<>();

            for (Timestamp ts : allTimeStamps) {
                games.add(ts.toString());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(games.get(0), games);
            dialog.setTitle("Game chooser");
            dialog.setHeaderText("Game chooser");
            dialog.setContentText("Choose a game in the list:");

            Optional<String> gameId = dialog.showAndWait();
            if (gameId.isPresent()) {
                System.out.println("Your choice: " + gameId.get());
                
                int index = 0;
                boolean found = false;
                
                while (!found && index < dialog.getItems().size()) {
                    if (dialog.getItems().get(index).equals(gameId.get())) {
                        found = true;
                    } else {
                        index++;
                    }
                }

                List<Move> moves = damesDb.getMovesOfGame(index);
                newGame();

                for (Move move : moves) {
                    game.movePiece(new Position(move.getFromLine(), move.getFromColumn()),
                            new Position(move.getToLine(), move.getToColumn()));
                }
            }
        } else {
            System.out.println("User cancelled");
        }
    }

    private void saveGame() {
        game.saveGame();
    }

    private void setupBoard() {
                squaresBoard = new ArrayList<>();
        for (int line = 0; line < 10; line++) {
            squaresBoard.add(new ArrayList<>());
            for (int column = 0; column < 10; column++) {
                Color squareColor = ((line + column) % 2 == 0) ? Color.WHEAT : Color.BURLYWOOD;
                SquareBean square = new SquareBean(squareColor);
                square.setOnMousePressed(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {

                        int row = GridPane.getRowIndex(square);
                        int column = GridPane.getColumnIndex(square);

                        if (mouseAction == MouseAction.CLICK1) {
                            if (square.hasPiece()) { // FIXME?
                                posPieceToMove = new Position(row, column);
                                if (currentPlayerFXColor == square.getPiece().getColor()) {
                                    listPosition = game.getBoard()[row][column].getValidPositions(posPieceToMove, game.board(), game.currentPlayer(), game.getCanEatAgain());
                                    if (!listPosition.isEmpty()) {
                                        square.getPiece().setHighlighted(true);
                                        for (Position pos : listPosition) {
                                            SquareBean square = squaresBoard.get(pos.getLine()).get(pos.getColumn());
                                            square.setHighlighted(true);
                                        }
                                        mouseAction = MouseAction.CLICK2;
                                    }
                                }
                            }
                        } else {
                            try {
                                game.movePiece(posPieceToMove, new Position(row, column));
                                for (Position pos : listPosition) {
                                    SquareBean square = squaresBoard.get(pos.getLine()).get(pos.getColumn());
                                    square.setHighlighted(false);
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
