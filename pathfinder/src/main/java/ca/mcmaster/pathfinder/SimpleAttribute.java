package ca.mcmaster.pathfinder;

class SimpleAttribute implements Attribute {
    private String name;
    private Object value;

    public SimpleAttribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
