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
package it.unicam.cs.api;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
/**
 * Version of a polygonal chain in which starting point equals the end point 
 * so the polygonal shape is a closed shape.
 */
public class Polygon extends PolygonalChain{
   
   public Polygon(List<Point> s) {
        super(s);

        if(!s.getFirst().equals(s.getLast())) throw new IllegalArgumentException("First vertex is not equal to last one");
    }

   /**
    * check if p point is inside the ps polygon
    * @param ps
    * @param p
    * @return
    */
   public boolean contains(Point p){

        List<Segment> segments = this.polygonToSegments(this.points);
        List<Point> inters = new LinkedList<>();
        List<Point> intersxray;
        int i = 0;
        
        // if the point is a vertex then it is contained
        if(this.points.contains(p)) return true;
        do{
            intersxray = new LinkedList<>();
            Segment ray = new Segment(p, new Point(100000, p.getY()+i%this.points.size())); // should be replaced with a max value
            for (Segment segment : segments) {

                Point x = ray.intersects(segment);
                if(!inters.contains(x) && x!=null) intersxray.add(x);

            }
            i=+1;
        }while(containsAny(intersxray));

        inters.addAll(intersxray);

        return inters.size() % 2 == 1;

   }

    private boolean containsAny(List<Point> intersxray) {
        for (Point point : this.points) {
            if(intersxray.contains(point)) return true;
        }
        return false;
    }

    public List<Segment> polygonToSegments(List<Point> ps) {

        Iterator<Point> it = ps.iterator();
        List<Segment> ls = new LinkedList<>();

        Point first = it.next();
        Point i=first, j=null;

        do{
            j = it.next();
            ls.add(new Segment(i, j));
            i = j;
        }while(!first.equals(i));
        
        
        return ls;

    }

}
