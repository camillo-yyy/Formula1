/*
 * Created on Sat Aug 31 2024
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

import it.unicam.cs.formula1.api.Driver;
import it.unicam.cs.formula1.api.InputResolver;
import it.unicam.cs.formula1.api.RacetrackDriver;

/**
 * Class that implements CarLoader
 * Load a RacetrackDriver with RacetrackCar from a .csv file
 * with this format:
 * Carname,X_PositionOfCar,Y_PositionOfCar,'U'/'B'
 * 
 * For letter U it will use first InputResolver parameter 
 * For letter B it will use second InputResolver parameter 
 */
public class RacetrackDriverLoader implements DriverLoader{

   private InputResolver u;
   private InputResolver b;
   private CarLoader carL;

   public RacetrackDriverLoader(CarLoader c, InputResolver u, InputResolver b){
      this.carL = c;
      this.u = u;
      this.b = b;
   }

   @Override
   public Driver parse(String s) throws IOException {

      String[] c = s.split(",");
      if(c[3].equals("U"))
      return new RacetrackDriver(c[0], carL.parse(c[1]+","+c[2]),u);
      else
      return new RacetrackDriver(c[0], carL.parse(c[1]+","+c[2]),b);

   }

   
}
