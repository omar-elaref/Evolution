package ca.mcmaster.island.PathFinder;

import java.util.Optional;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.island.MeshSize;
import ca.mcmaster.island.distance;
import ca.mcmaster.island.Tiles.lakeTile;
import ca.mcmaster.island.Tiles.oceanTile;
import ca.mcmaster.island.properties.ColorProperty;
import java.awt.Color;


public class CityPicker {
    
    public Structs.Polygon picker(Structs.Mesh m){

        Random random = new Random();
        int rand;
        Structs.Polygon p;
        Optional<Color> polygonColor;

        
        ColorProperty colorProperty = new ColorProperty();
        String oceanColorString = new oceanTile().getColor().getValue();
        Color oceanColor = colorProperty.toColor(oceanColorString);
        String lakeColorString = new lakeTile().getColor().getValue();
        Color lakeColor = colorProperty.toColor(lakeColorString);

        do{
            rand = random.nextInt(m.getPolygonsCount());
            p = m.getPolygons(rand);
            polygonColor = colorProperty.extract(p.getPropertiesList());
            
        }while (polygonColor.isPresent() && polygonColor.get().equals(oceanColor) || polygonColor.get().equals(lakeColor));

        return p;

    }

    public Structs.Polygon middleCentroid(Structs.Mesh m){
        MeshSize meshSize = new MeshSize(m);
        distance dis = new distance();
        double distanceOfV = 1000;
        Structs.Polygon poly = m.getPolygons(0);

        for (Structs.Polygon p : m.getPolygonsList()){
            Structs.Segment s = m.getSegments(p.getSegmentIdxs(0));
            Structs.Vertex v = m.getVertices(s.getV1Idx());
            if (dis.centerDistance(v, meshSize.getMaxX()/2, meshSize.getMaxY()/2) < distanceOfV){
                distanceOfV = dis.centerDistance(v, meshSize.getMaxX()/2, meshSize.getMaxY()/2);
                poly = p;
            }
        }
        return poly;
    }


}
