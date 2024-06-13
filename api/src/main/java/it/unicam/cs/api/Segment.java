/*
 * Created on Wed May 29 2024
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
package it.unicam.cs.api;

public class Segment {

   private final Point x;
   private final Point y;

   public Segment(Point x, Point y){
      this.x = x;
      this.y = y;
   }

   public Point getX() {
      return x;
   }

   public Point getY() {
      return y;
   }
   
   public boolean isContained(Point p){
      return false;
      //TODO
   }



   private static double crossProduct(Point p1, Point p2){
      return p1.getX()*p2.getY()-p1.getY()*p2.getX();
   }

   public Point intersects(Segment i){

      Point q=i.getX();
      Point s=new Point(i.getY().getX()-q.getX(), i.getY().getY()-q.getY());

      Point p = this.x;
      Point r = new Point(this.y.getX()-p.getX(), this.y.getY()-p.getY());

      double rs = Segment.crossProduct(r, s);
      double qpr = Segment.crossProduct(new Point(q.getX()-p.getX(), q.getY()-p.getY()), r);
      double qps = Segment.crossProduct(new Point(q.getX()-p.getX(), q.getY()-p.getY()), s);
      double t = qps / rs;
      double u = qpr / rs;
      if(rs == 0){
         return null;
      }
      else if(rs != 0 && t >= 0 //TODO check double precision better
      && t <= 1 
      && u >= 0
      && u <= 1){
         return new Point(p.getX()+t*r.getX(),p.getY()+t*r.getY());
      }
      else return null;

   }
   
}
