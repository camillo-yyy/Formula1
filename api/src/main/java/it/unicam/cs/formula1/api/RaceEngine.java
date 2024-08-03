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
package it.unicam.cs.formula1.api;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * Class that simulates a turn based racetrack match
 * Turn driver is selected through a round robin method.
 * RaceStatus pass from NOTSTARTED to STARTED when raceStart() is executed.
 * When RaceStatus is STARTED every new step is computed through methdo nextStep()
 * When a final condition is reached status will set FINISHED
 */
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

   @Override
   public Driver turnDriver() {
      return this.turnDriver;
   }

   /**
    * Compute next turn driver using a round robin iterator
    */
   private Driver nextTurnDriver() {
      Driver ret=null;
      int counter=0;
      if(this.status == RaceStatus.STARTED){
      
         if(!driverIterator.hasNext()) driverIterator = this.drivers.iterator(); // reset it
         
         while((ret = driverIterator.next()).getCar().getStatus() != Status.ONTRACK) {
            if(++counter == this.drivers.size()) return null;
            if(!driverIterator.hasNext()) driverIterator = this.drivers.iterator();
         };
      }
      return ret;
   }

   /**
    * Calculate if a car is crashing another car with the last movement
    * @param c avoid calculating with itself
    * @param p Point to be considered
    * @return if it's crashing or not
    */
   private boolean isOccupied(Car c, Point p){
      for (Driver driver : drivers) {
         if(c != driver.getCar() && driver.getCar().getPosition().equals(p)) return true;
      }
      return false;
   }

 
   /**
    * Updating logic of driver that extends BotInterface interface
    * @param d driver to test
    */
   private void updateAI(Driver d){

      InputResolver i = d.getInputStrategy();

      if(i instanceof BotInterface) {
         BotInterface input = (BotInterface) i;
         input.updatePosition(d.getCar());
         input.updateTrack(this.track);
         input.setRule(ruleOutTrack());
      }

   }

   public BiPredicate<Point, Track> ruleOutTrack(){
      return (movement, track) -> {
         return !track.getStartingLine().contains(movement) 
         && track.isOut(movement);};
   }

   @Override
   public Driver nextStep() {
      if(this.status == RaceStatus.STARTED){
         this.turnDriver = this.nextTurnDriver();
         if(this.turnDriver == null) {
            this.status = RaceStatus.FINISHED;
            return null;
         }
         this.updateAI(turnDriver);

         Segment movement = this.turnDriver.drive();

         // wincondition
         if(this.track.getEndingLine().intersects(movement) != null) {
            this.status = RaceStatus.FINISHED;
            this.winner = this.turnDriver;
            return this.turnDriver;
         }

         // if car position is out and not on the starting line
         if(ruleOutTrack().test(movement.getY(), this.track)) 
            this.turnDriver.getCar().setStatus(Status.CRASHED);
         // if its crashing on another car
         if(this.isOccupied(this.turnDriver.getCar(), movement.getY())) 
            this.turnDriver.getCar().setStatus(Status.CRASHED);
            
         return this.turnDriver;
      }
      return null;
   }

   @Override
   public boolean startRace() {
      if(this.status == RaceStatus.NOTSTARTED){

         int count=0; // count how many drivers are effectively on starting line

         for (Driver driver : this.drivers) {
            //if(!this.track.isOut(driver.getCar().getPosition()) && !this.isOccupied(driver.getCar(), driver.getCar().getPosition())) {
            if(this.track.getStartingLine().contains(driver.getCar().getPosition()) && !this.isOccupied(driver.getCar(), driver.getCar().getPosition())) {
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
