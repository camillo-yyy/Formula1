package it.unicam.cs.formula1.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


import java.util.LinkedList;
import java.util.List;

public class AILoaderTest {
   @Test void testNextDirection() {

      Car c = new RacetrackCar(new Point(0.0, 2.0));
      AILoader ai = new AILoader();
      Driver d = new RacetrackDriver("44", c, ai);
      
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

      LinkedList<Driver> dl = new LinkedList<Driver>();
      dl.add(d);
      RaceEngine re = new RaceEngine(t, dl);

      re.startRace();

      re.nextStep();
      assertEquals(1.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());

  }

  @Test void testMoves() {

   Car c = new RacetrackCar(new Point(0.0, 2.0));
   AILoader ai = new AILoader();
   Driver d = new RacetrackDriver("44", c, ai);
   
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


   ai.updatePosition(d.getCar());
   ai.updateTrack(t);
   List<Direction> dirs = ai.getAvailableMoves();

   assertEquals(dirs.get(0), Direction.N);
   assertEquals(dirs.get(1), Direction.S);
   assertEquals(dirs.get(2), Direction.E);
   assertEquals(dirs.get(3), Direction.STILL);
   assertEquals(dirs.get(4), Direction.NE);
   assertEquals(dirs.get(5), Direction.SE);

}
}
