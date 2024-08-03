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
package it.unicam.cs.formula1.api;

public class Segment {

  // Small epsilon used for double value comparison.
  private static final double EPS = 1e-5;

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

   private static double crossProduct(Point p1, Point p2){
      return p1.getX()*p2.getY()-p1.getY()*p2.getX();
   }

   public int orientation(Point c) {
      double value = (this.getY().getY() - this.getX().getY()) * (c.getX() - this.getY().getX()) - 
                     (this.getY().getX() - this.getX().getX()) * (c.getY() - this.getY().getY());
      if (Math.abs(value) < EPS) return 0;
      return (value > 0) ? -1 : +1;
    }
  
   /**  Tests whether point 'c' is on the line segment.
   /* Ensure first that point c is collinear to segment (a, b) and
   /*  then check whether c is within the rectangle formed by (a, b) */
   public boolean contains(Point c) {
   return orientation(c) == 0 && 
         Math.min(this.getX().getX(), this.getY().getX()) <= c.getX() && c.getX() <= Math.max(this.getX().getX(), this.getY().getX()) && 
         Math.min(this.getX().getY(), this.getY().getY()) <= c.getY() && c.getY() <= Math.max(this.getX().getY(), this.getY().getY());
   }

   /**
    * Check if two segments intersects (based on article "Intersection of two lines in three-space" by Ronald Goldman, 
    published in Graphics Gems, page 304).
    * @param i segment to check
    * @return point of intersection
    */
   public Point intersects(Segment i){

      // segment given is = q + s
      Point q=i.getX();
      // get vector s
      Point s=new Point(i.getY().getX()-q.getX(), i.getY().getY()-q.getY());

      // this segment is = p + r
      Point p = this.x;
      // get vector r
      Point r = new Point(this.y.getX()-p.getX(), this.y.getY()-p.getY());

      // calculate determinant between the vectors
      double rs = Segment.crossProduct(r, s);

      // finding t and u that (p + t r) × s = (q + u s) × s
      double qpr = Segment.crossProduct(new Point(q.getX()-p.getX(), q.getY()-p.getY()), r);
      double qps = Segment.crossProduct(new Point(q.getX()-p.getX(), q.getY()-p.getY()), s);
      double t = qps / rs;
      double u = qpr / rs;
      if(rs == 0){ // if determinant is 0 then no intersection
         return null;
      }
      else if(rs != 0 
      && t >= 0 // if calculated solutions t and u are within 0 and 1 then they intersects within the segment 
      && t <= 1 
      && u >= 0
      && u <= 1){
         return new Point(p.getX()+t*r.getX(),p.getY()+t*r.getY());
      }
      else return null;

   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((x == null) ? 0 : x.hashCode());
      result = prime * result + ((y == null) ? 0 : y.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Segment other = (Segment) obj;
      if (x == null) {
         if (other.x != null)
            return false;
      } else if (!x.equals(other.x))
         return false;
      if (y == null) {
         if (other.y != null)
            return false;
      } else if (!y.equals(other.y))
         return false;
      return true;
   }
   
}
