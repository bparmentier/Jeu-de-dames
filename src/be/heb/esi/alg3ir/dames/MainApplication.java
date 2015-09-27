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

import be.heb.esi.alg3ir.dames.business.Game;
import be.heb.esi.alg3ir.dames.business.Position;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class MainApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        Scanner keyboard = new Scanner(System.in);
        int fromLine, fromColumn, toLine, toColumn;
        game.start();
        
        while (!game.isFinished()) {
            System.out.println(game);

            System.out.println(game.currentPlayer() + " plays");
            System.out.print("Move piece (line): ");
            fromLine = keyboard.nextInt();
            System.out.print("Move piece (column): ");
            fromColumn = keyboard.nextInt();
            
            Position posFrom = new Position(fromLine, fromColumn);
            
            List<Position> listPos = game.getValidPositions(posFrom);
            for (int i = 0; i < listPos.size(); i++) {
                System.out.println("line = " + listPos.get(i).getLine() + "   -    column = " + listPos.get(i).getColumn());
            }
            
            System.out.print("Where? (line): ");
            toLine = keyboard.nextInt();
            System.out.print("Where? (column): ");
            toColumn = keyboard.nextInt();
            
            Position posTo = new Position(toLine,toColumn);
            
            game.movePiece(posFrom, posTo);
            
        }
    }
    
}
