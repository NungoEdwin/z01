import java.util.Objects;

public class CelestialObject{
public double x,y,z;
public String name;
public static final double KM_IN_ONE_AU=150000000;
public CelestialObject(){
name="Soleil";
x=0.0;
y=0.0;
z=0.0;
}
public CelestialObject(String name,double x,double y,double z){
this.x=x;
this.y=y;
this.z=z;
this.name=name;
}
public double getX(){
 return this.x;
}
public void setX(double x){
 this.x=x;
}
public double getY(){
return this.y;
}
public void setY(double y){
		this.y=y;
}
public double getZ(){
return this.z;
}
public void setZ(double z){
		this.z=z;
}
public String getName(){
		return this.name;
}
public void setName(String name){
		this.name=name;
}
public static double getDistanceBetween(CelestialObject a,CelestialObject b){
return (double) Math.sqrt(Math.pow(b.getX()-a.getX(),2)+Math.pow(b.getY()-a.getY(),2)+Math.pow(b.getZ()-a.getZ(),2));
}
public static double getDistanceBetweenInKm(CelestialObject a,CelestialObject b){
return ((double) Math.sqrt(Math.pow(b.getX()-a.getX(),2)+Math.pow(b.getY()-a.getY(),2)+Math.pow(b.getZ()-a.getZ(),2)))*KM_IN_ONE_AU;
}
@Override
public String toString(){
	return String.format("%s is positioned at (%.3f, %.3f, %.3f",this.name,this.x,this.y,this.z);
}
 @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        CelestialObject that = (CelestialObject) obj;
        
        return Double.compare(that.x, x) == 0 &&
               Double.compare(that.y, y) == 0 &&
               Double.compare(that.z, z) == 0 &&
               Objects.equals(name, that.name);
    }
    
    // hashCode method consistent with equals
    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, z);
    }


public static void main(String[] args){
 CelestialObject celestialObject = new CelestialObject();
        CelestialObject earth = new CelestialObject("Terre", 1.0, 2.0, 2.0);
        CelestialObject earth1 = new CelestialObject("Terre", 1.0, 2.0, 2.0);

        System.out.println(earth.toString());
        System.out.println(earth.equals(earth1));
        System.out.println(earth.equals(celestialObject));

        System.out.println(earth.hashCode());
        System.out.println(celestialObject.hashCode());
}
}
