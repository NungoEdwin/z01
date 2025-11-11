public class Character{
private final int maxHealth;
private int currentHealth;
private final String name;
public Character(String name,int maxHealth){
this.name=name;
this.maxHealth=maxHealth;
this.currentHealth=maxHealth;
}
@Override
public String toString(){
if(currentHealth==0){
return String.format("%s : KO",name);
}
 return String.format("%s : %d/%d",name,currentHealth,maxHealth);
}
public void takeDamage(int sub){
currentHealth=currentHealth-sub;
currentHealth=(currentHealth<=0)?0:currentHealth;
}
public void attack(Character s){
 s.currentHealth=s.currentHealth-9;
 s.currentHealth=(s.currentHealth<=0)?0:s.currentHealth;
}
public int getMaxHealth(){
   return this.maxHealth;
}
public int getCurrentHealth(){
return this.currentHealth;
}
public String getName(){
return this.name;
}
public static void main(String[] args) {
        Character aragorn = new Character("Aragorn", 20);
        Character uruk = new Character("Uruk", 5);
        
        System.out.println(aragorn.toString());
        System.out.println(uruk.toString());
        
        aragorn.attack(uruk);

        System.out.println(uruk.toString());
        
        aragorn.takeDamage(12);

        System.out.println(aragorn.toString());
    }
}
