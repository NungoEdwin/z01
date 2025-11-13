public class Sorcerer extends Character implements Healer{
private final int healCapacity;
public Sorcerer(String name,int maxHealth,int healCapacity){
super(name,maxHealth);
this.healCapacity=healCapacity;
}
public int getHealCapacity(){
 return this.healCapacity;
}
public void heal(Character ch){
if((ch.getCurrentHealth()+this.getHealCapacity()) >= ch.getMaxHealth()){
ch.setCurrentHealth(ch.getMaxHealth());
}else{
 ch.setCurrentHealth(ch.getCurrentHealth()+this.getHealCapacity());
}
}
public void takeDamage(int sub){
int health;
health=getCurrentHealth()-sub;
health=(health<=0)?0:health;
setCurrentHealth(health);
}
public void attack(Character s){
 //hack this is where heals self imenisumbua sana mf!   
this.heal(this);
s.takeDamage(10);
}
@Override
public String toString(){
    //here was building the string if are several names
    String at=this.getName();
    String[] nameArr=at.split(" ");
    StringBuilder an=new StringBuilder();
    int count=0;
    for(String s:nameArr){
    an.append(java.lang.Character.toUpperCase(s.charAt(0)));
    an.append(s.substring(1).toLowerCase());
    count++;
    if(count!=nameArr.length){
       an.append(" ");
    } 
    }
if(this.getCurrentHealth()==0){
return String.format("%s is a dead sorcerer. So bad, it could heal %d HP.",an.toString(),this.getHealCapacity());
}
return String.format("%s is a sorcerer with %d HP. It can heal %d HP.",an.toString(),this.getCurrentHealth(),this.getHealCapacity());
}
}
