/*
 * Created on Sat Jul 06 2024
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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unicam.cs.formula1.api.geom.Point;
import it.unicam.cs.formula1.api.geom.Segment;
/**
 * Defines input logic for a bot given a car and a track
 * 
 * Every time a next move is requested check all directions from the current positions 
 * returning the one with most "space" available ensuring it will not return on previous positions
 */
public class BaseBot implements BotResolver{
   
   private Random num = new Random(); 

   @Override
   public Direction getNextMove(Car c, Race r) {

      if(r == null) return Direction.values()[num.nextInt(Direction.values().length)];
      return this.getBestDirection(c, r);
      
   }

   @Override
   public Direction getNextMove() {
      return Direction.values()[num.nextInt(Direction.values().length)];
   }

   /**
    * @return get a list of available moves according to rule set and current car position set
    */
   public List<Segment> getAvailableMoves(Car car, Race race){

      List<Segment> movs = new LinkedList<Segment>();

      for(Direction d : Direction.values()){
         Segment s = new Segment(car.getPosition(), car.getNextPosition().getAdjacent(d));
         if(race.getRaceRule().isMovementAllowed(race.getTrack(), s))
            movs.add(s);
      }
      
      return movs;
   }

   /**
    * @return get a list of available moves according to rule set and given point
    */
   public List<Segment> getAvailableMoves(Race race, Point p){

      List<Segment> movs = new LinkedList<Segment>();

      for(Direction d : Direction.values()){
         Segment s = new Segment(p, p.getAdjacent(d));
         if(race.getRaceRule().isMovementAllowed(race.getTrack(), s))
            movs.add(s);
      }
      
      return movs;
   }

   /**
    * @return get a list of available moves according to rule set and current car position set
    */
   public List<Direction> getAvailableDirections(Car car, Race race){

      List<Direction> movs = new LinkedList<Direction>();

      for(Direction d : Direction.values()){
         Segment s = new Segment(car.getNextPosition(), car.getNextPosition().getAdjacent(d));
         if(race.getRaceRule().isMovementAllowed(race.getTrack(), s))
            movs.add(d);
      }
      
      return movs;
   }
   
   /**
    * @return get the most in depth movement possible according to rule set and track
    */   
   public Direction getBestDirection(Car car, Race race){
      Direction bestMove = Direction.STILL;
      int count=0;
      int max=count;

      for (Direction direction : Direction.values()) {
         if(direction.reverse() != car.getLastDirection()) {
            count = directionDepthness(car.getPosition(), race, direction);
            if(count > max) {
               max = count;
               bestMove = direction;
            }
         }
      }
      if(max < 4 && car.getLastDirection() != Direction.STILL) return bestMove.reverse();
      return bestMove;
   }

   /**
    * Return depthness of a direction given a point and a racetrack
      Return 99 if direction satisfy race wincon.
    * @param p point 
    * @param r race
    * @param d direction
    * @return lenght of movement from given point moving forward passed direction following race rules
    */
   public int directionDepthness(Point p, Race r, Direction d){
      int count = 0;
      Point nextPos = p.getAdjacent(d);
      while(r.getRaceRule().isMovementAllowed(r.getTrack(),  new Segment(p,nextPos))
      && !nextPos.equals(p)) {

         if(r.getRaceRule().triggersWinCon(r.getTrack(), new Segment(p,nextPos))) return 99;

         count++;
         nextPos = nextPos.getAdjacent(d);
      }
      return count;
   }
}

