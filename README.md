# n-body_problem_simulator

This project was created as a test for user input handeling and java swing animations
  based on the infamous n-body problem where any number of clestial bodies (typically stars),
  greater than 3 have chaotic motion. This was created as a coding project senior year of
  high school for AP Computer Science A.

## Usage

Upon running the program, the user is given a selection of presets to provide an understanding
  of how to approach custom simulations.
User input goes as follows:
1. choose "pre-set" or "custom"
  a. choose which preset to simulate between 5 options
  b. choose how many bodies to simulate for custom simulations
2. input the following information for each body: (x-position, y-position, x-velocity, y-velocity, mass)
    - *it is recommended that inputs are very large; e.g. 1e5 is the equivalent of 10^5 & 3e5 is the equivalent of 3 * 10^5
3. repeat step 3 until each of the bodies is given the following information
4. enjoy watching
    - *preset scale is 1e-9 to aid in fitting the bodies into the 500px by 500px window & preset timestep is 1e5 (user input not taken to alter these values, edit them in the code)
5. closing the window ends the program
                              
## Program IDE

Written in jGRASP
