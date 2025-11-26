public class Templar extends Character implements Healer,Tank {
    private final int healCapacity;
    private final int shield;

    public Templar(String name, int maxHealth, int healCapacity, int shield, Weapon weapon){
        super(name, maxHealth,weapon);
        this.healCapacity = healCapacity;
        this.shield = shield;
    }
    
    @Override
    public int getHealCapacity(){
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
        int damage = getWeapon() != null ? getWeapon().getDamage() : 6;
        target.takeDamage(damage);
    }

      
   public void takeDamage(int damage) {
        int effectiveDamage = damage < getCurrentHealth() ? Math.max(damage - shield, 0) : damage;
        int newHealth = getCurrentHealth() - effectiveDamage;
        if (newHealth < 0) newHealth = 0;
        setCurrentHealth(newHealth);
    }

    @Override
    public int getShield() {
        return shield;
    }

    @Override
public String toString() {
    return getName() + " is a strong Templar with " + getCurrentHealth() + " HP. He can heal " +
           getHealCapacity() + " HP and has a shield of " + getShield() + ". He has the weapon " +
           getWeapon().getName() + " deals " + getWeapon().getDamage() + " damages.";
}

}