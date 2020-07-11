package GameCards;

import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;

import javax.persistence.*;

@Entity
public abstract class Card {
    @Id
    protected String cardName;
    @Column
    protected String cardDescription;
    @Column
    @Enumerated(EnumType.STRING)
    protected Rarity rarity;
    @Column
    @Enumerated(EnumType.STRING)
    protected CardClass cardClass;
    @Column
    protected int gemCost;
    @Column
    protected int manaCost;

    // 0 for no target, 1 for friendly minion , 2 for enemy minion , 3 for enemy character
    @Column
    protected int targetType=0;

    public Card() {

    }

    public Card(String cardName, String cardDescription, Rarity rarity, CardClass cardClass, int gemCost, int manaCost) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.rarity = rarity;
        this.cardClass = cardClass;
        this.gemCost = gemCost;
        this.manaCost = manaCost;
    }

    public abstract void accept(CardVisitor cardVisitor, GameChar target);

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public CardClass getCardClass() {
        return cardClass;
    }

    public void setCardClass(CardClass cardClass) {
        this.cardClass = cardClass;
    }

    public int getGemCost() {
        return gemCost;
    }

    public void setGemCost(int gemCost) {
        this.gemCost = gemCost;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return cardName;
    }
}
