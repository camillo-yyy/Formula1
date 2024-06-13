package it.unicam.cs.api;

import org.junit.jupiter.api.Test;

import it.unicam.cs.api.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;

public class RacetrackLoaderTest {
   @Test void testParse() {
      TrackLoader x = new RacetrackLoader();
      Track k;
      try{
         k = x.parse(Paths.get("..\\api\\src\\main\\resources\\track.csv"));

         assertEquals(0, k.getStartingLine().getX().getX());
         assertEquals(0, k.getStartingLine().getX().getY());
         assertEquals(0, k.getStartingLine().getY().getX());
         assertEquals(4, k.getStartingLine().getY().getY());
         assertEquals(10, k.getEndingLine().getX().getX());
         assertEquals(0, k.getEndingLine().getX().getY());
         assertEquals(10, k.getEndingLine().getY().getX());
         assertEquals(4, k.getEndingLine().getY().getY());
         assertEquals(false, k.isOut(new Point(2, 2)));
         assertEquals(true, k.isOut(new Point(6, 6)));
         
      }  
      catch(IOException e) {
         System.out.println("IO Exception");;
      }

      

      
      


   }
}
