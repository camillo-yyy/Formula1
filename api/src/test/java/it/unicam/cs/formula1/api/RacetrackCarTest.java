package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class RacetrackCarTest {
   @Test void testMovement() {
      Car c = new RacetrackCar(new Point(2.0, 2.0));
      
      Segment s = c.move(Direction.N);

      assertEquals(2.0, c.getPosition().getX());
      assertEquals(3.0, c.getPosition().getY());

      assertEquals(2.0, s.getX().getX());
      assertEquals(2.0, s.getX().getY());
      assertEquals(2.0, s.getY().getX());
      assertEquals(3.0, s.getY().getY());

      Segment s2 = c.move(Direction.N);

      assertEquals(2.0, s2.getX().getX());
      assertEquals(3.0, s2.getX().getY());
      assertEquals(2.0, s2.getY().getX());
      assertEquals(5.0, s2.getY().getY());
  }
}