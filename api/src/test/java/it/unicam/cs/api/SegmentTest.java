package it.unicam.cs.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentTest {
   @Test void testIntersect() {
      Segment x = new Segment(new Point(0.0, 0.0), new Point(4.0, 0.0));
      Segment y = new Segment(new Point(2.0, 2.0), new Point(2.0, -2.0));

      assertEquals(2, x.intersects(y).getX());
      assertEquals(0, x.intersects(y).getY());

      Segment x2 = new Segment(new Point(0.0, 0.0), new Point(4.0, 0.0));
      Segment y2 = new Segment(new Point(2.0, 2.0), new Point(4.0, 4.0));

      assertEquals(null, x2.intersects(y2));

      Segment ray = new Segment(new Point(1.0, 1.0), new Point(10, 1.0));
      //Segment ray2 = new Segment(new Point(2.0, 2.0), new Point(10, 2.0));
      //Segment ray3 = new Segment(new Point(4.0, 3.0), new Point(10, 3.0));
      Segment l1 = new Segment(new Point(0.0, 0.0), new Point(1.0, 3.0));
      Segment l2 = new Segment(new Point(1.0, 3.0), new Point(4.0, 1.0));
      Segment l3 = new Segment(new Point(4.0, 1.0), new Point(0.0, 0.0));

      assertEquals(null, ray.intersects(l1));
      assertEquals(4, ray.intersects(l2).getX());
      assertEquals(1, ray.intersects(l2).getY());
      assertEquals(4, ray.intersects(l3).getX());
      assertEquals(1, ray.intersects(l3).getY());


  }
}
