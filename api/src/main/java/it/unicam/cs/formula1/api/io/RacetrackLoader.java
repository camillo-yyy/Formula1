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

import java.util.LinkedList;
import java.util.List;

import it.unicam.cs.formula1.api.*;

/**
 * Class that implements TrackLoader
 * Load a Racetrack from a .csv file
 * with this format:
 * PointX_X,PointX_Y,PointY_X,PointY_Y of starting line
 * PointX_X,PointX_Y,PointY_X,PointY_Y of endingline line
 * X,Y point
 * etc..
 * 
 */
public class RacetrackLoader implements TrackLoader {

   @Override
   public Track parse(String s) {
      String[] lines = s.split("\n");

      List<Point> ls = new LinkedList<Point>();

      String[] ss = lines[0].split(",");
      Segment start = new Segment(new Point(Double.parseDouble(ss[0]), Double.parseDouble(ss[1])), new Point(Double.parseDouble(ss[2]), Double.parseDouble(ss[3])));
      String[] ee = lines[1].split(",");
      Segment end = new Segment(new Point(Double.parseDouble(ee[0]), Double.parseDouble(ee[1])), new Point(Double.parseDouble(ee[2]), Double.parseDouble(ee[3])));


      for(int i=2; i<lines.length; i++) {

         String[] c = lines[i].split(",");
         ls.add(new Point(Double.parseDouble(c[0]), Double.parseDouble(c[1])));

      }

      return new Racetrack(start, end, new Polygon(ls));
   }


   
   
}
