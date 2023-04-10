package ca.mcmaster.pathfinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    public void idTesting(){
        Node node1 = new Node(0);

        assertEquals(node1.getId(), 0);
    }

    @Test
    public void attributeTesting(){
        Node node1 = new Node(0);

        node1.addAttribute(new SimpleAttribute("name", "node " + node1.getId()));

        assertEquals(node1.getAttribute("name"), "node 0");
    }
    
}
