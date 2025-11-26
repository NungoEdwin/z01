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


public static void main(String[] args){
CelestialObject defaultStar = new CelestialObject();
CelestialObject earth = new CelestialObject("Terre", 1.0, 2.0, 2.0);

        System.out.println(CelestialObject.getDistanceBetween(defaultStar, earth));
        System.out.println(CelestialObject.getDistanceBetweenInKm(defaultStar, earth));
        System.out.println(CelestialObject.KM_IN_ONE_AU);
}
}
