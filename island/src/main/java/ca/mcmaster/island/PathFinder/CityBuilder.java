package ca.mcmaster.island.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.awt.Color;


import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.island.MeshSize;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import ca.mcmaster.pathfinder.Builder;
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

    public Structs.Mesh middleCity(Structs.Mesh m){
        

        Optional<Color> polygonColor;
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        String lakeColorString = new lakeTile().getColor().getValue();
        Color lakeColor = colorProperty.toColor(lakeColorString);
        List<Structs.Polygon> poly = new ArrayList<>();


        MeshSize size = new MeshSize(m);
        double radius = size.getMaxY()/15;
        for(Structs.Polygon p : m.getPolygonsList()){
            distance dis = new distance();
            if(dis.centerDistance(m.getVertices(p.getCentroidIdx()), size.getMaxX()/2, size.getMaxY()/2) < radius){
                polygonColor = colorProperty.extract(p.getPropertiesList());
                if(polygonColor.isPresent() && !(polygonColor.get().equals(oceanColor) || polygonColor.get().equals(lakeColor))){
                    Property prop = Property.newBuilder().setKey("rgb_color").setValue("43,45,47").build();
                    Structs.Polygon newPolygon = Structs.Polygon.newBuilder(p).addProperties(prop).build();
                    poly.add(newPolygon);
                }else{
                    poly.add(p);
                }
            }else{
                poly.add(p);
            }
        }
        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearPolygons().addAllPolygons(poly).build();
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
        this.bigGraph = graph;
        return graph;
    }

    public Structs.Mesh buildsmth(Structs.Mesh m, Graph graph){

        List<Structs.Segment> segments = new ArrayList<>();
        
        for(Edge e : graph.getEdges()){
            Property prop = Property.newBuilder().setKey("road").setValue("road").build();
            Structs.Segment seg = Segment.newBuilder().setV1Idx((int) e.getSource().getAttribute("index")).setV2Idx((int) e.getDestination().getAttribute("index")).addProperties(prop).build();
            segments.add(seg);
        }

        List<Structs.Segment> segm = new ArrayList<>(m.getSegmentsList());

        // Add the new segments
        segm.addAll(segments);

    

        Structs.Mesh newMesh = Structs.Mesh.newBuilder(m).clearSegments().addAllSegments(segm).build();
        return newMesh;
        
    }


}
