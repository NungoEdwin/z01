public class Monster extends Character{
public Monster(String name,int maxHealth){
super(name,maxHealth);

}
public void takeDamage(int sub){
int health;
health=getCurrentHealth()- (int)Math.floor(sub*0.8);
health=(health<=0)?0:health;
setCurrentHealth(health);
}
public void attack(Character s){
//int health;
//  health=s.getCurrentHealth()-7;
//  health=(health<=0)?0:health;
//  s.setCurrentHealth(health);
s.takeDamage(7);
}
@Override
public String toString(){
if(this.getCurrentHealth()>0){
return String.format("%s is a monster with %d HP",this.getName(),this.getCurrentHealth());
}
return String.format("%s is a monster and is dead",this.getName());
}
}
