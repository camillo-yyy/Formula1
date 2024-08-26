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
/**
 * Defines input logic for a bot given a car and a track
 * 
 * Every time a next move is requested check all directions from the current positions 
 * returning the one with most "space" available ensuring it will not return on previous positions
 */
public class BaseBotLoader implements InputResolver, BotInterface{

   private Car car;
   private Track track;
   private RaceRule rule;
   private List<Point> lastPositions = new LinkedList<>(); // previous positions
   private Random num = new Random(); 

   @Override
   public Direction getNextMove() {

      if(car == null || track == null || rule == null) return Direction.values()[num.nextInt(Direction.values().length)];

      List<Direction> availableMoves = this.getAvailableDirections(); // get available moves from current position
      return getBestMovementAvailable(availableMoves);
   }

   @Override
   public void updateTrack(Track t) {
      this.track = t;
   }

   @Override
   public void updatePosition(Car p) {
      this.car = p;
   }

   @Override
   public void updateRule(RaceRule r) {
      this.rule = r;
   }

   /**
    * @return get a list of available moves according to rule set and current car position set
    */
   public List<Segment> getAvailableMoves(){

      List<Segment> movs = new LinkedList<Segment>();

      for(Direction d : Direction.values()){
         Segment s = new Segment(this.car.getPosition(), this.car.getNextPosition().getAdjacent(d));
         if(this.rule.isMovementAllowed(this.track, s))
            movs.add(s);
      }
      
      return movs;
   }

   /**
    * @return get a list of available moves according to rule set and given point
    */
    public List<Segment> getAvailableMoves(Point p){

      List<Segment> movs = new LinkedList<Segment>();

      for(Direction d : Direction.values()){
         Segment s = new Segment(p, p.getAdjacent(d));
         if(this.rule.isMovementAllowed(this.track, s))
            movs.add(s);
      }
      
      return movs;
   }

   /**
    * @return get a list of available moves according to rule set and current car position set
    */
    public List<Direction> getAvailableDirections(){

      List<Direction> movs = new LinkedList<Direction>();

      for(Direction d : Direction.values()){
         Segment s = new Segment(this.car.getNextPosition(), this.car.getNextPosition().getAdjacent(d));
         if(this.rule.isMovementAllowed(this.track, s))
            movs.add(d);
      }
      
      return movs;
   }


   /**
    * @return get the most in depth movement possible according to rule set and track
    */
   public Direction getBestMovementAvailable(List<Direction> moves){

      Direction bestMove = Direction.STILL;
      int count=0;
      int max=count;

      lastPositions.add(this.car.getPosition());
      //Point lastPos = this.car.getPosition();

      for (Direction direction : moves) {
         count = 0;
         Point nextPos = this.car.getNextPosition().getAdjacent(direction);

         // loop while checking if the adjacent point is not out of the track or it was already passed
         while(this.rule.isMovementAllowed(this.track,  new Segment(this.car.getPosition(),nextPos)) && !lastPositions.contains(nextPos) 
         && !nextPos.equals(this.car.getPosition())) {

            if(this.rule.triggersWinCon(track, new Segment(this.car.getPosition(),nextPos))) return direction;

            count++;
            //lastPos = nextPos;
            nextPos = nextPos.getAdjacent(direction);
         }
         if(lastPositions.contains(nextPos)) count = 0;
         if(count > max) {
            max = count;
            bestMove = direction;
         }
      }
      return bestMove;
   }

   
}
