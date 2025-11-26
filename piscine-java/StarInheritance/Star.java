public class Star extends CelestialObject {
    private double magnitude;
    
    // Default constructor
    public Star() {
        super(); // Calls the parent class default constructor
        this.magnitude = 0.0;
    }
    
    // Getter for magnitude
    public double getMagnitude() {
        return magnitude;
    }
    
    // Setter for magnitude
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
}