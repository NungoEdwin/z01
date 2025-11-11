import java.util.ArrayList;
import java.util.List;
public class Character{
private final int maxHealth;
private int currentHealth;
private final String name;
private static List <Character> allCharacters=new ArrayList<>();
public Character(String name,int maxHealth){
this.name=name;
this.maxHealth=maxHealth;
this.currentHealth=maxHealth;
allCharacters.add(this);
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
public static String printStatus(){
StringBuilder sb = new StringBuilder();
    
    sb.append("------------------------------------------\n");

    if (allCharacters.isEmpty()) {
        sb.append("Nobody's fighting right now !\n");
    } else {
        sb.append("Characters currently fighting :\n");
        for (Character c : allCharacters) {
            sb.append(" - ").append(c.toString()).append("\n");
        }
    }

    sb.append("------------------------------------------\n");
    
    return sb.toString();	
}
public static Character fight(Character a,Character b){
    int sub;
if(a.getCurrentHealth()>=b.getCurrentHealth()){
    if(b.getCurrentHealth()%9==0){
        sub=(b.getCurrentHealth()/9 -1 ) *9;
    }else{
        sub=(((int)Math.ceil((double) b.getCurrentHealth()/9))-1)*9;
    }
   a.currentHealth=a.getCurrentHealth() - sub;
   b.currentHealth=0;
   System.out.println(sub);
		return a;
}else if(b.getCurrentHealth()-a.getCurrentHealth()>9){
       if(a.getCurrentHealth()%9==0){
       sub=(a.getCurrentHealth()/9 -1 ) *9;
       }else{
       sub=(((int)Math.ceil((double) a.getCurrentHealth()/9))-1)*9;
       }
   b.currentHealth=b.getCurrentHealth() - sub;
   a.currentHealth=0;
   System.out.println(sub);
   return b;

}
if(b.getCurrentHealth()%9==0){
        sub=(b.getCurrentHealth()/9 -1 ) *9;
    }else{
        sub=(((int)Math.ceil((double) b.getCurrentHealth()/9))-1)*9;
    }
   a.currentHealth=a.getCurrentHealth() - sub;
   b.currentHealth=0;
return a;
}
public static void main(String[] args) {
     System.out.print(Character.printStatus());

        Character aragorn = new Character("Aragorn", 12);
        Character uruk = new Character("Uruk", 18);

        System.out.print(Character.printStatus());

        Character winner = Character.fight(aragorn, uruk);

        System.out.println(winner.toString());
        System.out.print(Character.printStatus());
}
}
