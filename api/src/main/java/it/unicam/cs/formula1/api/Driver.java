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
package it.unicam.cs.formula1.api;

import it.unicam.cs.formula1.api.geom.Segment;

/**
 * Defines a generic car driver
 */
public interface Driver {
   
   /**
    * @return driver name
    */
   String getUsername();

   /**
    * @return driver's car
    */
   Car getCar();

   /**
    * Return the movement executed by driver's car using InputResolver logic
    * @return movement segment
    */
   public Segment drive();

   /**
    * Return the movement executed by driver's car on passed Direction
    * @return movement segment
    */
   public Segment drive(Direction d);

   /**
    * Return the logic used to calculate how to drive a Car 
    * @return a input strategy
    */
   public InputResolver getInputStrategy();
   
}
