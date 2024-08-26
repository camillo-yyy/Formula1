package it.unicam.cs.formula1.api.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import it.unicam.cs.formula1.api.Driver;

/**
 * Defines a loader for Driver from file
 */
public interface DriverLoader {
     /**
    * Parse a string into a track
    * @param s
    * @return a Track
    * @throws IOException
    */
   Driver parse(String s) throws IOException;


   /**
    * Generate a Track given a file
    * @param path
    * @return
    * @throws IOException
    */
   default Driver parse(Path path) throws IOException { 
      return parse(Files.readString(path));
   }

   /**
    * @param s
    * @return
    * @throws IOException
    */
   default List<Driver> parseDrivers(String s) throws IOException { 

      String[] lines = s.split(System.lineSeparator());

      List<Driver> ls = new LinkedList<Driver>();

      for(int i=0; i<lines.length; i++) {

         ls.add(parse(lines[i]));

      }

      return ls;
   }

   /**
    * @param path
    * @return
    * @throws IOException
    */
   default List<Driver> parseDrivers(Path path) throws IOException { 
      return parseDrivers(Files.readString(path));
   }
}
