public class Sorcerer extends Character implements Healer{
public int healCapacity;
public Sorcerer(String name,int maxHealth,int healCapacity){
super(name,maxHealth);
this.healCapacity=healCapacity;
}
public int getHealCapacity(){
 return this.healCapacity;
}
public void heal(Character ch){
if(ch.getCurrentHealth()+this.getHealCapacity() > ch.getMaxHealth()){
ch.currentHealth=ch.getMaxHealth();
}else{
 ch.currentHealth=ch.getCurrentHealth()+this.getHealCapacity();
}
}
@Override
public String toString(){
    String at=this.getName();
    StringBuilder an=new StringBuilder();
    an.append(java.lang.Character.toUpperCase(at.charAt(0)));
    an.append(at.substring(1).toLowerCase());
if(this.getCurrentHealth()==0){
return String.format("%s is a dead sorcerer. So bad, it could heal %d HP.",an.toString(),this.getHealCapacity());
}
return String.format("%s is a sorcerer with %d HP. It can heal %d HP.",an.toString(),this.getCurrentHealth(),this.getHealCapacity());
}
public static void main(String[] args) {
        Sorcerer gandalf = new Sorcerer("Gandalf", 20, 5);
        Character frodon = new Character("Frodon", 20);
        Sorcerer saroumane = new Sorcerer("saroumane", 10, 3);

        Character.fight(frodon, saroumane);
        
        gandalf.heal(frodon);

        System.out.println(Character.printStatus());
    }
}
