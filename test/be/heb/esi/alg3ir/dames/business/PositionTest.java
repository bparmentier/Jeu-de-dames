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
public class PositionTest {
    
    
    //@srv retirer ces méthodes pas utilisées.
    public PositionTest() {
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
     * Test of getLine method, of class Position.
     */
    @Test
    public void testGetLine() {
        System.out.println("getLine");
        Position instance = new Position(0, 0);
        assertEquals(0, instance.getLine());
    }

    /**
     * Test of getColumn method, of class Position.
     */
    @Test
    public void testGetColumn() {
        System.out.println("getColumn");
        Position instance = new Position(0, 0);
        assertEquals(0, instance.getColumn());
    }

    /**
     * Test of equals method, of class Position.
     */
    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        assertTrue(new Position(0, 1).equals(new Position(0, 1)));
    }
    
    /**
     * Test of equals method, of class Position.
     */
    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        assertFalse(new Position(0, 1).equals(new Position(2, 3)));
    }

    /**
     * Test of hashCode method, of class Position.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals(new Position(1, 2).hashCode(), new Position(1, 2).hashCode());
    }
    
}
