package ca.mcmaster.pathfinder;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DijkstraTest {
    
    @Test
    public void shortestPathTest(){

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        Edge edge1_2 = new Edge(1, node1, node2);
        Edge edge1_3 = new Edge(2, node1, node3);
        Edge edge2_3 = new Edge(3, node2, node3);

        edge1_2.addAttribute(new SimpleAttribute("weight", 100));
        edge1_3.addAttribute(new SimpleAttribute("weight", 10));
        edge2_3.addAttribute(new SimpleAttribute("weight", 5));


        Graph graph = new Graph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);

        graph.addEdge(edge1_2);
        graph.addEdge(edge1_3);
        graph.addEdge(edge2_3);

        PathFinder shortest = new DijkstraPathFinder();
        shortest.findPath(graph, 1, 2);
        int cost = pathCost(shortest.findPath(graph, 1, 2));
        assertEquals(cost, 15);

    }

    @Test
    public void shortestPathTest2(){

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        Edge edge1_2 = new Edge(1, node1, node2);
        Edge edge1_3 = new Edge(2, node1, node3);
        Edge edge2_3 = new Edge(3, node2, node3);

        edge1_2.addAttribute(new SimpleAttribute("weight", 10));
        edge1_3.addAttribute(new SimpleAttribute("weight", 10));
        edge2_3.addAttribute(new SimpleAttribute("weight", 5));


        Graph graph = new Graph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);

        graph.addEdge(edge1_2);
        graph.addEdge(edge1_3);
        graph.addEdge(edge2_3);

        PathFinder shortest = new DijkstraPathFinder();
        shortest.findPath(graph, 1, 2);
        int cost = pathCost(shortest.findPath(graph, 1, 2));
        assertEquals(cost, 10);
    }

    private int pathCost(List<Edge> edges){
        int totalWeight = 0;

        for (Edge e : edges){
            totalWeight += (int) e.getAttribute("weight");
        }
        return totalWeight;
    }  
}
