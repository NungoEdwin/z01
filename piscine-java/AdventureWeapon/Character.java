import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    private final int maxHealth;
    private int currentHealth;
    private final String name;
    private final Weapon weapon;
    
    private static List<Character> allCharacters = new ArrayList<>();
    
    public Character(String name, int maxHealth, Weapon weapon) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.weapon = weapon;
        allCharacters.add(this);
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    protected void setCurrentHealth(int health) {
        this.currentHealth = health;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
        if (this.currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
    }
    
    public String getName() {
        return name;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    protected int getAttackDamage(int defaultDamage) {
        if (weapon != null) {
            return weapon.getDamage();
        }
        return defaultDamage;
    }
    
    @Override
    public String toString() {
        String baseString;
        if (currentHealth == 0) {
            baseString = name + " : KO";
        } else {
            baseString = name + " : " + currentHealth + "/" + maxHealth;
        }
        
        if (weapon != null) {
            baseString += ". He has the weapon " + weapon.toString();
        }
        
        return baseString;
    }
    
    // Abstract methods
    public abstract void attack(Character target);
    public abstract void takeDamage(int amount);
    
    public static String printStatus() {
        StringBuilder status = new StringBuilder();
        status.append("------------------------------------------\n");
        
        if (allCharacters.isEmpty()) {
            status.append("Nobody's fighting right now !\n");
        } else {
            status.append("Characters currently fighting :\n");
            for (Character character : allCharacters) {
                status.append(" - ").append(character.toString()).append("\n");
            }
        }
        
        status.append("------------------------------------------\n");
        return status.toString();
    }
    
    public static Character fight(Character first, Character second) {
        while (first.getCurrentHealth() > 0 && second.getCurrentHealth() > 0) {
            // First attacks second
            first.attack(second);
            if (second.getCurrentHealth() == 0) {
                break;
            }
            
            // Second attacks first
            second.attack(first);
            if (first.getCurrentHealth() == 0) {
                break;
            }
        }
        
        // Return the winner (the one who still has health)
        if (first.getCurrentHealth() > 0) {
            return first;
        } else {
            return second;
        }
    }
}