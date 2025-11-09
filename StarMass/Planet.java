import java.util.Objects;

public class Planet extends CelestialObject {
    private Star centerStar;
    
    // Default constructor - no parameters
    public Planet() {
        super(); // Calls the parent class default constructor
        this.centerStar = new Star(); // Initialize with default Star
    }
    
    // Constructor with parameters
    public Planet(String name, double x, double y, double z, Star centerStar, int mass) {
        super(name, x, y, z, mass); // Calls the parent class constructor
        this.centerStar = centerStar;
    }
    
    // Getter for centerStar
    public Star getCenterStar() {
        return centerStar;
    }
    
    // Setter for centerStar
    public void setCenterStar(Star centerStar) {
        this.centerStar = centerStar;
    }
    
    // Override toString method
    @Override
    public String toString() {
        double distance = CelestialObject.getDistanceBetween(this, centerStar);
        return String.format("%s circles around %s at the %.3f AU", 
                           getName(), centerStar.getName(), distance);
    }
    
    // Override equals method to include centerStar
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false; // Check parent class properties
        
        Planet planet = (Planet) obj;
        return Objects.equals(centerStar, planet.centerStar);
    }
    
    // Override hashCode method to include centerStar
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), centerStar);
    }
}