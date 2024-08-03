/*
 * Created on Thu May 30 2024
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


public class RacetrackDriver implements Driver {

   private String username;
   private Car personalCar;
   private InputResolver resolver;

   public RacetrackDriver(String u, Car c, InputResolver r){
      this.personalCar = c;
      this.username = u;
      this.resolver = r;
   }

   @Override
   public String getUsername() {
      return this.username;
   }

   @Override
   public Car getCar() {
      return this.personalCar;
   }
   
   public Segment drive(){
      //ExecutorService exec = Executors.newCachedThreadPool();
      //CompletableFuture<Direction> cf = CompletableFuture.supplyAsync(() -> resolver.getNextMove());
      //return this.personalCar.move(cf.join());
      return this.personalCar.move(resolver.getNextMove());
   }

   @Override
   public InputResolver getInputStrategy() {
      return this.resolver;
   }

}
