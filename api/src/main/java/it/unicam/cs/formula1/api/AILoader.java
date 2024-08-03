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
import java.util.function.BiPredicate;
/**
 * Defines input logic for a bot given a car and a track
 * 
 * Every time a next move is requested check all directions from the current positions 
 * returning the one with most "space" available ensuring it will not return on previous positions
 */
public class AILoader implements InputResolver, BotInterface{

   private Car car;
   private Track track;
   private List<Point> lastPositions = new LinkedList<>(); // previous positions
   private BiPredicate<Point, Track> pred;

   @Override
   public Direction getNextMove() {
      List<Direction> availableMoves = this.getAvailableMoves(); // get available moves from current position
      Direction bestMove = Direction.STILL;
      int count=0;
      int max=count;
      
      lastPositions.add(this.car.getPosition());
      Point lastPos = this.car.getPosition();
      for (Direction direction : availableMoves) {
         count = 0;
         Point nextPos = this.car.getPosition().getAdjacent(direction);

         // loop while checking if the adjacent point is not out of the track or it was already passed
         while(!this.pred.test(nextPos, this.track) && !lastPositions.contains(nextPos) 
         && !nextPos.equals(this.car.getPosition())) {
            count++;
            lastPos = nextPos;
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

   @Override
   public void updateTrack(Track t) {
      this.track = t;
   }

   @Override
   public void updatePosition(Car p) {
      this.car = p;
   }

   public List<Direction> getAvailableMoves(){

      List<Direction> dir = new LinkedList<Direction>();

      for(Direction d : Direction.values()){
         if(!this.track.isOut(this.car.getNextPosition().getAdjacent(d)) || this.track.getStartingLine().contains(this.car.getNextPosition().getAdjacent(d)))
            dir.add(d);
      }
      
      return dir;
   }

   @Override
   public void setRule(BiPredicate<Point, Track> predicate) {
      this.pred = predicate;
   }
   
   
}
