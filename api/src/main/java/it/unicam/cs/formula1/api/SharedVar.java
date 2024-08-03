/*
 * Created on Thu Jun 20 2024
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

/**
 * Producer/Consumer Shared variable with 2 waiting queues to set/get generic T value
 */
public class SharedVar<T> {

   private volatile T var;
   private boolean empty;

   public SharedVar(){
      this.empty = true;
   }

   public synchronized T getVar(){
      while(this.isEmpty()){
         try {
            this.wait();
         }
         catch (InterruptedException e){
            System.out.println(e.getLocalizedMessage());
         }
      }

      this.empty = true;
      this.notifyAll();
      return this.var;
   }

   public synchronized void setVar(T d){
      while(!this.isEmpty()){
         try {
            this.wait();
         }
         catch (InterruptedException e){
            System.out.println(e.getLocalizedMessage());
         }
      }

      this.empty = false;
      this.var = d;
      this.notifyAll();

   }

   private boolean isEmpty() {
      return empty;
   }


}
