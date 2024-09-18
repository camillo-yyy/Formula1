package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;

import it.unicam.cs.formula1.api.geom.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {
   @Test void testGetAdjacent() {
      Point x = new Point(0.0, 0.0);
      
      assertEquals(0, x.getAdjacent(Direction.N).getX());
      assertEquals(1, x.getAdjacent(Direction.N).getY());

      assertEquals(0, x.getAdjacent(Direction.S).getX());
      assertEquals(-1, x.getAdjacent(Direction.S).getY());

      assertEquals(-1, x.getAdjacent(Direction.W).getX());
      assertEquals(0, x.getAdjacent(Direction.W).getY());
      
      assertEquals(1, x.getAdjacent(Direction.E).getX());
      assertEquals(0, x.getAdjacent(Direction.E).getY());

      assertEquals(-1, x.getAdjacent(Direction.NW).getX());
      assertEquals(1, x.getAdjacent(Direction.NW).getY());

      assertEquals(-1, x.getAdjacent(Direction.SW).getX());
      assertEquals(-1, x.getAdjacent(Direction.SW).getY());

      assertEquals(1, x.getAdjacent(Direction.NE).getX());
      assertEquals(1, x.getAdjacent(Direction.NE).getY());

      assertEquals(1, x.getAdjacent(Direction.SE).getX());
      assertEquals(-1, x.getAdjacent(Direction.SE).getY());
  }
}
