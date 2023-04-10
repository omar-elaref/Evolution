package ca.mcmaster.island.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.awt.Color;


import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.island.MeshSize;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.pathfinder.Builder;
import ca.mcmaster.pathfinder.DijkstraPathFinder;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.Node;
import ca.mcmaster.pathfinder.SimpleAttribute;

public class CityBuilder {
    private Graph bigGraph;
    

    public List<Integer> cities(Structs.Mesh m, int numCities){

        List<Integer> cities = new ArrayList<>();
        CityPicker picker = new CityPicker();
        cities.add(picker.middleCentroid(m));

        for(int i = 0; i < numCities; i++){
            
            cities.add(picker.picker(m));

        }
        return cities;
    }

    private List<Node> addingNodeAtt(List<Integer> cities, Structs.Mesh m){
        Adapter adapter = new Adapter(m);
        Graph graph = adapter.graph;
        List<Node> nodeList = new ArrayList<>();
        Random rand = new Random();    

        
        Node central = graph.getNode(neighborPoly(m, cities.get(0)));
        central.addAttribute(new SimpleAttribute("type", "central"));
        central.addAttribute(new SimpleAttribute("CitySize", CitySize.CAPITAL_CITY));
        central.addAttribute(new SimpleAttribute("index", cities.get(0)));
        nodeList.add(central);

        for(int i = 1; i < cities.size(); i++){
            Node node = graph.getNode(cities.get(i));
            node.addAttribute(new SimpleAttribute("type", "notCentral"));
            node.addAttribute(new SimpleAttribute("index", cities.get(i)));

            CitySize size;
            do {
                size = CitySize.values()[rand.nextInt(CitySize.values().length)];
            } while (size == CitySize.CAPITAL_CITY);

            node.addAttribute(new SimpleAttribute("CitySize", size));

            nodeList.add(node);
        }
        return nodeList;
    }

    public Structs.Mesh cityVertices(Structs.Mesh m, List<Integer> cities){
        List<Structs.Vertex> verts = new ArrayList<>();
        Property centralVert = Property.newBuilder().setKey("citySize").setValue(String.valueOf(CitySize.CAPITAL_CITY)).build();
        Structs.Vertex v = Vertex.newBuilder(m.getVertices(cities.get(0))).addProperties(centralVert).build();
        verts.add(v);

        for (int i = 1; i < cities.size(); i++){
            Random rand = new Random();
            CitySize size;
            do {
                size = CitySize.values()[rand.nextInt(CitySize.values().length)];
            } while (size == CitySize.CAPITAL_CITY);
            Property cityVert = Property.newBuilder().setKey("citySize").setValue(String.valueOf(size)).build();
            Structs.Vertex vert = Vertex.newBuilder(m.getVertices(cities.get(i))).addProperties(cityVert).build();
            verts.add(vert);
        }

        List<Structs.Vertex> listOfVert = new ArrayList<>(m.getVerticesList());
        listOfVert.addAll(verts);
        
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearVertices().addAllVertices(listOfVert).build();
        return newMesh;
    }



    private List<Edge> pathFinderList(List<Integer> cities, Structs.Mesh m){
        Adapter adapter = new Adapter(m);
        this.bigGraph = adapter.graph;
        List<Edge> edges = new ArrayList<>();
        

        for(int i = 1; i < cities.size(); i++){
            List<Edge> e = new DijkstraPathFinder().findPath(
                bigGraph,
                bigGraph.getNode(neighborPoly(m, cities.get(0))).getId(),
                bigGraph.getNode(neighborPoly(m, cities.get(i))).getId()
            );            
            edges.addAll(e);
        }
        

        return edges;

    }

      

    private int neighborPoly(Structs.Mesh m, int num){
        for (int i = 0; i < m.getPolygonsCount(); i++){
            Structs.Polygon p = m.getPolygons(i);
            if(p.getCentroidIdx() == num){
                return i;
            }
        }
        return -1;
    }


    public void g(Structs.Mesh m){
        Adapter adapter = new Adapter(m);
        this.bigGraph = adapter.graph;

        for(Edge e : bigGraph.getEdges()){
            System.out.println(e.getAttributes() + " " + e.getSource().getId() + " " + e.getDestination().getId() + " " + m.getPolygons(e.getSource().getId()).getCentroidIdx());
        }
    }

    public Structs.Mesh buildsmth(Structs.Mesh m, int numCities, List<Integer> cities){
        
        List<Edge> edges = pathFinderList(cities, m);

        List<Structs.Segment> segments = new ArrayList<>();
        
        for(Edge e : edges){
            Property prop = Property.newBuilder().setKey("road").setValue("road").build();
            Structs.Segment seg = Segment.newBuilder().setV1Idx(m.getPolygons(e.getSource().getId()).getCentroidIdx()).setV2Idx(m.getPolygons(e.getDestination().getId()).getCentroidIdx()).addProperties(prop).build();
            segments.add(seg);
        }

        List<Structs.Segment> segm = new ArrayList<>(m.getSegmentsList());

        // Add the new segments
        segm.addAll(segments);

    

        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearSegments().addAllSegments(segm).build();
        return newMesh;
        
    }





















    
    public List<Node> nodeBuilder(List<Integer> cities, Structs.Mesh m){

        List<Structs.Vertex> citiesVert = new ArrayList<>();
        for(int i : cities){
            citiesVert.add(m.getVertices(i));
        }

        List<Node> nodesList = new ArrayList<>();    
        Random rand = new Random();    
        
        Node central = new Node(1);
        central.addAttribute(new SimpleAttribute("type", "central"));
        central.addAttribute(new SimpleAttribute("vertex", citiesVert.get(0)));
        central.addAttribute(new SimpleAttribute("CitySize", CitySize.CAPITAL_CITY));
        central.addAttribute(new SimpleAttribute("index", cities.get(0)));
        nodesList.add(central);

        for(int i = 1; i < citiesVert.size(); i++){
            Node node = new Node(i + 1);
            node.addAttribute(new SimpleAttribute("type", "notCentral"));
            node.addAttribute(new SimpleAttribute("vertex", citiesVert.get(i)));
            node.addAttribute(new SimpleAttribute("index", cities.get(i)));

            CitySize size;
            do {
                size = CitySize.values()[rand.nextInt(CitySize.values().length)];
            } while (size == CitySize.CAPITAL_CITY);

            node.addAttribute(new SimpleAttribute("CitySize", size));

            nodesList.add(node);
        }
        
        return nodesList;
    }

    public Graph starBuilding(Structs.Mesh m, int numCities){
        List<Node> nodes = nodeBuilder(cities(m, numCities), m);
        Builder builder = new Builder();
        Graph graph = builder.starBuilder(nodes);
        return graph;
    }

    

}
