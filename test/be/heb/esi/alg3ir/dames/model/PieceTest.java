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
package be.heb.esi.alg3ir.dames.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gbps2
 */
public class PieceTest {

    /**
     * Test of getColor method, of class Piece.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Piece instance = new Pawn(Color.BLACK);
        assertEquals(Color.BLACK, instance.getColor());
    }

    /**
     * Test of getType method, of class Piece.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Piece instance = new Pawn(Color.WHITE);
        assertEquals(PieceType.PAWN, instance.getType());
    }

    /**
     * Test of setType method, of class Piece.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        Piece instance = new Pawn(Color.BLACK);
        
        assertEquals("setType(PION)", PieceType.PAWN, instance.getType());

        instance = new Queen(Color.BLACK);
        
        assertEquals("setType(DAME)", PieceType.QUEEN, instance.getType());
    }
}
