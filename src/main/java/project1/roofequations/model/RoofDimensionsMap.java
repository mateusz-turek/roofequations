package project1.roofequations.model;

import java.util.HashMap;

public class RoofDimensionsMap {
    private HashMap<String, Double> dimensions = new HashMap<>();

    public void addRoofDimension(String name, Double value) {
        dimensions.put(name, value);
    }

    @Override
    public String toString() {
        return dimensions.toString();
    }

}



