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
package it.unicam.cs.formula1.api.geom;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
/**
 * Particular type of a polygonal chain, which forms
 * a closed shape, where last point is connected to the first one through a segment.
 */
public class Polygon extends PolygonalChain{
   
    private final static double BIG_NUMBER = 10000000;

    public Polygon(List<Point> s) {
        super(s);
    }

   /**
    * Check if p point is inside the ps polygon using a variant of ray casting algorithm
    * The algorithm first create a ray starting from the point given and check how many points it intersects
    * If any vertex is contained then the ray is replaced
    * If the total number of intersection is odd then Point is contained otherwise not.
    * @param p Point
    * @return
    */
    public boolean contains(Point p){

        List<Segment> segments = Polygon.polygonToSegments(this);
        List<Point> tempInters = new LinkedList<>();
        int i = 0;

        for(Segment ss : segments) if(ss.contains(p)) return true; // if point is contained in the segments
        do{
            // empty the list
            tempInters.clear();
            // creating a ray from the point to 'infinite'
            Segment ray = new Segment(p, new Point(BIG_NUMBER, p.getY()+i%this.points.size())); 
            for (Segment segment : segments) {

                Point x = ray.intersects(segment); // calculate the point of intersection
                // if the point is not null add to a temporary list of intersection points
                if(x!=null) tempInters.add(x);

            }
            i=+1;
        }while(containsAny(tempInters)); // verify if any vertex was an intersection point
        // if yes then calculate intersections again changing the angle of the ray
        
        return tempInters.size() % 2 == 1;
    }

    /**
     * verify if any vertex of the polygon is contained in the given list of point
     */ 
    public boolean containsAny(List<Point> intersxray) {
        for (Point point : this.points) {
            if(intersxray.contains(point)) return true;
        }
        return false;
    }

    /**
     * @param ps Polygon given
     * @return a list of segment 
     */
    public static List<Segment> polygonToSegments(Polygon ps) {

        Iterator<Point> it = ps.getPoints().iterator();
        List<Segment> ls = new LinkedList<>();

        Point first = it.next();
        Point i=first, j=null;

        do{
            j = it.next();
            ls.add(new Segment(i, j));
            i = j;
        }while(it.hasNext());
        
        // segment that connect last point to first one
        ls.add(new Segment(i, first));

        return ls;

    }

}
