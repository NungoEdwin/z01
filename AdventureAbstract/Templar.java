public class Templar extends Character implements Healer,Tank{
private final int healCapacity;
private final int shield;
public Templar(String name,int maxHealth,int healCapacity,int shield){
    super(name, maxHealth);
    this.healCapacity=healCapacity;
    this.shield=shield;
}
public int getHealCapacity(){
 return this.healCapacity;
}
public void heal(Character ch){
if((ch.getCurrentHealth()+this.getHealCapacity()) > ch.getMaxHealth()){
ch.setCurrentHealth(ch.getMaxHealth());
}else{
 ch.setCurrentHealth(ch.getCurrentHealth()+this.getHealCapacity());
}
}
public int getShield(){
    return this.shield;
}
public void takeDamage(int sub){
    int health;
health=getCurrentHealth()-(sub-getShield());
health=(health<=0)?0:health;
setCurrentHealth(health);
}
public void attack(Character s){
//int health;
this.heal(this);
//  health=s.getCurrentHealth()-6;
//  health=(health<=0)?0:health;
//  s.setCurrentHealth(health);
s.takeDamage(6);
}
@Override
public String toString(){
    if(this.getCurrentHealth()==0){
        return String.format("%s has been beaten, even with its %d shield. So bad, it could heal %d HP.",this.getName(),this.getShield(),this.getHealCapacity());
    }
    return String.format("%s is a strong Templar with %d HP. It can heal %d HP and has a shield of %d.",this.getName(),this.getCurrentHealth(),this.getHealCapacity(),this.getShield());
}
}