import java.util.Objects;

public class CelestialObject {
    private double x;
    private double y;
    private double z;
    private String name;
    private int mass;
    
    // Public constant for kilometers in one Astronomical Unit
    public static final double KM_IN_ONE_AU = 150000000.0;
    
    // Default constructor - no parameters
    public CelestialObject() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.name = "Soleil";
        this.mass = 0;
    }
    
    // Constructor with four parameters
    public CelestialObject(String name, double x, double y, double z, int mass) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mass = mass;
    }
    
    // Getters
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }
    
    public String getName() {
        return name;
    }
    
    public int getMass() {
        return mass;
    }
    
    // Setters
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public void setZ(double z) {
        this.z = z;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setMass(int mass) {
        this.mass = mass;
    }
    
    // Static method to calculate distance between two celestial objects in AU
    public static double getDistanceBetween(CelestialObject obj1, CelestialObject obj2) {
        double deltaX = obj2.getX() - obj1.getX();
        double deltaY = obj2.getY() - obj1.getY();
        double deltaZ = obj2.getZ() - obj1.getZ();
        
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    
    // Static method to calculate distance between two celestial objects in kilometers
    public static double getDistanceBetweenInKm(CelestialObject obj1, CelestialObject obj2) {
        double distanceInAU = getDistanceBetween(obj1, obj2);
        return distanceInAU * KM_IN_ONE_AU;
    }
    
    // toString method with 3 decimal precision
    @Override
    public String toString() {
        return String.format("%s is positioned at (%.3f, %.3f, %.3f)", 
                           name, x, y, z);
    }
    
    // equals method to compare all properties
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        CelestialObject that = (CelestialObject) obj;
        
        return Double.compare(that.x, x) == 0 &&
               Double.compare(that.y, y) == 0 &&
               Double.compare(that.z, z) == 0 &&
               mass == that.mass &&
               Objects.equals(name, that.name);
    }
    
    // hashCode method consistent with equals
    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, z, mass);
    }
}