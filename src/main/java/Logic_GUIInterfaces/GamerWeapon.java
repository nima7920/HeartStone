package Logic_GUIInterfaces;

import GameCards.Weapon;

public class GamerWeapon {
    private String name;
    private int durability, attack;
    private boolean enabled;
    private Weapon weapon;

    public GamerWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.name=weapon.getCardName();
        this.durability = weapon.getDurability();
        this.attack = weapon.getAttack();
        this.enabled=true;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
