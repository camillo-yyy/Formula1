package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.LinkedList;

public class RacetrackTest {
   @Test void testIsOut() {
      List<Point> s = new LinkedList<>();
      s.add(new Point(0, 0));
      s.add(new Point(5, 1));
      s.add(new Point(10, 0));
      s.add(new Point(10, 4));
      s.add(new Point(5, 5));
      s.add(new Point(0, 4));
      Polygon border = new Polygon(s);
      Track t = new Racetrack(new Segment(new Point(0.0, 0.0), new Point(0.0, 4.0)), 
                              new Segment(new Point(10.0, 0.0), new Point(10.0, 4.0)),
                              border);

      assertEquals(false, t.isOut(new Point(1, 3)));
      assertEquals(true, t.isOut(new Point(11, 3)));
      assertEquals(true, t.isOut(new Point(0, 3)));
      assertEquals(false, t.isOut(new Point(1, 3)));
      assertEquals(false, t.isOut(new Point(2, 3)));
      assertEquals(false, t.isOut(new Point(1, 2)));
      assertEquals(false, t.isOut(new Point(2, 2)));

  }

  
}