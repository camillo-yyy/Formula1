package it.unicam.cs.formula1.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unicam.cs.formula1.api.geom.Point;
import it.unicam.cs.formula1.api.geom.Polygon;
import it.unicam.cs.formula1.api.geom.Segment;
import it.unicam.cs.formula1.api.io.DriverLoader;
import it.unicam.cs.formula1.api.io.RacetrackCarLoader;
import it.unicam.cs.formula1.api.io.RacetrackDriverLoader;
import it.unicam.cs.formula1.api.io.RacetrackLoader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class BaseBotTest {
   @Test void testNextDirection() {

      Car c = new RacetrackCar(new Point(0.0, 2.0));
      BaseBot ai = new BaseBot();
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
      RaceEngine re = new RaceEngine(t, dl, new RacetrackRule());

      List<Direction> dirs = ai.getAvailableDirections(c, re);

      assertEquals(dirs.get(0), Direction.N);
      assertEquals(dirs.get(1), Direction.S);
      assertEquals(dirs.get(2), Direction.E);
      assertEquals(dirs.get(3), Direction.STILL);
      assertEquals(dirs.get(4), Direction.NE);
      assertEquals(dirs.get(5), Direction.SE);
      re.nextStep();
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      assertEquals(1.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
      List<Direction> dirs2 = ai.getAvailableDirections(c, re);

      assertEquals(dirs2.get(0), Direction.N);
      assertEquals(dirs2.get(1), Direction.W);
      assertEquals(dirs2.get(2), Direction.S);
      assertEquals(dirs2.get(3), Direction.E);
      assertEquals(dirs2.get(4), Direction.STILL);
      assertEquals(dirs2.get(5), Direction.NW);
      assertEquals(dirs2.get(6), Direction.SW);
      assertEquals(dirs2.get(7), Direction.NE);
      assertEquals(dirs2.get(8), Direction.SE);
      re.nextStep();
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getFirst().getCar().getStatus());
      assertEquals(Status.ONTRACK, re.getCurrentDrivers().getLast().getCar().getStatus());
      assertEquals(3.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getX());
      assertEquals(2.0, re.getCurrentDrivers().getFirst().getCar().getPosition().getY());
  }


  @Test void testNextDirection2() {

   try{
      BaseBot ai = new BaseBot();
      DriverLoader d = new RacetrackDriverLoader(new RacetrackCarLoader(), new InputLoader(), ai);
      List<Driver> ds = d.parseDrivers(Paths.get("..\\api\\src\\main\\resources\\DefaultCars3.csv"));
      Track t = new RacetrackLoader().parse(Paths.get("..\\api\\src\\main\\resources\\DefaultTrack3.csv"));
   
      RaceEngine re = new RaceEngine(t, ds, new RacetrackRule());
   
      BaseBot s = (BaseBot) ds.getFirst().getInputStrategy();
   
      s.getBestDirection(new RacetrackCar(new Point(23, 26)), re);

      assertEquals(s.directionDepthness(new Point(23, 26), re, Direction.NE),12);
      assertEquals(s.directionDepthness(new Point(23, 26), re, Direction.N),1);
   }
   catch (IOException e){
      return;
   }

}

  @Test void testMoves() {

   Car c = new RacetrackCar(new Point(0.0, 2.0));
   BaseBot ai = new BaseBot();
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
   RaceEngine re = new RaceEngine(t, dl, new RacetrackRule());
   List<Direction> dirs = ai.getAvailableDirections(c, re);

   assertEquals(dirs.get(0), Direction.N);
   assertEquals(dirs.get(1), Direction.S);
   assertEquals(dirs.get(2), Direction.E);
   assertEquals(dirs.get(3), Direction.STILL);
   assertEquals(dirs.get(4), Direction.NE);
   assertEquals(dirs.get(5), Direction.SE);

}
}
