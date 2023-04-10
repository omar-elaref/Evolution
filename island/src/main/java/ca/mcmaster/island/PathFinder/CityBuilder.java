package ca.mcmaster.island.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.pathfinder.DijkstraPathFinder;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;

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


    public Structs.Mesh buildPath(Structs.Mesh m, List<Integer> cities){
        
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

}