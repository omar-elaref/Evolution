package ca.mcmaster.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraPathFinder implements PathFinder {
    @Override
    public List<Edge> findPath(Graph graph, int sourceId, int destinationId) {
        
        // Initialize the distances and visited set
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Edge> previousEdges = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previousEdges.put(node, null);
        }

        distances.put(graph.getNode(sourceId), 0.0);

        // Search for the shortest path
        while (!visited.contains(graph.getNode(destinationId))) {
            // Find the unvisited node with the smallest distance
            Node current = null;
            double currentDistance = Double.POSITIVE_INFINITY;
            for (Node node : graph.getNodes()) {
                if (!visited.contains(node) && distances.get(node) < currentDistance) {
                    current = node;
                    currentDistance = distances.get(node);
                }
            }

            if (current == null) {
                throw new RuntimeException("There is no path between the provided source and destination nodes.");
            }

            visited.add(current);

            // Update the distances of the neighboring nodes
            for (Edge edge : graph.getEdges()) {
                if (edge.getSource() == current || edge.getDestination() == current) {
                    Node neighbor = edge.getSource() == current ? edge.getDestination() : edge.getSource();
                    if (!visited.contains(neighbor)){
                        double newDistance = distances.get(current) + getEdgeWeight(edge);
                        if (newDistance < distances.get(neighbor)) {
                            distances.put(neighbor, newDistance);
                            previousEdges.put(neighbor, edge);
                        }
                    }
                }
            }
        }
        

        // Reconstruct the shortest path
        List<Edge> path = new ArrayList<>();
        Node currentNode = graph.getNode(destinationId);
        while (currentNode != null && currentNode.getId() != sourceId) {
            Edge edge = previousEdges.get(currentNode);
            path.add(0, edge);
            currentNode = edge.getSource() == currentNode ? edge.getDestination() : edge.getSource();
        }

        return path;
    }

    private int getEdgeWeight(Edge edge) {
        int weight = (int) edge.getAttribute("weight");
        return weight != 0 ? weight : 1;
    }
}