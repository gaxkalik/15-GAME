Author: Armen Balagyozyan
CS120D homework 7

This archive includes the solutions to problems of homework 7
There is packages containing the solutions for all problems

Use "javac <name>.java" command to compile .java files
Then you will be able to run the program by typing "java <name> <parameter>" *note that there is no extension here.
For instance you should run the program with this command: 
		java Puzzle15Matrix "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0"

There is also a Documentation folder for javadoc documentation.


The class Configuration is immutable because there are no mutator methods.
In Puzzle15Matrix class some methods are package private because they are used in another class.
Others are hidden, because there is no need to show them. 
The matrix representation can be uised in UI design or in places, when user must interact
The Array representation is better for online playing or giving API access to state of the game to other applications. The main disadvantage of Array class is that it is dependent of Matrix class in my case. 
 



