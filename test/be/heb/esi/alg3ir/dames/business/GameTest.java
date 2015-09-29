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
package be.heb.esi.alg3ir.dames.business;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bp
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
     * Test of start method, of class Game.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Game instance = new Game();
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of movePiece method, of class Game.
     */
    @Test
    public void testMovePiece() {
        System.out.println("movePiece");
        Position posFrom = null;
        Position posTo = null;
        Game instance = new Game();
        instance.movePiece(posFrom, posTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFinished method, of class Game.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.isFinished();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValidPositions method, of class Game.
     */
    @Test
    public void testGetValidPositions() {
        System.out.println("getValidPositions");
        Position posPieceToMove = null;
        Game instance = new Game();
        List<Position> expResult = null;
        List<Position> result = instance.getValidPositions(posPieceToMove);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Game.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Game instance = new Game();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of currentPlayer method, of class Game.
     */
    @Test
    public void testCurrentPlayer() {
        System.out.println("currentPlayer");
        Game instance = new Game();
        Player expResult = null;
        Player result = instance.currentPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
