package ca.mcmaster.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node{
    private int id;
    String attributrName;
    Object attribute;
    private Map<String, Object> attributes;

    public Node(int id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public int getId() {
        return id;
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