import java.util.ArrayList;
import java.util.List;

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
}