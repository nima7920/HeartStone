package AllCards;

import GameCards.CardClass;
import GameCards.Rarity;
import GameCards.Spell;
import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;
import javax.persistence.Entity;

@Entity
public class Polymorph extends Spell {
    public Polymorph() {
    }

    public Polymorph(String cardName, int manaCost, int gemCost, CardClass cardClass, Rarity rarity, String cardDescription) {
        super(cardName,manaCost,gemCost,cardClass,rarity,cardDescription);
        this.targetType=2;
    }
    @Override
    public void accept(CardVisitor cardVisitor, GameChar target) {
        cardVisitor.polymorphVisit(this,target);
    }

}
