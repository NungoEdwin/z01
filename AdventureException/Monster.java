public class Monster extends Character {

    // Constructor with same parameters as Character
    public Monster(String name, int maxHealth, Weapon weapon) {
        super(name, maxHealth, weapon);
    }
    
    public void attack(Character target) {
        int damage = getWeapon() != null ? getWeapon().getDamage() : 7;
        target.takeDamage(damage);
    }

   @Override
    public void takeDamage(int damage) {
    int actualDamage = (int)(damage * 0.8);
    if (actualDamage > getCurrentHealth()) {
        actualDamage = getCurrentHealth();
    }
    setCurrentHealth(getCurrentHealth() - actualDamage);
    }

    // Override toString to show monster-specific format
    @Override

public String toString() {
    if (getCurrentHealth() <= 0) {
        return getName() + " is a monster and is dead. He has the weapon " + getWeapon().getName() +
               " deals " + getWeapon().getDamage() + " damages.";
    } else {
        return getName() + " is a monster with " + getCurrentHealth() + " HP. He has the weapon " +
               getWeapon().getName() + " deals " + getWeapon().getDamage() + " damages.";
    }
}

}
