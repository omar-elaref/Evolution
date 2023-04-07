package ca.mcmaster.island.PathFinder;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class CityBuilder {

    public void cities(Structs.Mesh m, int numCities){

        List<Integer> cities = new ArrayList<>();
        CityPicker picker = new CityPicker();
        cities.add(picker.middleCentroid(m));

        for(int i = 0; i < numCities; i++){
            
            cities.add(picker.picker(m));

        }


    }
    
}
