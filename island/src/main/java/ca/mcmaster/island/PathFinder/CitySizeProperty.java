package ca.mcmaster.island.PathFinder;

import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.island.properties.PropertyAccess;
import ca.mcmaster.island.properties.Reader;


public class CitySizeProperty implements PropertyAccess<String> {

    private static final String CITY = "citySize";
    
    
    @Override
    public Optional<String> extract(List<Property> props){
        String value = new Reader(props).get(CITY);
        if (value == null)
            return Optional.empty();
        return Optional.of(value);
    }
    
    
}
