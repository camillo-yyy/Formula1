/*
 * Created on Thu May 30 2024
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
package it.unicam.cs.formula1.api;

import java.util.List;

public class Racetrack implements Track {

   private Segment startLine;
   private Segment finishLine;
   private Polygon points;

   public Racetrack(Segment s, Segment e, Polygon p){

      if(s.intersects(e) != null) throw new IllegalArgumentException("Invalid start");

      this.startLine = s;
      this.finishLine = e;
      this.points = p;

   }

   @Override
   public boolean isOut(Point p) {
      return !points.contains(p);
   }
   
   @Override
   public Segment getStartingLine() {
      return this.startLine;
   }

   @Override
   public Segment getEndingLine() {
      return this.finishLine;
   }

   @Override
   public List<Point> getCorners() {
      return this.points.getPoints();
   }

   @Override
   public List<Segment> getBorders() {
      return Polygon.polygonToSegments(points);
   }
   
}
