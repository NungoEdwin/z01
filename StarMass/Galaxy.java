import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy {
    private List<CelestialObject> celestialObjects;
    
    // Constructor with no parameters
    public Galaxy() {
        this.celestialObjects = new ArrayList<>();
    }
    
    // Getter for celestialObjects
    public List<CelestialObject> getCelestialObjects() {
        return celestialObjects;
    }
    
    // Method to add a celestial object to the list
    public void addCelestialObject(CelestialObject celestialObject) {
        celestialObjects.add(celestialObject);
    }
    
    // Method to compute mass repartition by type
    public Map<String, Integer> computeMassRepartition() {
        Map<String, Integer> massRepartition = new HashMap<>();
        massRepartition.put("Star", 0);
        massRepartition.put("Planet", 0);
        massRepartition.put("Other", 0);
        
        for (CelestialObject obj : celestialObjects) {
            String type;
            if (obj instanceof Star) {
                type = "Star";
            } else if (obj instanceof Planet) {
                type = "Planet";
            } else {
                type = "Other";
            }
            
            int currentMass = massRepartition.get(type);
            massRepartition.put(type, currentMass + obj.getMass());
        }
        
        return massRepartition;
    }
}