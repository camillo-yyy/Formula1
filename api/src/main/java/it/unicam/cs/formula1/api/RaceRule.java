/*
 * Created on Sat Aug 31 2024
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
 * Class to define rules for a race
 */
public interface RaceRule {
   
   /**
    * @param t Track to test
    * @param d Driver to test
    * @return true if driver met requirements to start on given track
    */
   boolean isAllowedToStart(Track t, Driver d);

   /**
    * @param t Track to test
    * @param s Segment which defines movement to test
    * @return true if movement is valid on given track, false otherwise
    */
   boolean isMovementAllowed(Track t, Segment s);

   /**      
    * @param t Track to test
    * @param s Segment which defines movement to test
    * @return true if segment met win condition requirements on given track
    */
   boolean triggersWinCon(Track t, Segment s);

}
