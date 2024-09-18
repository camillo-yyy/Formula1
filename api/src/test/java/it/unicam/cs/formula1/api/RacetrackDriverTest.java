package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;

import it.unicam.cs.formula1.api.geom.Point;
import it.unicam.cs.formula1.api.geom.Segment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RacetrackDriverTest {
   @Test void testDirection() {
      Car c = new RacetrackCar(new Point(2.0, 2.0));
      Driver d = new RacetrackDriver("44", c, new InputLoader());

      InputLoader il = (InputLoader) d.getInputStrategy();

      ExecutorService dd = Executors.newCachedThreadPool();
      try {
            Future<Segment> future = dd.submit(() -> d.drive());  
            il.getSharedVar().setVar(Direction.N);
            future.get();
      }
      catch (InterruptedException e){

      }
      catch (ExecutionException e){
            
      }

      assertEquals(2.0, c.getPosition().getX());
      assertEquals(3.0, c.getPosition().getY());

/*       assertEquals(2.0, s.getX().getX());
      assertEquals(2.0, s.getX().getY());
      assertEquals(2.0, s.getY().getX());
      assertEquals(3.0, s.getY().getY()); */



      //assertEquals(Direction.N, d.getInputStrategy().getNextMove());

  }
}