package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.*;

public class CentroidsGen {

    public ArrayList<Vertex> createRegularCentroids(double height, double width, double square_size){

        ArrayList<Vertex> centroids = new ArrayList<>();

        for (int y = 10; y < height-10; y += square_size) {
            for (int x = 10; x < width-10; x += square_size) {
                Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0,127").build();
                centroids.add(Vertex.newBuilder().setX(Math.round( x * 100)/100).setY(Math.round( y * 100)/100).addProperties(color).build());
            }
        }

        return centroids;
    }

    public ArrayList<Coordinate> createRandomCentroids(double height, double width, double square_size, int polyNum){

        PrecisionModel PM = new PrecisionModel();
        ArrayList<Coordinate> coords = new ArrayList<>();
        Coordinate temp;
        Random bag = new Random();

        for (int y = 0; y < polyNum; y ++) {
            temp = new Coordinate(bag.nextDouble(width), bag.nextDouble(height));
            PM.makePrecise(temp);
            coords.add(temp);
        }

        return coords;  
    }

    public ArrayList<Vertex> createIrregularFinalCentroids(ArrayList<Coordinate> coords){

        ArrayList<Vertex> centroids = new ArrayList<>();

        for (Coordinate c: coords){
            Vertex v = Vertex.newBuilder().setX(Math.round(c.x)).setY(Math.round(c.y)).build();
            centroids.add(v);
        }

        return centroids;
    }

}