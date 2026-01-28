/*
   Connor Reid
   24 January 2024
   
   Simulation is the class that calls the CalculateBody class, updating the x, y, vx, vy, and mass
   numbers previously stored and then allowing the other classes to have access to those numbers
   in the form of a list.
*/

import java.util.ArrayList;
import java.util.List;

public class Simulation {
   
   // turns the CalculateBody constructor in the CalculateBody class and turns it into a list
   private List<CalculateBody> bodies;
   
   // variable storing how much time passes between each frame of the simulation (or dt)
   private double timeStep;
   
   // constructor : stores all the constructed bodies in CalculatedBody and dt
   public Simulation(double timeStep) {
      this.bodies = new ArrayList<>();
      this.timeStep = timeStep;
   }
   
   // when creating a new body in Main, this method adds it to CalculatedBody
   public void addBody(CalculateBody body) {
      bodies.add(body);
   }
   
   // for all the bodies, set all forces (fx, fy) to zero
   public void update() {
      for (CalculateBody b : bodies) {
         b.resetForce();  // found in CalculatedBody class
      }
      
      for (int i = 0; i < bodies.size(); i++) {           // ensures each body is considered once
         for (int j = i + 1; j < bodies.size(); j++) {    // ensures each PAIR of bodies is considered (see below)
            bodies.get(i).calculateForce(bodies.get(j));  // calculates gravitational force between bodies i and j
            bodies.get(j).calculateForce(bodies.get(i));  // calculates gravitational force between bodies j and i
         }
      }
      
      // for all the bodies, updates calling the method in CalculateBody
      for (CalculateBody b : bodies) {
         b.update(timeStep);
      }
   }
   
   // allows the other classes to get the post-simulated numbers for the bodies
   // (being after each frame and compiling in the Main class)
   public List<CalculateBody> getBodies() {
      return bodies;
   }
}