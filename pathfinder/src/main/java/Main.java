import java.util.ArrayList;
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
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i <= numNodes; i++) {
            nodes.add(new Node(i));
        }    

        Graph graph = builder.starBuilder(nodes);
 
    
    }
}