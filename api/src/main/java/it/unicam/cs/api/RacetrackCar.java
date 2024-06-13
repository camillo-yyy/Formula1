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

public class RacetrackCar implements Car {

   private Point currentPosition;
   private Point nextPosition;
   private Status status;

   public RacetrackCar(Point cp){
      this.currentPosition = cp;
      this.nextPosition = cp;
      this.status = Status.READY;
   }

   public void setPosition(Point p){
      this.currentPosition = p;
      this.nextPosition = p;
   }
   
   @Override
   public Point getPosition() {
      return this.currentPosition;
   }

   @Override
   public Point getNextPosition() {
      return this.nextPosition;
   }

   @Override
   public Segment move(Direction d) {
      Point moveStartingPoint = this.currentPosition;

      this.currentPosition = this.nextPosition.getAdjacent(d);
      this.nextPosition = new Point((this.currentPosition.getX()-moveStartingPoint.getX())+this.currentPosition.getX()
                                    , (this.currentPosition.getY()-moveStartingPoint.getY())+this.currentPosition.getY());

      return new Segment(moveStartingPoint, currentPosition);
   }

   @Override
   public Status getStatus() {
      return this.status;
   }

   @Override
   public void setStatus(Status s) {
      this.status = s;
   }
   
}
