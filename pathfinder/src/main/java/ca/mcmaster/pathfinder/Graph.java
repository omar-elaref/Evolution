package ca.mcmaster.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, Node> nodes;
    private Map<Integer, Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public void addEdge(Edge edge) {
        edges.put(edge.getId(), edge);
    }

    public Edge getEdge(int id){
        return edges.get(id - 1);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges.values());
    }

    
}
