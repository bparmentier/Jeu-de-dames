/*
 * Copyright (C) 2015 bp
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
package be.heb.esi.alg3ir.dames.mvc;

import be.heb.esi.alg3ir.dames.db.BDManager;
import be.heb.esi.alg3ir.dames.model.Board;
import be.heb.esi.alg3ir.dames.model.Color;
import be.heb.esi.alg3ir.dames.model.Game;
import be.heb.esi.alg3ir.dames.model.GameImpl;
import be.heb.esi.alg3ir.dames.model.Piece;
import be.heb.esi.alg3ir.dames.model.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bp
 */
public class ObservableGame implements Observable, Game {

    private final GameImpl gameImpl;
    private final List<Observer> observers;

    public ObservableGame() {
        observers = new ArrayList<>();
        gameImpl = new GameImpl();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        notifyChange();
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        notifyChange();
    }

    private void notifyChange() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void movePiece(Position posFrom, Position posTo) {
        gameImpl.movePiece(posFrom, posTo);
        notifyChange();
    }

    @Override
    public Board board() {
        return gameImpl.board();
    }

    @Override
    public Piece[][] getBoard() {
        return gameImpl.getBoard();
    }

    @Override
    public boolean isFinished() {
        return gameImpl.isFinished();
    }

    @Override
    public Color currentPlayer() {
        return gameImpl.currentPlayer();
    }

    @Override
    public boolean getCanEatAgain() {
        return gameImpl.getCanEatAgain();
    }

    @Override
    public Color getWinner() {
        return gameImpl.getWinner();
    }

    @Override
    public List<Position> getPlayablePieces() {
        return gameImpl.getPlayablePieces();
    }
    
    @Override
    public BDManager getBD() {
       return gameImpl.getBD();
    }
}
