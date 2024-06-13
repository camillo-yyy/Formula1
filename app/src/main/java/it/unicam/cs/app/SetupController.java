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
package it.unicam.cs.app;

import java.io.IOException;
import java.nio.file.Paths;

import it.unicam.cs.api.Race;
import it.unicam.cs.api.RaceEngine;
import it.unicam.cs.api.RaceStatus;
import it.unicam.cs.api.io.CarLoader;
import it.unicam.cs.api.io.RacetrackCarLoader;
import it.unicam.cs.api.io.RacetrackLoader;
import it.unicam.cs.api.io.TrackLoader;

public class SetupController {

   private ConsoleView console;
   private JavaFXView gui;
   private Race race;
   private TrackLoader trackLoader;
   private CarLoader carLoader;

   public SetupController(){
      this.trackLoader= new RacetrackLoader();
      this.carLoader = new RacetrackCarLoader();
   }

   public void setup(String urlTrack, String urlCar){

      try{
         this.race = new RaceEngine(trackLoader.parse(Paths.get(urlTrack)), carLoader.parse(Paths.get(urlCar)));
         this.race.start();
      }  
      catch(IOException e) {
         System.out.println(e.getMessage());;
      }
      
      this.console = new ConsoleView(this.race);
      this.gui = new JavaFXView();
      this.gui.open();
      this.console.printDriversState();
      this.console.printRaceStatus();

   }

   public void run() throws InterruptedException{
      if(this.race.getRaceStatus() == RaceStatus.NOTSTARTED) return;
      while(this.race.getRaceStatus() != RaceStatus.FINISHED){
         this.race.nextStep();
         this.console.printDriversState();
         this.console.printRaceStatus();
         Thread.sleep(2000);
      }
      console.close();
   }

   
}
