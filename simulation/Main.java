/*
   Connor Reid
   24 January 2024
   
   The Main class is what runs the whole simulation by calling the other classes and handeling user input.
   It extends JPanel such that a visual output of the simulation can be created. The user can ask for a pre-made
   simulation or to create their own, outputting the proper information depending on how they answer each question
   (incorrect inputs allows them to try again).
*/

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;

// allows for the painting of the window (print simulation)
public class Main extends JPanel {
   private Simulation simulation;
   
   // constructor : references the Simulation class
   public Main (Simulation simulation) {
      this.simulation = simulation;
   }
   
   // to animate the window (to create the simulation), painComponent() redraws it
   protected void paintComponent(Graphics g) {
      // clears the window
      super.paintComponents(g);
      
      // create a list of colors so that each body is more easily identifyable
      Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.BLACK, Color.DARK_GRAY};
      
      int index = 0;

      // for-each loop ensures that all the bodies get drawn
      for (CalculateBody body : simulation.getBodies()) {
         g.setColor(colors[index % colors.length]);
         
         int x = (int) (body.x * 1e-9);  // scales down the x positions of the bodies for easier display
         int y = (int) (body.y * 1e-9);  // scales down the y positions of the bodies for easier display
         
         // draws an oval to represent each body
         // getWidth() / 2  and  getHeight() / 2  places (0,0) at the center of the window
         g.fillOval(x + getWidth() / 2, y + getHeight() / 2, 5, 5);
         
         index++;
      }
   }
   
   
   // main method
   public static void main(String[] args) {
      
      // handle user input below
      //create the scanner object
      Scanner scanner = new Scanner(System.in);
      
      // create the array list to clump the x, y, vx, vy, and mass values together
      ArrayList<CalculateBody> bodies = new ArrayList<>();
      double timestep = 1e5;
      
      // welcome the user and ask for number of bodies
      System.out.println("Welcome to the n-body problem simulation. Would you like a custom simulation or pre-set?");
      String option;
      
      // will break once the user inputs a correct response
      while (true) {
         option = scanner.nextLine().toLowerCase().replace("-", "");
         
         if (option.equals("preset")) {
            System.out.println("Which of the following pre-sets would you like to simulate? (Input a number)");
            System.out.println("1: Standard Perpetual 2-Body\t(run at dt = 1e5)\n2: 2-Body Figure-Eight\t\t\t(run at dt = 1e5)");
            System.out.println("3: Initial Stable 3-Body\t\t(run at dt = 1e4)\n4: Chaotic 3-Body\t\t\t\t\t(run at dt = 1e4)");
            System.out.println("5: Chaotic 5-Body\t\t\t\t\t(run at dt = 1e4)");
            
            // create the int that will store the input number for pre-set simulations outside the while-loop
            int num;
            
            // will break if one of the pre-sets is selected
            while (true) {
               // which pre-set simulation does the user wish to simulate
               num = scanner.nextInt();
               if (num == 1) { // standard perpetual 2-body
                  bodies.add(new CalculateBody(-1e11, 0, 0, 1.19e4, 1e30));
                  bodies.add(new CalculateBody(1e11, 0, 0, -1.19e4, 1e30));
                  break; // exits the inner while loop
                  
               } else if (num == 2) { // 2-body figure-eight
                  bodies.add(new CalculateBody(-0.97000436e11, 0.24308753e11, 0.466203685e4, 0.43236573e4, 1e30));
                  bodies.add(new CalculateBody(0.97000436e11, -0.24308753e11, -0.466203685e4, -0.43236573e4, 1e30));
                  break;
                  
               } else if (num == 3) { // initial stable 3-body
                  bodies.add(new CalculateBody(0, -8.660254038e10, 1.732050808e4, 0, 6e30));
                  bodies.add(new CalculateBody(7.5e10, 4.330127019e10, -8.660254038e3, 1.5e4, 6e30));
                  bodies.add(new CalculateBody(-7.5e10, 4.330127019e10, -8.660254038e3, -1.5e4, 6e30));
                  timestep = 1e4;
                  break;
                  
               } else if (num == 4) { // chaotic 3-body
                  bodies.add(new CalculateBody(-1.0e11, 0, 0, -7.5e3, 1e30));
                  bodies.add(new CalculateBody(0.5e11, 8.66025e10, 6.49519e3, 3.75e3, 1e30));
                  bodies.add(new CalculateBody(0.5e11, -8.66025e10, -6.49519e3, 3.75e3, 1e30));
                  timestep = 1e4;
                  break;
                  
               } else if (num == 5) { // chaotic 5-body
                  bodies.add(new CalculateBody(-1e11, 2e10, 1.5e4, -2e4, 5e29));
                  bodies.add(new CalculateBody(1e11, -2e10, -1.2e4, 2.1e4, 5e29));
                  bodies.add(new CalculateBody(0, 1.5e11, -2.5e4, -1.7e4, 5e29));
                  bodies.add(new CalculateBody(-1.2e11, -1e11, 2.8e4, 1.2e4, 5e29));
                  bodies.add(new CalculateBody(9e10, 8e10, -1.8e4, -2.4e4, 5e29));
                  timestep = 1e4;
                  break;
                  
               } else { // if not a number represented above is given
                  System.out.println("Invalid input.\tPlease input a valid number.");
               }
            }
            break; // exit the outside while loop
            
         } else if (option.equals("custom")) {
            System.out.println("Please input the number of bodies you wish to simulate below.");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t*a reminder: too many bodies won't look very good.");
            
            // grab number of bodies wished to simulate
            int numBodies = scanner.nextInt();
            scanner.nextLine();
            
            // tell them to input the values in the correct order
            System.out.println("Alright then. For each body, please input the following information in the specified pattern.");
            System.out.println("x, y, vx, vy, mass\t\t\t\t\t\t\t\t\t\t*a reminder: mass must be positive and non-zero, and try large numbers e.g. 1e5");
            // repeat for all the bodies
            for (int n = 0; n < numBodies; n++) {
               System.out.print("For body " + (n + 1) + ": ");
               String input = scanner.nextLine();
               String[] values = input.split(","); // removes the commas
               
               // check for five values (x, y, vx, vy, mass)
               if (values.length != 5) {
                  System.out.println("Invalid input.\tPlease try again with five input values (you may have missed a comma).");
                  n--; // repeats the last body
                  continue;
               }
               
               // attempt to turn the input values into doubles for each in the ArrayList
               try {
                  double x = Double.parseDouble(values[0].trim());
                  double y = Double.parseDouble(values[1].trim());
                  double vx = Double.parseDouble(values[2].trim());
                  double vy = Double.parseDouble(values[3].trim());
                  double mass = Double.parseDouble(values[4].trim());
                  
                  // add the new body to the simulation
                  bodies.add(new CalculateBody(x, y, vx, vy, mass));
                  
                 // tell user the input is invalid except for values with "e"
               } catch (NumberFormatException e) {
                  System.out.println("Invalid input.\tPlease enter valid numbers.");
               }
            }
            break; // exits the outside while loop
            
         } else { // if neither 'custom' or 'pre-set' is inputted
            System.out.println("Invalid input.");
         }
      }
      System.out.println("Loading...");
      scanner.close();
      
      
      // creates a new simulation with dt being 1e5
      Simulation simulation = new Simulation(timestep);
      
      // takes the bodies from the ArrayList 'bodies' which stores, for each body, 
      // the 5 values needed for the simulation and adds them to the simulation
      for (CalculateBody body : bodies) {
         simulation.addBody(body);
      }
      
      // creates the simulation window 
      JFrame frame = new JFrame("Orbital Simulation");
      
      // new instance of Main and added to the window
      Main mainPanel = new Main(simulation);
      frame.add(mainPanel);
      frame.setSize(500, 500);  // sets the window to be 500 x 500 pixels
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // upon closing the window, the execution ends
      frame.setVisible(true);
      
      // swing timer with 16 milisecond intervals (about 60fps)
      new Timer(16, e -> {
         simulation.update();  // updates the simulation numbers
         mainPanel.repaint();  // redraws the window to display the new numbers
      }).start();
   }
}