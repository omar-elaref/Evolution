package ca.mcmaster.pathfinder;

import java.util.List;

public interface PathFinder {
    List<Edge> findPath(Graph graph, int sourceId, int destinationId);
}
