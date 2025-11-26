public class Monster extends Character {
    
    public Monster(String name, int maxHealth) {
        super(name, maxHealth, null);
    }
    
    public Monster(String name, int maxHealth, Weapon weapon) {
        super(name, maxHealth, weapon);
    }
    
    @Override
    public void attack(Character target) {
        int damage = getAttackDamage(7); // Use weapon damage or default 7
        target.takeDamage(damage);
    }
    
    @Override
    public void takeDamage(int amount) {
        int actualDamage = (int) Math.floor(amount * 0.8); // Take 80% of damage (rounded down)
        setCurrentHealth(getCurrentHealth() - actualDamage);
    }
    
    @Override
    public String toString() {
        String baseString;
        if (getCurrentHealth() > 0) {
            baseString = getName() + " is a monster with " + getCurrentHealth() + " HP";
        } else {
            baseString = getName() + " is a monster and is dead";
        }
        
        if (getWeapon() != null) {
            baseString += ". He has the weapon " + getWeapon().toString();
        }
        
        return baseString;
    }
}