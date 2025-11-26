import java.util.Objects;

public class Star extends CelestialObject {
    private double magnitude;
    
    // Default constructor
    public Star() {
        super(); // Calls the parent class default constructor
        this.magnitude = 0.0;
    }
    
    // New constructor with all parameters
    public Star(String name, double x, double y, double z, double magnitude, int mass) {
        super(name, x, y, z, mass); // Calls the parent class constructor
        this.magnitude = magnitude;
    }
    
    // Getter for magnitude
    public double getMagnitude() {
        return magnitude;
    }
    
    // Setter for magnitude
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    // Override toString method
    @Override
    public String toString() {
        return String.format("%s shines at the %.3f magnitude", getName(), magnitude);
    }
    
    // Override equals method to include magnitude
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false; // Check parent class properties
        
        Star star = (Star) obj;
        return Double.compare(star.magnitude, magnitude) == 0;
    }
    
    // Override hashCode method to include magnitude
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), magnitude);
    }
}