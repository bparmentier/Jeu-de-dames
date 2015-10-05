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
package be.heb.esi.alg3ir.dames.business;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class PieceTest {
    
    public PieceTest() {
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
     * Test of getColor method, of class Piece.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Piece instance = new Piece();
        assertEquals(Color.NO_COLOR, instance.getColor());
        instance = new Piece(Color.BLACK, PieceType.PION);
        assertEquals(Color.BLACK, instance.getColor());
    }

    /**
     * Test of getType method, of class Piece.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Piece instance = new Piece();
        assertEquals(PieceType.EMPTY, instance.getType());
    }

    /**
     * Test of setType method, of class Piece.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        Piece instance = new Piece(Color.BLACK, PieceType.PION);
        
        instance.setType(PieceType.DAME);
        assertEquals("setType(DAME)", PieceType.DAME, instance.getType());

        instance = new Piece(Color.BLACK, PieceType.DAME);
        
        instance.setType(PieceType.PION);
        assertEquals("setType(PION)", PieceType.PION, instance.getType());
    }
    
    /**
     * Test of setType method, of class Piece.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetTypeException() {
        System.out.println("setTypeException");
        Piece instance = new Piece();
        
        instance.setType(PieceType.DAME);
    }

    /**
     * Test of isEmpty method, of class Piece.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        Piece instance = new Piece();
        assertTrue(instance.isEmpty());
        instance = new Piece(Color.NO_COLOR, PieceType.EMPTY);
        assertTrue(instance.isEmpty());
        instance = new Piece(Color.BLACK, PieceType.DAME);
        assertFalse(instance.isEmpty());
    }
}
