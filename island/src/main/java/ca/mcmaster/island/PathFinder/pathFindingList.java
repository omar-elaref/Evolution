package ca.mcmaster.island.PathFinder;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.Edge;

public interface pathFindingList {
    
    public List<Edge> pathFinderList(List<Integer> cities, Structs.Mesh m);
}
