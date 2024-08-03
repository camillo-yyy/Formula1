/*
 * Created on Sat Jun 15 2024
 *
 * The MIT License (MIT)
 * Copyright (c) 2024 Samuele Camilletti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.unicam.cs.formula1.app;

import org.junit.jupiter.api.Test;

import it.unicam.cs.formula1.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFXControllerTest {

   @Test void testArray() {

      LinkedList<Point> s = new LinkedList<>();
      s.add(new Point(0, 0));
      s.add(new Point(5, 1));
      s.add(new Point(10, 0));
      s.add(new Point(10, 4));
      s.add(new Point(5, 5));
      s.add(new Point(0, 4));
      s.add(new Point(0, 0));
      Polygon border = new Polygon(s);
      Track t = new Racetrack(new Segment(new Point(0.0, 0.0), new Point(0.0, 4.0)), 
                              new Segment(new Point(10.0, 0.0), new Point(10.0, 4.0)),
                              border);
      Car c1 = new RacetrackCar(new Point(1, 2));
      Car c2 = new RacetrackCar(new Point(1, 3));
      Driver d1 = new RacetrackDriver("46", c1, ()->{return Direction.E;});
      Driver d2 = new RacetrackDriver("23", c2, ()->{return Direction.E;});
      LinkedList<Driver> dl = new LinkedList<Driver>();
      dl.add(d1);
      dl.add(d2);
      RaceEngine re = new RaceEngine(t, dl);
      
      List<Double> array = re
      .getTrack()
      .getCorners()
      .stream()
      .flatMap(p -> Stream.of(p.getX()*10, p.getY()*10))
      .collect(Collectors.toList())
      ;
      Double[] a = new Double[array.size()];
      a = array.toArray(a);

      assertEquals(0.0, a[0]);
      assertEquals(0.0, a[1]);
      assertEquals(50.0, a[2]);
      assertEquals(10.0, a[3]);
      assertEquals(0.0, a[13]);


  }

}
