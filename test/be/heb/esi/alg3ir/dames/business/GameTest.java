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

    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of movePiece method, of class Game. Move piece to top left in empty
     * square
     */
    @Test
    public void testMovePiece1() {
        System.out.println("movePiece to top left in empty square");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,2);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        assertEquals(Color.NO_COLOR, instance.getBoard()[6][3].getColor());
        assertEquals(PieceType.EMPTY, instance.getBoard()[6][3].getType());
        assertEquals(Color.WHITE, instance.getBoard()[5][2].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][2].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to top right in empty
     * square
     */
    @Test
    public void testMovePiece2() {
        System.out.println("movePiece to top right in empty square");
        
        Position posFrom = new Position(6,3);
        Position posTo = new Position (5,4);
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        assertEquals(Color.NO_COLOR, instance.getBoard()[6][3].getColor());
        assertEquals(PieceType.EMPTY, instance.getBoard()[6][3].getType());
        assertEquals(Color.WHITE, instance.getBoard()[5][4].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[5][4].getType());
    }
    /**
     * Test of movePiece method, of class Game. Move piece to bottom left in empty
     * square
     */
    @Test
    public void testMovePiece3() {
        System.out.println("movePiece to bottom left in empty square");
        
        Position posFrom = new Position(3,2);
        Position posTo = new Position (4,1);
        Game instance = new Game();
        instance.movePiece(new Position(6,3),new Position (5,4)); // WHITE PLAYER MUST START
        instance.movePiece(posFrom, posTo);
        assertEquals(Color.NO_COLOR, instance.getBoard()[3][2].getColor());
        assertEquals(PieceType.EMPTY, instance.getBoard()[3][2].getType());
        assertEquals(Color.BLACK, instance.getBoard()[4][1].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][1].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to bottom right in empty
     * square
     */
    @Test
    public void testMovePiece4() {
        System.out.println("movePiece to bottom right in empty square");
        
        Position posFrom = new Position(3,2);
        Position posTo = new Position (4,3);
        Game instance = new Game();
        instance.movePiece(new Position(6,3),new Position (5,4)); // WHITE PLAYER MUST START
        instance.movePiece(posFrom, posTo);
        assertEquals(Color.NO_COLOR, instance.getBoard()[3][2].getColor());
        assertEquals(PieceType.EMPTY, instance.getBoard()[3][2].getType());
        assertEquals(Color.BLACK, instance.getBoard()[4][3].getColor());
        assertEquals(PieceType.PION, instance.getBoard()[4][3].getType());
    }
    
    /**
     * Test of movePiece method, of class Game. Move piece to top left on border
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMovePiece5() {
        System.out.println("movePiece to top left on border - out of bounds");
        
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
        System.out.println("movePiece to top right on border - out of bounds");
        
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
        System.out.println("movePiece to bottom left on border - out of bounds");
        
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
        System.out.println("movePiece to bottom right on border - out of bounds");
        
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
        assertEquals(Color.NO_COLOR, instance.getBoard()[4][9].getColor());
        assertEquals(PieceType.EMPTY, instance.getBoard()[4][9].getType());
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
        instance.movePiece(new Position(3,0), new Position(4,1)); // BLAKC MUST PLAY
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
        instance.movePiece(new Position(3,0), new Position(4,1)); // BLAKC MUST PLAY
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
}
