# Assignment A4: Urbanism

  - Omar El Aref [elarefo@mcmaster.ca]
  

  rationale, and explanations for
extending the library by implementing a new algorithm

### Rationale

I made 2 interfaces as those are the two aspects of the project that I feel can be changed quite a bit in the future. As for Node, Edge and Graph these are concrete and I don't see them changing much when trying to implement new things and hence I made them concrete implementatioin rather than adding a layer of abstraction to them. If I did want to add a layer of abstraction to them in the near future it wouldn't be difficult as they each only have one class and thereofre wouldn't be hard to add the layer of abstraction.


### Reasons for extending the library to island

Dijkstra's path finding algorithm is an extremely efficient algorithm that finds the shortest path. We used this to find the shortest path between cities built. This makes the product run a lot smoother and better.

### How to run just PathFinder
```
mvn install
java -jar pathfinder/pathfinder.jar 
```