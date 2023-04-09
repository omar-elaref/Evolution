package ca.mcmaster.island.PathFinder;

import java.util.List;

import com.google.protobuf.Struct;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.Node;
import ca.mcmaster.pathfinder.SimpleAttribute;

public class Adapter {

    private Structs.Mesh mesh;
    public Graph graph = new Graph();

    public Adapter(Structs.Mesh mesh) {
        this.mesh = mesh;
        this.graph = convertMeshToGraph();
    }

    private Graph convertMeshToGraph() {
        
        // Add centroids as nodes
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {    
            
            Structs.Vertex centroid = mesh.getVertices(mesh.getPolygons(i).getCentroidIdx());
            
            Node node = new Node(i);
            node.addAttribute(new SimpleAttribute("vertex", centroid));
            node.addAttribute(new SimpleAttribute("neighbors", mesh.getPolygons(i).getNeighborIdxsList()));
            this.graph.addNode(node);
        }

        // Add connections between neighboring centroids as edges
        int edgeId = 0;
        for (Node n : graph.getNodes()) {
            
            List<Integer> neighbors = (List<Integer>) graph.getNode(n.getId()).getAttribute("neighbors");

            for (int neighbor : neighbors) {
                Edge e = new Edge(edgeId, graph.getNode(n.getId()), graph.getNode(neighbor));
                e.addAttribute(new SimpleAttribute("weight", 1));
                this.graph.addEdge(e);
                edgeId++;
            }
            
        }
        return graph;
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
}