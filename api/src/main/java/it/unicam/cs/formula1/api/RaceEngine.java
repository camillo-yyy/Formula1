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

import it.unicam.cs.formula1.api.geom.Segment;

/**
 * Class that simulates a turn based racetrack match
 * Turn driver is selected through a round robin method.
 * RaceStatus pass from NOTSTARTED to STARTED when nextStep() is executed for the first time.
 * When RaceStatus is STARTED every new step is computed through methdo nextStep()
 * When a final condition is reached status is FINISHED
 */
public class RaceEngine implements Race{

   private Track track;
   private List<Driver> drivers;
   private Driver turnDriver;
   private RaceStatus status;
   private Driver winner;
   private Iterator<Driver> driverIterator;
   private RaceRule ruleSets;
   private Driver nextTurnDriver;

   public RaceEngine(Track t, List<Driver> ds, RaceRule s){
      this.track = t;
      this.drivers = ds;
      this.turnDriver = null;
      this.nextTurnDriver = null;
      this.status = RaceStatus.NOTSTARTED;
      this.winner = null;
      this.driverIterator = this.drivers.iterator();
      this.ruleSets = s;

      this.setupRace();
      this.computeNextTurnDriver();
   }

   @Override
   public Driver turnDriver() {
      return this.turnDriver;
   }

   @Override
   public Driver nextTurnDriver() {
      return this.nextTurnDriver;
   }

   /**
    * Compute next turn driver using a round robin iterator
    * @return next turn driver
    */
   private void computeNextTurnDriver() {
      Driver ret=null;
      int counter=0;

      if(!driverIterator.hasNext()) driverIterator = this.drivers.iterator(); // reset it
      
      while((ret = driverIterator.next()).getCar().getStatus() != Status.ONTRACK) { 
         if(++counter == this.drivers.size()) {
            this.nextTurnDriver = null; // if all drivers are crashed
            return;
         }
         if(!driverIterator.hasNext()) driverIterator = this.drivers.iterator(); // reset it
      };

      this.nextTurnDriver = ret;
   }

   /**
    * Calculate if a car is crashing another car with the last movement
    * @param c Car to be considered
    * @return if it's crashing or not
    */
   private boolean isOccupied(Car c){
      for (Driver driver : drivers) {
         if(c != driver.getCar() && driver.getCar().getPosition().equals(c.getPosition())) return true;
      }
      return false;
   }

   private Segment computeMovement(Driver d){
      return switch(d.getInputStrategy()){
         case HumanResolver h-> d.drive();
         case BotResolver b -> d.drive(b.getNextMove(d.getCar(), this));
      };
   }
 
   @Override
   public Driver nextStep() {
      if(startRace()){
         if((this.turnDriver = this.nextTurnDriver) == null) {
            this.status = RaceStatus.FINISHED;
         }
         else {
            Segment movement = this.computeMovement(this.turnDriver); // compute movement
   
            // if car movement not allowed
            if(!this.ruleSets.isMovementAllowed(track, movement)) 
               this.turnDriver.getCar().setStatus(Status.CRASHED);
            // if its crashing on another car
            if(this.isOccupied(this.turnDriver.getCar()))
               this.turnDriver.getCar().setStatus(Status.CRASHED);

            checkEndRace(track, movement);
         }
         this.computeNextTurnDriver();
         return this.turnDriver;
      }
      else return null;
   }

   private void checkEndRace(Track t, Segment s){
      // wincondition
      if(this.ruleSets.triggersWinCon(track, s)) {
         this.status = RaceStatus.FINISHED;
         this.winner = this.turnDriver;
      }
   }

   /**
    * @return true if race status is not NOTSTARTED
    */
   private boolean startRace() {
      if(this.status == RaceStatus.NOTSTARTED){
         int count=0; // count how many drivers are effectively on starting line
         for (Driver driver : this.drivers) {
            if(driver.getCar().getStatus() == Status.ONTRACK) {
               count++;
            }
         }
         if(count > 0) {
            this.status = RaceStatus.STARTED;
         }
         else return false;
      }  
      return true;
   }

      /**
    * @return true if race is setup
    */
    private void setupRace() {
      if(this.status == RaceStatus.NOTSTARTED){
         for (Driver driver : this.drivers) {
            if(ruleSets.isAllowedToStart(track, driver) && !this.isOccupied(driver.getCar())) {
               driver.getCar().setStatus(Status.ONTRACK);
            }
         }
      }  
   }

   @Override
   public RaceStatus getRaceStatus() {
      return this.status;
   }

   @Override
   public RaceRule getRaceRule() {
      return this.ruleSets;
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
