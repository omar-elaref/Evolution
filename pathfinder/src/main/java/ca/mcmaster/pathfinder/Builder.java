package ca.mcmaster.pathfinder;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Builder {
    private Graph graph;

    public Builder() {
        this.graph = new Graph();
    }

    public Builder addNode(int id) {
        Node node = new Node(id);
        this.graph.addNode(node);
        return this;
    }

    public Node addAttribute(int nodeId, Attribute attribute) {
        Node node = this.graph.getNode(nodeId);
        node.addAttribute(attribute);
        return this.graph.getNode(nodeId);
    }

    public Graph addAttributeToAllNodes(String name, Object value) {
        for (Node node : graph.getNodes()) {
            node.addAttribute(new SimpleAttribute(name, value));
        }
        return this.graph;
    }

    public Builder addEdge(int id, int sourceId, int destinationId) {
        Node source = this.graph.getNode(sourceId);
        Node destination = this.graph.getNode(destinationId);
        Edge edge = new Edge(id, source, destination);
        this.graph.addEdge(edge);
        return this;
    }

    public Graph build() {
        return this.graph;
    }

    public Graph buildAll(int numNodes, int numEdges) {
        Random rand = new Random();
        

        // Add nodes
        for (int i = 1; i <= numNodes; i++) {
            this.addNode(i);
            this.addAttribute(i, new SimpleAttribute("name", "Node " + i));
        }

        // Add edges
        Set<String> addedEdges = new HashSet<>();
        int id = 1;

        for (int i = 1; i < numNodes; i++){
            int sourceId = i;
            int destinationId = i + 1;
            String edgeKey = sourceId + "," + destinationId;
            if (sourceId != destinationId && !addedEdges.contains(edgeKey)) {
                this.addEdge(i, sourceId, destinationId);
                addedEdges.add(edgeKey);
            }
            id = i;
        }
        id++;
        
        while (this.graph.getEdges().size() < numEdges) {
            int sourceId = rand.nextInt(numNodes) + 1;
            int destinationId = rand.nextInt(numNodes) + 1;
            String edgeKey = sourceId + "," + destinationId;
            if (sourceId != destinationId && !addedEdges.contains(edgeKey)) {
                this.addEdge(id, sourceId, destinationId);
                addedEdges.add(edgeKey);
                id++;
            }
        }
        Random random = new Random();
        for (Edge e : graph.getEdges()){
            int rand2 = random.nextInt(12) + 1;
            e.addAttribute(new SimpleAttribute("name", "Edge " + e.getId()));
            e.addAttribute(new SimpleAttribute("weight", rand2));
        }

        return this.graph;
    }

    public Graph starBuilder(List<Node> nodes) {
        // Add the central node
        this.graph.addNode(nodes.get(0));
    
        for(int j = 1; j < nodes.size(); j++){
            this.graph.addNode(nodes.get(j));
        }
    
        // Connect the central node to all other nodes
        
        for (int i = 1; i < nodes.size(); i++) {
            Edge e = new Edge(i, nodes.get(0), nodes.get(i));
            e.addAttribute(new SimpleAttribute("id", e.getId()));
            this.graph.addEdge(e);
            
        }
    
        return this.graph;
    }

    
}
