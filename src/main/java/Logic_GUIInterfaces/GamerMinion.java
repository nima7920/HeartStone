package Logic_GUIInterfaces;

import GameCards.Ability;
import GameCards.Minion;
import Logic_cards.CardVisitor;

import java.util.ArrayList;

// a class to hold the data of a minion which is planted in the playGround
public class GamerMinion extends GameChar {
    private Minion minion;
    private int attack;
    private ArrayList<Ability> abilities;
    // these booleans are created to be used in GUI for drawing proper effects
    private boolean enabled = false;
    private boolean inRush = false;
    private boolean inCharge = false;
    private boolean isTaunt = false;
    private boolean hasDivineShield = false;
    private boolean isLifeSteal = false;
    private boolean isPoisonous = false;
    private boolean isWindFury = false;


    public GamerMinion(Minion minion) {
        this.minion = minion;
        this.name=minion.getCardName();
        this.hp = minion.getHp();
        this.attack = minion.getAttack();
        this.abilities =new ArrayList<>(minion.getAbilities());
//        this.abilities=new ArrayList<>();
        consumeAbilities();
    }

    private void consumeAbilities() {
        enabled = false;
        inRush = false;
        inCharge = false;
        isTaunt = false;
        hasDivineShield = false;
        isLifeSteal = false;
        isPoisonous = false;
        isWindFury = false;

        for (int i = 0; i < abilities.size(); i++) {
            switch (abilities.get(i)) {
                case Rush: {
                    inRush = true;
                    enabled=true;
                    break;
                }
                case Taunt: {
                    isTaunt = true;
                    break;
                }
                case Charge: {
                    inCharge = true;
                    enabled=true;
                    break;
                }
                case WindFury: {
                    isWindFury = true;
                    break;
                }
                case LifeSteal: {
                    isLifeSteal = true;
                    break;
                }
                case Poisonous: {
                    isPoisonous = true;
                    break;
                }
                case DivineShield: {
                    hasDivineShield = true;
                    break;
                }
            }
        }
    }

    public void addAbility(Ability ability) {
        if(!abilities.contains(ability)) {
            abilities.add(ability);
            consumeAbilities();
        }
    }
    public void removeAbility(Ability ability){
        abilities.remove(ability);
        consumeAbilities();
    }

    public void accept(CardVisitor cardVisitor,GameChar target){
        minion.accept(cardVisitor,target);
        this.setHp(minion.getHp());
        this.setAttack(minion.getAttack());
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

    public boolean isInRush() {
        return inRush;
    }

    public boolean isInCharge() {
        return inCharge;
    }

    public boolean getIsTaunt() {
        return isTaunt;
    }

    public boolean getHasDivineShield() {
        return hasDivineShield;
    }

    public boolean getIsLifeSteal() {
        return isLifeSteal;
    }

    public boolean getIsPoisonous() {
        return isPoisonous;
    }

    public boolean getIsWindFury() {
        return isWindFury;
    }
}
