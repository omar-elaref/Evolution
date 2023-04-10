package ca.mcmaster.pathfinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    
    @Test
    public void numNodeTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);

        Graph graph = new Graph();
        graph.addNode(node2);
        graph.addNode(node1);

        assertEquals(2, graph.getNodes().size());
        
    }

    @Test
    public void numEdgeTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        Graph graph = new Graph();
        graph.addEdge(edge);
        

        assertEquals(1, graph.getEdges().size());
        
    }

    @Test
    public void nodeGettingTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        

        Graph graph = new Graph();
        graph.addNode(node2);
        graph.addNode(node1);
        

        assertEquals(node1, graph.getNode(0));
        
    }

    @Test
    public void edgeGettingTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        Graph graph = new Graph();
        graph.addEdge(edge);
        

        assertEquals(edge, graph.getEdge(1));   
    }



}
