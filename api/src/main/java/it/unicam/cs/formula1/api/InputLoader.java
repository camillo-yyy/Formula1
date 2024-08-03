package it.unicam.cs.formula1.api;

public class InputLoader implements InputResolver, HumanInterface {

   private SharedVar<Direction> t;

   public InputLoader(){
      t = new SharedVar<Direction>();
   }

   public SharedVar<Direction> getSharedVar(){
      return this.t;
   }

   @Override
   public Direction getNextMove() {
      return t.getVar();
   }

   @Override
   public void sendDirection(Direction d) {
      t.setVar(d);
   }
   
}
