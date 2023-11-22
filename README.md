# CS 5010 Semester Project

This repo represents the coursework for CS 5010, the Fall 2022 Edition!

**Name:** Atul Kumar

**Email:** kumar.atu@northeastern.edu

**Preferred Name:** put your preferred name here (how shall we address you)



### About/Overview

Give a general overview of the problem and how your program solve the problem
I have developed a kind of board game that is very loosely inspired by the Doctor Lucky series of gamesLinks to an external site. created by Cheapass GamesLinks to an external site. The goal of this milestone is to start building the model that will be used in our MVC project. This is just the start of the model so be sure to keep your design flexible for future changes.
1. Determine the neighbors of any space. Spaces that share a "wall" are neighbors. Spaces can see into their neighboring spaces.

2. Display information about a specified space in the world. In addition to the name of the space, this information should include what items are in the space and what spaces can be seen from the specified space.

3. Move the target character around the world. The target character starts in space 0 and moves from space to space in order using the ordered, 0-indexed list of spaces found in the world specification. Thus if they are in space 5, they would move to space 6 even if these spaces are not neighbors.

4. Create a graphical representation of the world map (like the image above) in the form of a BufferedImageLinks to an external site.. Learn more about this in the Technical Tidbits later in this description.






### List of Features

List all features that are present in your program.

1. Reads the world requirements.
2. Creates the world with required space information and weapons.
3. Creates graphical representation of the world.
4. Moves the character.
5. Tests the WorldImpl class.
6. Creating view for the World game.
7. Testing Controller.
8. Testing Model.




### How to Run

Describe how to run your program from the JAR file. Describe what arguments are needed (if any) and what they mean.
To run a JAR file on Windows, Mac, or Ubuntu machines, follow these steps:

Verify that Java is installed on your computer
Confirm the computer’s PATH variable includes Java’s \bin directory
Double-click the JAR file if auto-run has been configured
Run the JAR file on the command line or terminal window if a double-clicking fails

Command to run the JAR file from the terminal - 
java -jar cs5010project-MS4.jar





### How to Use the Program

Provide instructions on how to use the functionality in your program. If it is interactive, describe how to interact with your program. Pay particular attention to the parts that are not part of the example runs that you provide.
We just need to rn the driver class to create a example run.





### Example Runs

List any example runs that you have in res/ directory and provide a description of what each example represents or does. Make sure that your example runs are provided as *plain text files*.
My driver class runs the example run. It creates the world, prints all the spaces, and respective weapons cotained in them. It creates the png file 
to show the graphical reprentation of the world. It moves the character from the first space to the destination space specified.

Example runs for MileStone 4 is in the video provided.



### Design/Model Changes

Document what changes you have made from earlier designs. Why did you make those changes? Keep an on-going list using some form of versioning so it is clear when these changes occurred.
I had my Weapon's class outside the Room class. i changed that.
Instead of writting "set", I replaced it with "add".
I was exposing the implementation, I changed that.
I was creating the world from the Driver class, I moved the functionality to WorldImpl class.

Updated design and testing plan has been added into the res/ folder.
TestingPlan&DesignUpdatedMS4.pdf




### Assumptions

List any assumptions that you made during program development and implementations. Be sure that these do not conflict with the requirements of the project.
I performed everything as described in the project requirements.


### Limitations

What limitations exist in your program. This should include any requirements that were *not* implemented or were not working correctly (including something that might work some of the time).
1. My program implements the "Main" class in the driver class and reads the World description file from there and 
generates the world. 
2. My program is not scrollable.



### Citations

Be sure to cite your sources. A good guideline is if you take more than three lines of code from some source, you must include the information on where it came from. Citations should use proper [IEEE citation guidelines](https://ieee-dataport.org/sites/default/files/analysis/27/IEEE Citation Guidelines.pdf) and should include references (websites, papers, books, or other) for ***any site that you used to research a solution***. For websites, this includes name of website, title of the article, the url, and the date of retrieval**.** Citations should also include a qualitative description of what you used, and what you changed/contributed.
https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Run-JAR-file-example-windows-linux-ubuntu
https://www.jetbrains.com/help/idea/class-diagram.html




