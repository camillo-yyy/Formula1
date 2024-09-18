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

import java.util.List;

/**
 * Interface to rapresent the standard operations needed to simulate a turn based race between Drivers
 *
 */
public interface Race {

  /**
  * Returns the Driver that is currently doing its turn or just completed it (last driver who moved)
  * @return next turn driver 
  */
  public Driver turnDriver();

  /**
  * Returns the Driver that will be scheduled by nextStep() method
  * @return next turn driver 
  */
  public Driver nextTurnDriver();

  /**
  * Advance Race simulation Status in following order:
    NOTSTARTED, STARTED, FINISHED
  * Calculate the next Turn driver then
  * compute next step of race simulation using current Race Rule sets. 
  * 
  * @return Driver involved in the action, null if no step was computed
  */
  Driver nextStep();

  /**
  * @return current race status
  */
  RaceStatus getRaceStatus();

  /**
  * @return current race rule set
  */
  RaceRule getRaceRule();

  /**
  * Return all drivers enlisted for the race
  * @return list of all drivers even one that are not started the race
  */
  List<Driver> getCurrentDrivers();

  /**
  * Return current track used for racing
  * @return track
  */
  Track getTrack();

  /**
  * Returns a race winner if present, null otherwise
  * @return race winner null if race is not finished
  */
  Driver getWinner();


}
