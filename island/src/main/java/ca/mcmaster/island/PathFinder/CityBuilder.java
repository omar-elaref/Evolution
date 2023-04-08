package ca.mcmaster.island.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.pathfinder.Builder;
import ca.mcmaster.pathfinder.Edge;
import ca.mcmaster.pathfinder.Graph;
import ca.mcmaster.pathfinder.Node;
import ca.mcmaster.pathfinder.SimpleAttribute;

public class CityBuilder {

    public List<Integer> cities(Structs.Mesh m, int numCities){

        List<Integer> cities = new ArrayList<>();
        CityPicker picker = new CityPicker();
        cities.add(picker.middleCentroid(m));

        for(int i = 0; i < numCities; i++){
            
            cities.add(picker.picker(m));

        }
        return cities;
    }

    public List<Structs.Vertex> citiesVertices(Structs.Mesh m, List<Integer> cities){
        List<Structs.Vertex> citiesVert = new ArrayList<>();
        for(int i : cities){
            citiesVert.add(m.getVertices(i));
        }
        return citiesVert;
    }
    
    public List<Node> nodeBuilder(List<Structs.Vertex> citiesVert){
        List<Node> nodesList = new ArrayList<>();    
        Random rand = new Random();    
        
        Node central = new Node(1);
        central.addAttribute(new SimpleAttribute("type", "central"));
        central.addAttribute(new SimpleAttribute("vertex", citiesVert.get(0)));
        central.addAttribute(new SimpleAttribute("CitySize", CitySize.CAPITAL_CITY));
        nodesList.add(central);

        for(int i = 1; i < citiesVert.size(); i++){
            Node node = new Node(i + 1);
            node.addAttribute(new SimpleAttribute("type", "notCentral"));
            node.addAttribute(new SimpleAttribute("vertex", citiesVert.get(i)));

            CitySize size;
            do {
                size = CitySize.values()[rand.nextInt(CitySize.values().length)];
            } while (size == CitySize.CAPITAL_CITY);

            node.addAttribute(new SimpleAttribute("CitySize", size));

            nodesList.add(node);
        }
        
        return nodesList;
    }

    public void starBuilding(Structs.Mesh m, int numCities){
        List<Node> nodes = nodeBuilder(citiesVertices(m, cities(m, numCities)));
        Builder builder = new Builder();
        Graph graph = builder.starBuilder(nodes);
        
    }


}
