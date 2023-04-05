package ca.mcmaster.pathfinder;

import java.util.HashMap;
import java.util.Map;

public class Edge {
    private int id;
    private Node source;
    private Node destination;
    private Map<String, Object> attributes;

    public Edge(int id, Node source, Node destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.attributes = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getName(), attribute.getValue());
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
