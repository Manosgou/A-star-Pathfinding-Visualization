# A*(star) Pathfinding Visualization 
This project is a visual representation of the [A* (star)](https://en.wikipedia.org/wiki/A*_search_algorithm) pathfinding algorithm.

# What is A*(star)?
A*(star) is one of the most successful search algorithms to find the shortest path between nodes or graphs. It is an informed search algorithm, as it uses information about path cost and also uses heuristics to find the solution.

<p align="right">-towardsdatascience.com</p>


# Configure the app before you run it
Inside `main.java` you will find the following three variables.

```java
//The ROWS variable holds the number of rows that the grid has.
// By changing its value you will also affect the number of columns because rows = columns.
final int ROWS = 20;

//The allowDiagonal variable according to it's value(true or false) allows diagonal movement.
// Default value is true.
final boolean allowDiagonal =true;

//The animationDelay variable stores the delay(milliseconds) of algorithm's visual representation.
final int animationDelay = 50;
```

# Controls
* Left click to create obstacles.
* Right click to delete obstacles.
* "S" to add the start point.
* "E" to add the end point.
* "R" to reset.(Do not to press the R button when the animation runs,it will lead to an exception.)


# Screenshots & Videos
coming soon ...


#Important note
This project is not yet completed.I' sure some of you,will experience bugs and crashes.There is a lot of things to be rethinked and fixed,so feel free to report any issues.

# Built with
* Java
* [RayLib](https://www.raylib.com/)