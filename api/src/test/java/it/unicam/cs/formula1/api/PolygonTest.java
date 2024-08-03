package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PolygonTest {
    @Test void testPolygontoSegment() {
        List<Point> s = new LinkedList<>();
        s.add(new Point(0, 0));
        s.add(new Point(1, 3));
        s.add(new Point(4, 1));
        s.add(new Point(0, 0));
        
        Polygon t = new Polygon(s);

        List<Segment> ss = Polygon.polygonToSegments(t);
        Iterator<Segment> i = ss.iterator();
        
        Segment ist = i.next();
        assertEquals(0.0, ist.getX().getX());
        assertEquals(0.0, ist.getX().getY());
        assertEquals(1.0, ist.getY().getX());
        assertEquals(3.0, ist.getY().getY());
        Segment cond = i.next();
        assertEquals(1.0, cond.getX().getX());
        assertEquals(3.0, cond.getX().getY());
        assertEquals(4.0, cond.getY().getX());
        assertEquals(1.0, cond.getY().getY());
        Segment ird = i.next();
        assertEquals(4.0, ird.getX().getX());
        assertEquals(1.0, ird.getX().getY());
        assertEquals(0.0, ird.getY().getX());
        assertEquals(0.0, ird.getY().getY());
    }

    @Test void testPointInPolygon() {
        List<Point> s = new LinkedList<>();
        s.add(new Point(0, 0));
        s.add(new Point(1, 3));
        s.add(new Point(4, 1));
        s.add(new Point(0, 0));
        
        Polygon t = new Polygon(s);

        Point p1 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        Point p2 = new Point(4, 3);
        Point p4 = new Point(3, 1);

        assertEquals(true, t.contains(p1));
        assertEquals(false, t.contains(p2));
        assertEquals(true, t.contains(p3));
        assertEquals(true, t.contains(p4));
    }

    @Test void testPointInPolygon2() {
        List<Point> s = new LinkedList<>();
        s.add(new Point(0, 0));
        s.add(new Point(5, 1));
        s.add(new Point(10, 0));
        s.add(new Point(10, 4));
        s.add(new Point(5, 5));
        s.add(new Point(0, 4));
        s.add(new Point(0, 0));

        Polygon t = new Polygon(s);

        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 5);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(2, 3);
        Point p5 = new Point(4, 3);
        Point p6 = new Point(7, 3);
        Point p7 = new Point(11, 3);
        Point p8 = new Point(0, 4);
        assertEquals(true, t.contains( p1));
        assertEquals(false, t.contains(p2));
        assertEquals(true, t.contains(p3));
        assertEquals(true, t.contains(p4));
        assertEquals(true, t.contains(p5));
        assertEquals(true, t.contains(p6));
        assertEquals(false, t.contains(p7));
        assertEquals(true, t.contains(p8));
    }

    @Test void testPointInPolygon3() {
        List<Point> s = new LinkedList<>();
        s.add(new Point(0, 4));
        s.add(new Point(5, 4));
        s.add(new Point(10, 4));
        s.add(new Point(10, 0));
        s.add(new Point(5, 1));
        s.add(new Point(0, 0));
        s.add(new Point(0, 4));

        Polygon t = new Polygon(s);

        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);

        assertEquals(true, t.contains(p1));
        assertEquals(true, t.contains(p2));
    }

    /*@Test void testThrowsException() {
        List<Point> s = new LinkedList<>();
        s.add(new Point(0, 0));
        s.add(new Point(5, 1));
        s.add(new Point(10, 0));
        s.add(new Point(10, 4));
        s.add(new Point(5, 5));
        s.add(new Point(0, 4));

        assertThrows(IllegalArgumentException.class, () -> new Polygon(s));
        
    }*/
}
