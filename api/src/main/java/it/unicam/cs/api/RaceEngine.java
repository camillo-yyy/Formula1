/*
 * Created on Sat Jun 01 2024
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

public class RaceEngine implements Race{

   private Track track;
   private List<Driver> drivers;
   private Driver turnDriver;
   private RaceStatus status;
   private Driver winner;
   private Iterator<Driver> driverIterator;

   public RaceEngine(Track t, List<Driver> ds){
      this.track = t;
      this.drivers = ds;
      this.turnDriver = null;
      this.status = RaceStatus.NOTSTARTED;
      this.winner = null;
      this.driverIterator = this.drivers.iterator();
   }

   //TODO fix this
   @Override
   public Driver nextTurnDriver() {
      Driver ret=null;
      if(this.status == RaceStatus.STARTED){
      
         if(!driverIterator.hasNext()) driverIterator = this.drivers.iterator(); // reset it
                                                         //TODO understand if a round robin decorator is needed
         
         while((ret = driverIterator.next()).getCar().getStatus() != Status.ONTRACK) {
            if(!driverIterator.hasNext()) return null;
         };
      }
      return ret;
   }

   private boolean isOccupied(Car c, Point p){
      for (Driver driver : drivers) {
         if(c != driver.getCar() && driver.getCar().getPosition().equals(p)) return true;
      }
      return false;
   }

   @Override
   public void nextStep() {
      if(this.status == RaceStatus.STARTED){
         this.turnDriver = this.nextTurnDriver();
         if(this.turnDriver == null) this.status = RaceStatus.FINISHED;
         Segment movement;
         movement = this.turnDriver.drive();

         if(this.track.getEndingLine().intersects(movement) != null) {
            this.status = RaceStatus.FINISHED;
            this.winner = this.turnDriver;
            return;
         }
         if(this.track.isOut(movement.getY())) this.turnDriver.getCar().setStatus(Status.CRASHED);//TODO is trespassing?
         if(this.isOccupied(this.turnDriver.getCar(), movement.getY())) this.turnDriver.getCar().setStatus(Status.CRASHED);


      }
   }

   @Override
   public boolean start() {
      if(this.status == RaceStatus.NOTSTARTED){

         int count=0; // count how many drivers are effectively on track

         for (Driver driver : this.drivers) {
            if(!this.track.isOut(driver.getCar().getPosition()) && !this.isOccupied(driver.getCar(), driver.getCar().getPosition())) {
               driver.getCar().setStatus(Status.ONTRACK);
               count++;
            }
         }

         if(count > 0) {
            this.status = RaceStatus.STARTED;
            return true;
         }
         else return false;
      }  
      else return false;
   }

   @Override
   public RaceStatus getRaceStatus() {
      return this.status;
   }

   @Override
   public List<Driver> getCurrentDrivers() {
      return this.drivers;
   }
   
   @Override
   public Track getTrack() {
      return this.track;
   }

   @Override
   public Driver getWinner(){
      return this.winner;
   }
}
