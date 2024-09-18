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
package it.unicam.cs.formula1.api;

import it.unicam.cs.formula1.api.geom.Segment;

/**
 * This RaceTrack Rule defines racing rules for a standard RaceTrack Formula 1 Game.
 * 
 * According to this rule  Drivers that are contained on starting Line track are allowed to start the race.
 * According to this Rule movements that collides with any of track's border are not allowed except ones that intersects ending Line.
 * According to this Rule the win con is triggered if ending Line is trespassed.
*/
public class RacetrackRule implements RaceRule {

   @Override
   public boolean isAllowedToStart(Track t, Driver d) {
      return t.getStartingLine().contains(d.getCar().getPosition());
   }

   @Override
   public boolean isMovementAllowed(Track t, Segment s) {
      if(t.getStartingLine().contains(s.getX())){ // if starting point of the movement is contained on starting line
         if(t.getStartingLine().contains(s.getY()) || (!t.isOut(s.getY()) && checkCollision(t, s) == 1)) return true;
      }
      else{
         if(!t.isOut(s.getX())) { // if ending point of the movement is intersecting ending line
            if(checkCollision(t, s) == 1 && s.intersects(t.getEndingLine()) != null) return true;
         }
         else return false;
      }
      if(checkCollision(t, s) < 1) return true;
      else return false;
   }

   private int checkCollision(Track t, Segment s){
      int count = 0;
      for(Segment tt : t.getBorders()){
         if(s.intersects(tt) != null) count++;
      }
      return count;
   }

   @Override
   public boolean triggersWinCon(Track t, Segment s) {
      return t.getEndingLine().intersects(s) != null && !t.isOut(s.getX());
   }
   
}
