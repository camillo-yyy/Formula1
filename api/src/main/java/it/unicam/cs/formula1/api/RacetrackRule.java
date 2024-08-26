package it.unicam.cs.formula1.api;

public class RacetrackRule implements RaceRule {

   @Override
   public boolean isAllowedToStart(Track t, Driver d) {
      return t.getStartingLine().contains(d.getCar().getPosition());
   }

   @Override
   public boolean isMovementAllowed(Track t, Segment s) {
      if(t.getStartingLine().contains(s.getX())){
         if(t.getStartingLine().contains(s.getY()) || (!t.isOut(s.getY()) && checkCollision(t, s) == 1)) return true;
      }
      else{
         if(!t.isOut(s.getX())) {
            if(checkCollision(t, s) == 1 && s.intersects(t.getEndingLine()) != null) return true;
         }
         else return false;
      }
      if(checkCollision(t, s) < 1) return true;
      else return false;
   }

   private int checkCollision(Track t, Segment s){
      int count = 0;
      for(Segment tt : t.getBorders()){
         if(s.intersects(tt) != null) count++;
      }
      return count;
   }

   @Override
   public boolean triggersWinCon(Track t, Segment s) {
      return t.getEndingLine().intersects(s) != null && !t.isOut(s.getX());
   }
   
}
