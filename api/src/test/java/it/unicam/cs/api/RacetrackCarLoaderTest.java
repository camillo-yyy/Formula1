package it.unicam.cs.api;

import org.junit.jupiter.api.Test;

import it.unicam.cs.api.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class RacetrackCarLoaderTest {
   @Test void testParse() {
      CarLoader x = new RacetrackCarLoader();

      try{
         List<Driver> k = x.parse(Paths.get("..\\api\\src\\main\\resources\\cars.csv"));

         assertEquals("46", k.getFirst().getUsername());
         assertEquals(1, k.getFirst().getCar().getPosition().getX());
         assertEquals(1, k.getFirst().getCar().getPosition().getY());
         assertEquals("56", k.getLast().getUsername());
         assertEquals(1, k.getLast().getCar().getPosition().getX());
         assertEquals(2, k.getLast().getCar().getPosition().getY());
      }  
      catch(IOException e) {
         System.out.println("IO Exception");;
      }

      


   }
}
