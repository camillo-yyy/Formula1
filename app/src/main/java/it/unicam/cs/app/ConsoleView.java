/*
 * Created on Sun Jun 02 2024
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

import java.util.List;

import it.unicam.cs.api.Driver;
import it.unicam.cs.api.Race;
import it.unicam.cs.api.RaceStatus;

public class ConsoleView implements View {
   
   // thread this??
   private Race model;

   public ConsoleView(Race r){
      this.model = r;
   }

   public void printDriversState(){
      List<Driver> dl = model.getCurrentDrivers();

      for (Driver driver : dl) {
         System.out.println(driver.getUsername() + " - " 
         + driver.getCar().getStatus() + "(" + driver.getCar().getPosition().getX() + ", " 
         + driver.getCar().getPosition().getY() + ")");
      }

   }

   public void printRaceStatus(){
      System.out.println(model.getRaceStatus());
      if(model.getRaceStatus() == RaceStatus.FINISHED) {
         if(model.getWinner() == null)
            System.out.println("Race ended without a winner");
         else System.out.println(model.getWinner().getUsername());
      }
   }

   @Override
   public void open() {
   }

   @Override
   public void close() {
      System.out.println("Match is over");
   }
}
