/*
 * Created on Fri Jun 07 2024
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
package it.unicam.cs.formula1.app;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import it.unicam.cs.formula1.api.Race;
import it.unicam.cs.formula1.api.BotResolver;
import it.unicam.cs.formula1.api.Direction;
import it.unicam.cs.formula1.api.Driver;
import it.unicam.cs.formula1.api.HumanResolver;
import it.unicam.cs.formula1.api.RaceFactory;
import it.unicam.cs.formula1.api.RaceRule;
import it.unicam.cs.formula1.api.Track;
import it.unicam.cs.formula1.api.io.DriverLoader;
import it.unicam.cs.formula1.api.io.TrackLoader;

/**
 * Controller to manage model and console view 
 */
public final class SetupController {

   private ConsoleView console;
   private Race race;
   private TrackLoader trackLoader;
   private DriverLoader driverLoader;
   private RaceFactory raceFactory;
   private RaceRule raceRule;

   // loaded from file
   private Track track;
   private List<Driver> drivers;

   public SetupController(TrackLoader t, DriverLoader d, RaceFactory r, RaceRule s){ 
      this.trackLoader = t;
      this.raceFactory = r;
      this.raceRule = s;
      this.driverLoader = d;
   }

   public static SetupController getSetupController(TrackLoader t, DriverLoader d, RaceFactory r, RaceRule s) {
      return new SetupController(t, d, r, s);
   }

   public void loadTrack(String urlTrack) {

      try{
         this.track = trackLoader.parse(Paths.get(urlTrack));
      }
      catch(IOException e){
         System.out.println(e.getMessage());;
      }

   }

   public void loadDrivers(String urlCar) {

      try{
         this.drivers = driverLoader.parseDrivers(Paths.get(urlCar));
      }
      catch(IOException e){
         System.out.println(e.getMessage());;
      }

   }

   public void sendInput(Direction d){

      switch(this.getModel().turnDriver().getInputStrategy()){
         case HumanResolver h: 
            h.sendDirection(d);
            break;
         case BotResolver b: return;
      };
   }

   public boolean start(){

      if(this.track != null && this.drivers != null){      
         this.race = raceFactory.createRace(this.track, this.drivers, this.raceRule);
         this.console = new ConsoleView(this.race);
         this.console.printDriversState();
         this.console.printRaceStatus();
         return true;
      }
      return false;

   }

   public void run(){
      
      if(this.race != null){
         this.race.nextStep();
         this.console.printDriversState();
         this.console.printRaceStatus();
      }

   }

   public Race getModel(){
      return this.race;
   }

   public void reset(){
      this.track = null;
      this.drivers = null;
      this.race = null;
      this.console = null;
   }

   
}
