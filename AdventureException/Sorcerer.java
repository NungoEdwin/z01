public class Sorcerer extends Character implements Healer{
    private final int healCapacity;

    public  Sorcerer(String name, int maxHealth, int healCapacity, Weapon weapon){
        super(name, maxHealth, weapon);
        this.healCapacity = healCapacity;
    }

    @Override
    public int getHealCapacity() {
        return healCapacity;
    }

    @Override
    public void heal(Character target) {
        int newHealth = target.getCurrentHealth() + healCapacity;
        if (newHealth > target.getMaxHealth()){
            newHealth = target.getMaxHealth();
        }
        target.setCurrentHealth(newHealth);
    }

    public void attack(Character target) {
        this.heal(this);
        int damage = getWeapon() != null ? getWeapon().getDamage() : 10;
        target.takeDamage(damage);
    }

   public void takeDamage(int damage) {
    int newHealth = getCurrentHealth() - damage;
    if (newHealth < 0) newHealth = 0; 
    setCurrentHealth(newHealth);
    }

    @Override
     public String toString() {
    return getName() + " is a sorcerer with " + getCurrentHealth() + " HP. He can heal " +
           getHealCapacity() + " HP. He has the weapon " + getWeapon().getName() +
           " deals " + getWeapon().getDamage() + " damages.";
}
}