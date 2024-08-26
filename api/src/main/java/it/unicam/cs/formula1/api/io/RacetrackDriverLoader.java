package it.unicam.cs.formula1.api.io;

import java.io.IOException;

import it.unicam.cs.formula1.api.Driver;
import it.unicam.cs.formula1.api.InputResolver;
import it.unicam.cs.formula1.api.RacetrackDriver;

public class RacetrackDriverLoader implements DriverLoader{

   private InputResolver h;
   private InputResolver b;
   private CarLoader carL;

   public RacetrackDriverLoader(CarLoader c, InputResolver h, InputResolver b){
      this.carL = c;
      this.h = h;
      this.b = b;
   }

   @Override
   public Driver parse(String s) throws IOException {

      String[] c = s.split(",");
      if(c[3].equals("U"))
      return new RacetrackDriver(c[0], carL.parse(c[1]+","+c[2]),h);
      else
      return new RacetrackDriver(c[0], carL.parse(c[1]+","+c[2]),b);

   }

   
}
