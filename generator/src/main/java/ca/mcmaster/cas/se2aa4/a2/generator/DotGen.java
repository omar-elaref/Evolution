package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        
        ArrayList<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        HashSet<Segment> segments = new HashSet<>();
        for(int i = 0; i < 25; i++) {
            for(int j = 0; j < 25; j++){
                if(j != 24){
                    Segment s = Segment.newBuilder().setV1Idx(j + (i*25)).setV2Idx(j+1).build();
                    segments.add(s);
                }
                if(i != 24){
                    Segment s = Segment.newBuilder().setV1Idx(j+(i*25)).setV2Idx(j+25).build();
                    segments.add(s);
                }
            }
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();

    }

}
