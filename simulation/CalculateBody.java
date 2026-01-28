/*
   Connor Reid
   24 January 2024
   
   The CalculateBody class is the math behind the Simulation class--handeling the
   x, y, vx, vy, and mass inputs from the Main class and updating after each iteration
   so that a simulation of the bodies can be outputed.
*/

public class CalculateBody {
   
   public double x, y;     // x, y position
   public double vx, vy;   // x, y velocity (components)
   public double mass;     // mass
   public double fx, fy;   // x, y force (components)
   
   // constructor : stores original x, y, vx, vy, and mass values
   public CalculateBody(double x, double y, double vx, double vy, double mass) {
      this.x = x;
      this.y = y;
      this.vx = vx;
      this.vy = vy;
      this.mass = mass;
   }
   
   // sets the x and y forces to zero
   public void resetForce() {
      fx = 0;
      fy = 0;
   }
   
   // finds the x and y forces
   public void calculateForce(CalculateBody other) {
      double G = 6.67430e-11;        // Gravitational constant
      double dx = other.x - this.x;  // change in x
      double dy = other.y - this.y;  // change in y
      double distance = Math.sqrt(dx * dx + dy * dy);                       // pythagoriean theorem for distance
      double force = (G * this.mass * other.mass) / (distance * distance);  // force due to gravity equation
      fx += force * dx / distance;  // force in x direction
      fy += force * dy / distance;  // force in y direction
   }
   
   // updates velocity and position based on the force
   public void update(double dt) {
      vx += fx / mass * dt;  // Newton's Second Law: F = ma
      vy += fy / mass * dt;  // derived for both x and y because acceleration * time = velocity
      x += vx * dt;          // velocity * time = distance
      y += vy * dt;          // for both x and y to update the body's position
   }
}