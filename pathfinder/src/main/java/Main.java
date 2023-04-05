import java.util.List;
import java.util.Random;

import ca.mcmaster.pathfinder.Builder;
import ca.mcmaster.pathfinder.DijkstraPathFinder;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.Node;
import ca.mcmaster.pathfinder.PathFinder;
import ca.mcmaster.pathfinder.SimpleAttribute;

public class Main {
    public static void main(String[] args) {
        int numNodes = 6;
        int numEdges = 14;

        if (numEdges < numNodes - 1){
            throw new IllegalArgumentException("Number of edges are too low");
        }

        Builder builder = new Builder();        

        Graph graph = builder.buildAll(numNodes,numEdges);
/* 
        for (Node n : graph.getNodes()){
            System.out.println(n.getId());
            System.out.println(n.getAttributes());
        }
*/
        PathFinder shortest = new DijkstraPathFinder();
        
        List<Edge> shorter = shortest.findPath(graph, 1, 6);
        for (Edge e2 : shorter){
            System.out.println(e2.getSource().getId() + " " + e2.getDestination().getId());
        }
          
    }
}