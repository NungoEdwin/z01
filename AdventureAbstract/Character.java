import java.util.ArrayList;
import java.util.List;
public  abstract class Character{
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
abstract void takeDamage(int sub);
abstract void attack(Character s);
// public void takeDamage(int sub){
// currentHealth=currentHealth-sub;
// currentHealth=(currentHealth<=0)?0:currentHealth;
// }
// public void attack(Character s){
//  s.currentHealth=s.currentHealth-9;
//  s.currentHealth=(s.currentHealth<=0)?0:s.currentHealth;
// }
public int getMaxHealth(){
   return this.maxHealth;
}
public int getCurrentHealth(){
return this.currentHealth;
}
protected void setCurrentHealth(int a){
    this.currentHealth=a;
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
    if(a.getCurrentHealth()==0){
        return b;
    }else if(b.getCurrentHealth()==0){
        return a;
    }
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
}else if(b.getCurrentHealth()>9){
       if(a.getCurrentHealth()%9==0){
       sub=(a.getCurrentHealth()/9 -1 ) *9;
       }else{
       sub=(((int)Math.ceil((double) a.getCurrentHealth()/9)))*9;
       }
   b.currentHealth=b.getCurrentHealth() - sub;
   a.currentHealth=0;
   System.out.println(sub);
   return b;

};
if(b.getCurrentHealth()%9==0){
        sub=(b.getCurrentHealth()/9 -1 ) *9;
    }else{
        sub=(((int)Math.ceil((double) b.getCurrentHealth()/9))-1)*9;
    }
   a.currentHealth=a.getCurrentHealth() - sub;
   b.currentHealth=0;
   System.out.println(sub);
return a;
}
}
