/*
 * Created on Thu Jun 06 2024
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
package it.unicam.cs.formula1.api.io;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import it.unicam.cs.formula1.api.Driver;
import it.unicam.cs.formula1.api.InputResolver;
import it.unicam.cs.formula1.api.Point;
import it.unicam.cs.formula1.api.RacetrackCar;
import it.unicam.cs.formula1.api.RacetrackDriver;

/**
 * Class that implements CarLoader
 * Load a RacetrackDriver with RacetrackCar from a .csv file
 * with this format:
 * Carname,X_PositionOfCar,Y_PositionOfCar,U/B depending on input logic
 * 
 * For letter U it will use first parameter 
 * For letter B it will use second parameter 
 */
public class RacetrackCarLoader implements CarLoader {

   private InputResolver h;
   private InputResolver b;

   public RacetrackCarLoader(InputResolver h, InputResolver b){
      this.h = h;
      this.b = b;
   }


   @Override
   public List<Driver> parse(String s) throws IOException {
      String[] lines = s.split(System.lineSeparator());

      List<Driver> ls = new LinkedList<Driver>();

      for(int i=0; i<lines.length; i++) {

         String[] c = lines[i].split(",");
         if(c[3].equals("U"))
         ls.add(new RacetrackDriver(c[0], new RacetrackCar(new Point(Double.parseDouble(c[1]), Double.parseDouble(c[2]))),h));
         else
         ls.add(new RacetrackDriver(c[0], new RacetrackCar(new Point(Double.parseDouble(c[1]), Double.parseDouble(c[2]))),b));

      }

      return ls;

   }

   
}
