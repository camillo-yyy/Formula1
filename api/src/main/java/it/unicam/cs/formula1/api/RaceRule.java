package it.unicam.cs.formula1.api;
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
