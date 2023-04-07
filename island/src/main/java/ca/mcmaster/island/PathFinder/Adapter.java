package ca.mcmaster.island.PathFinder;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.Node;
import ca.mcmaster.pathfinder.SimpleAttribute;

public class Adapter {

    private Structs.Mesh mesh;
    private Graph graph;

    public Adapter(Structs.Mesh mesh) {
        this.mesh = mesh;
        convertMeshToGraph();
    }

    private void convertMeshToGraph() {
        // Add vertices as nodes
        for (int i = 0; i < mesh.getVerticesCount(); i++) {
            Structs.Vertex vertex = mesh.getVertices(i);
            
            Node node = new Node(i);
            node.addAttribute(new SimpleAttribute("vertex", vertex));
            graph.addNode(node);
        }

        // Add segments as edges
        for (int i = 0; i < mesh.getSegmentsCount(); i++) {
            Structs.Segment segment = mesh.getSegments(i);
            Edge edge = new Edge(i, graph.getNode(segment.getV1Idx()), graph.getNode(segment.getV2Idx()));
            edge.addAttribute(new SimpleAttribute("segment", segment));
            graph.addEdge(edge);
        }
    }
    
}
