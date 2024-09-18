package it.unicam.cs.formula1.api;

import org.junit.jupiter.api.Test;

import it.unicam.cs.formula1.api.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class RacetrackCarLoaderTest {
   @Test void testParse() {
      DriverLoader x = new RacetrackDriverLoader(new RacetrackCarLoader(), new InputLoader(), new BaseBot());

      try{
         List<Driver> k = x.parseDrivers(Paths.get("..\\api\\src\\main\\resources\\DefaultCars1.csv"));
         String a = "VER,11,15,U";
         String[] s = a.split(",");
         assertEquals("VER", s[0]);
         assertEquals("11", s[1]);
         assertEquals("15", s[2]);
         assertEquals("U", s[3]);
         assertEquals("VER", k.getFirst().getUsername());
         assertEquals(11, k.getFirst().getCar().getPosition().getX());
         assertEquals(15, k.getFirst().getCar().getPosition().getY());
         assertEquals("NOR", k.getLast().getUsername());
         assertEquals(13, k.getLast().getCar().getPosition().getX());
         assertEquals(15, k.getLast().getCar().getPosition().getY());
      }  
      catch(IOException e) {
         System.out.println("IO Exception");;
      }

      


   }
}
