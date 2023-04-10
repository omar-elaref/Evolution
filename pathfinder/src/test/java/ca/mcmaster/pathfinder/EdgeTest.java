package ca.mcmaster.pathfinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {

    @Test
    public void idTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        int id = edge.getId();

        assertEquals(id, 0);
    }

    @Test
    public void sourceNodeTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        assertEquals(edge.getDestination(), node1);
    }

    @Test
    public void destinationNodeTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        

        assertEquals(edge.getSource(), node2);
    }

    @Test
    public void attributeTesting(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        edge.addAttribute(new SimpleAttribute("name", "edge " + edge.getId()));

        assertNotEquals(edge.getAttribute("name"), "edge 1");
    }

    @Test
    public void attributeTesting2(){
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Edge edge = new Edge(0, node2, node1);

        edge.addAttribute(new SimpleAttribute("name", "edge " + edge.getId()));

        assertEquals(edge.getAttribute("name"), "edge 0");
    }
    
    
    
}
