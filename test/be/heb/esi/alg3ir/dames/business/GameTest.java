/*
 * Copyright (C) 2015 gbps2
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
package be.heb.esi.alg3ir.dames.business;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gbps2
 */
public class GameTest {

    /**
     * Test of movePiece method, of class Game. Move piece to top left in empty
     * square
     */
    @Test
    public void testMovePiece1() {
        System.out.println("movePiece : to top left in empty square");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        
        assertTrue(instance.getBoard()[6][3].isEmpty());
        
        assertEquals(Color.WHITE, instance.getBoard()[5][2].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][2].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to top right in empty
     * square
     */
    @Test
    public void testMovePiece2() {
        System.out.println("movePiece : to top right in empty square");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,4);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        
        assertTrue(instance.getBoard()[6][3].isEmpty());
        
        assertEquals(Color.WHITE, instance.getBoard()[5][4].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][4].getType());
    }
    /**
     * Test of movePiece method, of class Game. Move piece to bottom left in empty
     * square
     */
    @Test
    public void testMovePiece3() {
        System.out.println("movePiece : to bottom left in empty square");
        
        Position posFrom = new Position(3,2);
        Position posTo = new Position (4,1);
        Game instance = new Game();
        instance.movePiece(new Position(6,3),new Position (5,4)); // WHITE PLAYER MUST START
        instance.movePiece(posFrom, posTo);
        
        assertTrue(instance.getBoard()[3][2].isEmpty());
        
        assertEquals(Color.BLACK, instance.getBoard()[4][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][1].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to bottom right in empty
     * square
     */
    @Test
    public void testMovePiece4() {
        System.out.println("movePiece : to bottom right in empty square");
        
        Position posFrom = new Position(3,2);
        Position posTo = new Position (4,3);
        Game instance = new Game();
        instance.movePiece(new Position(6,3),new Position (5,4)); // WHITE PLAYER MUST START
        instance.movePiece(posFrom, posTo);
        
        assertTrue(instance.getBoard()[3][2].isEmpty());

        assertEquals(Color.BLACK, instance.getBoard()[4][3].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][3].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to top left on border
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMovePiece5() {
        System.out.println("movePiece : to top left on border - out of bounds");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        instance.movePiece(new Position(3,2),new Position (4,1)); // BLACK PLAYER MUST PLAY THE SECOND TURN
        instance.movePiece(posTo, new Position(4,-1));
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to top right on border
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMovePiece6() {
        System.out.println("movePiece : to top right on border - out of bounds");
        
        Position posFrom = new Position(6,9);
        Position posTo = new Position (5,10);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to bottom left on border
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMovePiece7() {
        System.out.println("movePiece : to bottom left on border - out of bounds");
        
        Position posFrom = new Position(3,0);
        Position posTo = new Position (4,-1);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to bottom right on border
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMovePiece8() {
        System.out.println("movePiece : to bottom right on border - out of bounds");
        
        Position posFrom = new Position(3,8);
        Position posTo = new Position (4,9);
        Game instance = new Game();
        instance.movePiece(new Position(6,3),new Position (5,4)); // WHITE PLAYER MUST START
        instance.movePiece(posFrom, posTo);
        instance.movePiece(new Position(6,5),new Position (5,6)); // WHITE PLAYER MUST START
        instance.movePiece(posTo, new Position(5,10));
    }
    
    /**
     * Test of movePiece method, of class Game. Move black pion first turn
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMovePiece9() {
        System.out.println("movePiece : Move white pion first turn then again white pion");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,4);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        instance.movePiece(posTo, new Position(4,3));
    }
    
    /**
     * Test of movePiece method, of class Game. Move black pion first turn
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMovePiece10() {
        System.out.println("movePiece : Move black pion first turn");
        
        Position posFrom = new Position(3,8);
        Position posTo = new Position (4,9);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
    }
    
    /**
     * Test of movePiece method, of class Game. Move pion to an invalid empty square
     */
    @Test
    public void testMovePiece11() {
        System.out.println("movePiece : Move pion to an invalid empty square");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (4,9);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        
        assertEquals(Color.WHITE, instance.getBoard()[6][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[6][1].getType());
        
        assertTrue(instance.getBoard()[4][9].isEmpty());

    }
    
    /**
     * Test of movePiece method, of class Game. Move white pion to a black square
     */
    @Test
    public void testMovePiece12() {
        System.out.println("movePiece : Move white pion to a black square");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        instance.movePiece(new Position(3,0), new Position(4,1)); // BLACK MUST PLAY
        instance.movePiece(posTo, new Position(4,1));

        assertEquals(Color.WHITE, instance.getBoard()[5][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][0].getType());
        assertEquals(Color.BLACK, instance.getBoard()[4][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][1].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move black pion to a white square
     */
    @Test
    public void testMovePiece13() {
        System.out.println("movePiece : Move black pion to a white square");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); // WHITE MUST START 
        instance.movePiece(new Position(3,0), new Position(4,1)); // BLACK MUST PLAY
        instance.movePiece(new Position(6,3), new Position(5,4));
        instance.movePiece(new Position(4,1), posTo);

        assertEquals(Color.WHITE, instance.getBoard()[5][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][0].getType());
        assertEquals(Color.BLACK, instance.getBoard()[4][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][1].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move white pion to a white square
     */
    @Test
    public void testMovePiece14() {
        System.out.println("movePiece : Move white pion to a white square");
        
        Position posFrom = new Position(7,0);
        Position posTo = new Position (6,1);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 

        assertEquals(Color.WHITE, instance.getBoard()[7][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[7][0].getType());
        assertEquals(Color.WHITE, instance.getBoard()[6][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[6][1].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move black pion to a black square
     */
    @Test
    public void testMovePiece15() {
        System.out.println("movePiece : Move black pion to a black square");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); // WHITE MUST START
        instance.movePiece(new Position(2,1), new Position(3,0));

        assertEquals(Color.BLACK, instance.getBoard()[2][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[2][1].getType());
        assertEquals(Color.BLACK, instance.getBoard()[3][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[3][0].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Eat a black pawn
     */
    @Test
    public void testMovePiece16() {
        System.out.println("movePiece : Eat a black pawn");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 
        instance.movePiece(new Position(3,0), new Position(4,1)); // BLACK TO PLAY
        
        // WHITE EATS
        
        instance.movePiece(posTo, new Position(3,0));
        
        assertTrue(instance.getBoard()[5][2].isEmpty());
        assertTrue(instance.getBoard()[4][1].isEmpty());

        assertEquals(Color.WHITE, instance.getBoard()[3][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[3][0].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Eat a white pawn
     */
    @Test
    public void testMovePiece17() {
        System.out.println("movePiece : Eat a white pawn");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 
        instance.movePiece(new Position(3,0), new Position(4,1)); 
        instance.movePiece(new Position(6,9), new Position(5,8)); // WHITE TO PLAY

        // BLACK EATS
        
        instance.movePiece(new Position(4,1), new Position(6,3));
        
        assertTrue(instance.getBoard()[4][1].isEmpty());
        assertTrue(instance.getBoard()[5][2].isEmpty());

        assertEquals(Color.BLACK, instance.getBoard()[6][3].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[6][3].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Try to eat a black pawn when not possible
     */
    @Test
    public void testMovePiece18() {
        System.out.println("movePiece : Try to eat a black pawn when not possible");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 
        instance.movePiece(new Position(3,0), new Position(4,1)); // BLACK TO PLAY
        
        // WHITE TRIES TO EAT BUT THERE IS A BLACK PAWN BEHIND.
        
        instance.movePiece(posTo, new Position(3,1));

        assertEquals(Color.WHITE, instance.getBoard()[5][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][0].getType());
        assertEquals(Color.BLACK, instance.getBoard()[4][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][1].getType());
        assertEquals(Color.BLACK, instance.getBoard()[3][2].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[3][2].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Try to eat a white pawn when not possible
     */
    @Test
    public void testMovePiece19() {
        System.out.println("movePiece : Try to eat a white pawn when not possible");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); // WHITE MUST START
        instance.movePiece(new Position(3,0), new Position(4,1)); 
        instance.movePiece(new Position(6,9), new Position(5,8)); // WHITE TO PLAY

        //  BLACK TRIES TO EAT BUT THERE IS A WHITE PAWN BEHIND.
        
        instance.movePiece(new Position(4,1), new Position(6,3));
        
        assertEquals(Color.BLACK, instance.getBoard()[4][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][1].getType());
        assertEquals(Color.WHITE, instance.getBoard()[5][2].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][2].getType());
        assertEquals(Color.WHITE, instance.getBoard()[6][3].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[6][3].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Eat 2 black pawns
     */
    @Test
    public void testMovePiece20() {
        System.out.println("movePiece : Eat 2 black pawns");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 
        instance.movePiece(new Position(3,0), new Position(4,1));
        instance.movePiece(new Position(6,9), new Position(5,8));
        instance.movePiece(new Position(3,2), new Position(4,3));
        
        // WHITE EATS TWO TIMES
        
        instance.movePiece(new Position(5,0), new Position(3,2)); // WHITE PLAYS
        instance.movePiece(new Position(3,2), new Position(5,4)); // WHITE PLAYS

        
        assertTrue(instance.getBoard()[5][0].isEmpty());
        assertTrue(instance.getBoard()[4][1].isEmpty());
        assertTrue(instance.getBoard()[3][2].isEmpty());
        assertTrue(instance.getBoard()[4][3].isEmpty());
        
        assertEquals(Color.WHITE, instance.getBoard()[5][4].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][4].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Eat 2 white pawns
     */
    @Test
    public void testMovePiece21() {
        System.out.println("movePiece : Eat 2 white pawns");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 
        instance.movePiece(new Position(3,0), new Position(4,1));
        instance.movePiece(new Position(6,3), new Position(5,4));
        
        // BLACK EATS TWO TIMES
        
        instance.movePiece(new Position(4,1), new Position(6,3)); // BLACK PLAYS
        instance.movePiece(new Position(6,3), new Position(4,5)); // BLACK PLAYS

        
        assertTrue(instance.getBoard()[4][1].isEmpty());
        assertTrue(instance.getBoard()[5][2].isEmpty());
        assertTrue(instance.getBoard()[6][3].isEmpty());
        assertTrue(instance.getBoard()[5][4].isEmpty());
        
        assertEquals(Color.BLACK, instance.getBoard()[4][5].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][5].getType());
    }

    /**
     * Test of movePiece method, of class Game. White pawn becomes a queen  by top right
     */
    @Test
    public void testMovePiece22() {
        System.out.println("movePiece : White pawn becomes a queen by top right");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom,posTo);
        instance.movePiece(new Position(3,0),  new Position(4,1)); 
        instance.movePiece(new Position(6,3),  new Position(5,2)); 
        instance.movePiece(new Position(3,4),  new Position(4,3)); 
        instance.movePiece(new Position(5,2),  new Position(3,0));
        instance.movePiece(new Position(2,3),  new Position(3,4));
        instance.movePiece(new Position(5,0),  new Position(4,1));
        instance.movePiece(new Position(1,2),  new Position(2,3)); 
        instance.movePiece(new Position(3,0),  new Position(1,2));
        instance.movePiece(new Position(0,3),  new Position(2,1)); 
        instance.movePiece(new Position(4,1),  new Position(3,0)); 
        instance.movePiece(new Position(4,3),  new Position(5,2));
        instance.movePiece(new Position(3,0),  new Position(1,2));
        instance.movePiece(new Position(1,0),  new Position(2,1));
        
        // WHITE PAWN BECOMES A QUEEN
        
        instance.movePiece(new Position(1,2),  new Position(0,3));
        
        assertTrue(instance.getBoard()[1][2].isEmpty());

        assertEquals(Color.WHITE, instance.getBoard()[0][3].getColor());
        assertEquals(PieceType.DAME, instance.getBoard()[0][3].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. White pawn becomes a queen  by top left
     */
    @Test
    public void testMovePiece23() {
        System.out.println("movePiece : White pawn becomes a queen by top left");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom,posTo);
        instance.movePiece(new Position(3,0),  new Position(4,1)); 
        instance.movePiece(new Position(6,3),  new Position(5,2)); 
        instance.movePiece(new Position(3,4),  new Position(4,3)); 
        instance.movePiece(new Position(5,2),  new Position(3,0));
        instance.movePiece(new Position(2,3),  new Position(3,4));
        instance.movePiece(new Position(5,0),  new Position(4,1));
        instance.movePiece(new Position(1,2),  new Position(2,3)); 
        instance.movePiece(new Position(3,0),  new Position(1,2));
        instance.movePiece(new Position(0,3),  new Position(2,1)); 
        instance.movePiece(new Position(4,1),  new Position(3,0)); 
        instance.movePiece(new Position(4,3),  new Position(5,2));
        instance.movePiece(new Position(3,0),  new Position(1,2));
        instance.movePiece(new Position(1,0),  new Position(2,1));
        instance.movePiece(new Position(6,9),  new Position(5,8));
        instance.movePiece(new Position(0,1),  new Position(1,0));
        
        // WHITE PAWN BECOMES A QUEEN
        
        instance.movePiece(new Position(1,2),  new Position(0,1));

        
        assertTrue(instance.getBoard()[1][2].isEmpty());

        assertEquals(Color.WHITE, instance.getBoard()[0][1].getColor());
        assertEquals(PieceType.DAME, instance.getBoard()[0][1].getType());
    }
        
    /**
     * Test of movePiece method, of class Game. Black pawn becomes a queen  by bottom left
     */
    @Test
    public void testMovePiece24() {
        System.out.println("movePiece : Black pawn becomes a queen by bottom left");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        instance.movePiece(posFrom,posTo);
        instance.movePiece(new Position(3,0),  new Position(4,1)); 
        instance.movePiece(new Position(6,3),  new Position(5,2)); 
        instance.movePiece(new Position(4,1),  new Position(6,3)); 
        instance.movePiece(new Position(7,2),  new Position(6,1));
        instance.movePiece(new Position(3,2),  new Position(4,1));
        instance.movePiece(new Position(8,3),  new Position(7,2));
        instance.movePiece(new Position(2,1),  new Position(3,0)); 
        instance.movePiece(new Position(7,4),  new Position(5,2));
        instance.movePiece(new Position(3,4),  new Position(4,3)); 
        instance.movePiece(new Position(8,5),  new Position(7,4)); 
        instance.movePiece(new Position(4,1),  new Position(6,3));
        instance.movePiece(new Position(6,3),  new Position(8,5));
        instance.movePiece(new Position(9,4),  new Position(8,3));
        
        // BLACK PAWN BECOMES A QUEEN 
        
        instance.movePiece(new Position(8,5),  new Position(9,4));

        
        assertTrue(instance.getBoard()[8][5].isEmpty());

        assertEquals(Color.BLACK, instance.getBoard()[9][4].getColor());
        assertEquals(PieceType.DAME, instance.getBoard()[9][4].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Black pawn becomes a queen  by bottom right
     */
    @Test
    public void testMovePiece25() {
        System.out.println("movePiece : Black pawn becomes a queen by bottom right");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        
        instance.movePiece(posFrom,posTo);
        instance.movePiece(new Position(3,0),  new Position(4,1)); 
        instance.movePiece(new Position(6,3),  new Position(5,2)); 
        instance.movePiece(new Position(4,1),  new Position(6,3)); 
        instance.movePiece(new Position(7,2),  new Position(6,1));
        instance.movePiece(new Position(3,2),  new Position(4,1));
        instance.movePiece(new Position(8,3),  new Position(7,2));
        instance.movePiece(new Position(2,1),  new Position(3,0)); 
        instance.movePiece(new Position(7,4),  new Position(5,2));
        instance.movePiece(new Position(3,4),  new Position(4,3)); 
        instance.movePiece(new Position(8,5),  new Position(7,4)); 
        instance.movePiece(new Position(4,1),  new Position(6,3));
        instance.movePiece(new Position(6,3),  new Position(8,5));
        instance.movePiece(new Position(9,6),  new Position(7,4));
        instance.movePiece(new Position(4,3),  new Position(5,2));
        instance.movePiece(new Position(7,4),  new Position(6,3));
        instance.movePiece(new Position(3,0),  new Position(4,1));
        instance.movePiece(new Position(6,5),  new Position(5,6));
        instance.movePiece(new Position(5,2),  new Position(7,4));
        instance.movePiece(new Position(9,4),  new Position(8,3));
        instance.movePiece(new Position(7,4),  new Position(8,5));
        instance.movePiece(new Position(7,2),  new Position(6,3));
        
        // BLACK PAWN BECOMES A QUEEN 
        
        instance.movePiece(new Position(8,5),  new Position(9,6));

        assertTrue(instance.getBoard()[8][5].isEmpty());

        assertEquals(Color.BLACK, instance.getBoard()[9][6].getColor());
        assertEquals(PieceType.DAME, instance.getBoard()[9][6].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. White pawn tries to eat white pawn
     */
    @Test
    public void testMovePiece26() {
        System.out.println("movePiece : White pawn tries to eat white pawn");
        
        Position posFrom = new Position(7,0);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo); 

        assertEquals(Color.WHITE, instance.getBoard()[7][0].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[7][0].getType());
        assertEquals(Color.WHITE, instance.getBoard()[6][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[6][1].getType());
        
        assertTrue(instance.getBoard()[5][2].isEmpty());
    }
    
    /**
     * Test of movePiece method, of class Game. Black pawn tries to eat black pawn
     */
    @Test
    public void testMovePiece27() {
        System.out.println("movePiece : Black pawn tries to eat black pawn");
        
        Position posFrom = new Position(6,1);
        Position posTo = new Position (5,0);
        Game instance = new Game();
        
        instance.movePiece(posFrom,posTo); // WHITE MUST PLAY FIRST
        instance.movePiece(new Position(2,1), new Position (4,3)); 

        assertEquals(Color.BLACK, instance.getBoard()[2][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[2][1].getType());
        assertEquals(Color.BLACK, instance.getBoard()[3][2].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[3][2].getType());
        
        assertTrue(instance.getBoard()[4][3].isEmpty());
    }
}