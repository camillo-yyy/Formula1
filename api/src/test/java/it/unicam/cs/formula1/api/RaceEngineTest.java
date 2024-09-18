package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;

import it.unicam.cs.formula1.api.geom.Point;
import it.unicam.cs.formula1.api.geom.Polygon;
import it.unicam.cs.formula1.api.geom.Segment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

public class RaceEngineTest {

   public class StupidBot implements BotResolver{

      @Override
      public Direction getNextMove() {
         return Direction.E;
      }

      @Override
      public Direction getNextMove(Car c, Race r) {
         return Direction.E;
      }
      
   }

   @Test void testNextDriver() {
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
      Car c1 = new RacetrackCar(new Point(0, 2));
      Car c2 = new RacetrackCar(new Point(0, 3));
      Driver d1 = new RacetrackDriver("46", c1, new StupidBot());
      Driver d2 = new RacetrackDriver("23", c2, new StupidBot());
      LinkedList<Driver> dl = new LinkedList<Driver>();
      dl.add(d1);
      dl.add(d2);
      RaceEngine re = new RaceEngine(t, dl, new RacetrackRule());
      assertEquals(d1, re.nextTurnDriver());
      re.nextStep();
      assertEquals(d2, re.nextTurnDriver());
      assertEquals(d1, re.turnDriver());
      re.nextStep();
      assertEquals(d1, re.nextTurnDriver());
      assertEquals(d2, re.turnDriver());
  }

  @Test void testStart() {
      LinkedList<Point> s = new LinkedList<>();
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
      Car c1 = new RacetrackCar(new Point(0, 2));
      Car c2 = new RacetrackCar(new Point(0, 3));
      Driver d1 = new RacetrackDriver("46", c1, new StupidBot());
      Driver d2 = new RacetrackDriver("23", c2, new StupidBot());
      LinkedList<Driver> dl = new LinkedList<Driver>();
      dl.add(d1);
      dl.add(d2);
      RaceEngine re = new RaceEngine(t, dl, new RacetrackRule());
      assertEquals(RaceStatus.NOTSTARTED, re.getRaceStatus());
      re.nextStep();
      assertEquals(RaceStatus.STARTED, re.getRaceStatus());
      assertEquals("46", re.getCurrentDrivers().getFirst().getUsername());
      assertEquals("23", re.getCurrentDrivers().getLast().getUsername());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
   }

   @Test void testRun() {
      LinkedList<Point> s = new LinkedList<>();
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
      Car c1 = new RacetrackCar(new Point(0, 2));
      Car c2 = new RacetrackCar(new Point(0, 3));
      Driver d1 = new RacetrackDriver("46", c1, new StupidBot());
      Driver d2 = new RacetrackDriver("23", c2, new StupidBot());
      LinkedList<Driver> dl = new LinkedList<Driver>();
      dl.add(d1);
      dl.add(d2);
      RaceEngine re = new RaceEngine(t, dl, new RacetrackRule());

      re.nextStep();
      assertEquals(1.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      re.nextStep();
      assertEquals(1.0, re.getCurrentDrivers().getLast().getCar().getPosition().getX());
      assertEquals(3.0, re.getCurrentDrivers().getLast().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      re.nextStep();
      assertEquals(3.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      re.nextStep();
      assertEquals(3.0, re.getCurrentDrivers().getLast().getCar().getPosition().getX());
      assertEquals(3.0, re.getCurrentDrivers().getLast().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      re.nextStep();
      assertEquals(6.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      re.nextStep();
      assertEquals(6.0, re.getCurrentDrivers().getLast().getCar().getPosition().getX());
      assertEquals(3.0, re.getCurrentDrivers().getLast().getCar().getPosition().getY());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      re.nextStep();
      assertEquals(10.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
      assertEquals(RaceStatus.FINISHED, re.getRaceStatus());
      assertEquals(d1, re.getWinner());

  }


}