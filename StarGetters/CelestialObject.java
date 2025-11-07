public class CelestialObject{
public double x,y,z;
public String name;
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
public static void main(String[] args){
 CelestialObject defaultStar = new CelestialObject();
        System.out.println(defaultStar.getX());
        System.out.println(defaultStar.getY());
        System.out.println(defaultStar.getZ());
        System.out.println(defaultStar.getName());

        defaultStar.setName("Terre");
        defaultStar.setX(0.43);
        defaultStar.setY(0.98);
        defaultStar.setZ(1.43);
        System.out.println(defaultStar.getX());
        System.out.println(defaultStar.getY());
        System.out.println(defaultStar.getZ());
        System.out.println(defaultStar.getName());

}
}
