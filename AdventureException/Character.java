import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    private final int maxHealth;
    private int currentHealth;
    private final String name;
    private final Weapon weapon;

   private static List<Character> allCharacters = new ArrayList<>();

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    protected void setCurrentHealth(int currentHealth){
        this.currentHealth = currentHealth;
    }

    public  String getName(){
        return name;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Character(String name, int maxHealth, Weapon weapon){
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.weapon = weapon;
        allCharacters.add(this); 
    }


    public Character(String name, int maxHealth){
        this(name, maxHealth, null);
    }

    
    public abstract void takeDamage(int amount);

    public abstract void attack(Character target);

    @Override
    public String toString() {
        if (currentHealth == 0) {
            return name + " : KO";
        }
        return name + " : " + currentHealth + "/" + maxHealth;
    }

    public static String printStatus() {
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

    public static Character fight(Character c1, Character c2) {
        while (c1.getCurrentHealth() > 0 && c2.getCurrentHealth() > 0) {
            c1.attack(c2);
            if (c2.getCurrentHealth() <= 0) break;
            c2.attack(c1);
        }
        return c1.getCurrentHealth() > 0 ? c1 : c2;
    }
}