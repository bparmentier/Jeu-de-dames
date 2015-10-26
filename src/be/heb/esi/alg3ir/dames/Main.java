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
package be.heb.esi.alg3ir.dames;

import be.heb.esi.alg3ir.dames.model.Game;
import be.heb.esi.alg3ir.dames.model.GameImpl;
import be.heb.esi.alg3ir.dames.model.GameView;
import be.heb.esi.alg3ir.dames.view.GUIGameView;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        Game game = new GameImpl();
        
        System.out.println("new GUIGameView");
        GameView guiGameView = new GUIGameView();
        
        System.out.println("gui.setGame(game)");
        guiGameView.setGame(game);

        System.out.println("launch GUIGameView");
        GUIGameView.launch(GUIGameView.class);
    }
}
