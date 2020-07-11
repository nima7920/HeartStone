package AllCards;

import GameCards.CardClass;
import GameCards.Rarity;
import GameCards.Spell;
import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;
import javax.persistence.Entity;

@Entity
public class DrainLife extends Spell {
    public DrainLife() {
    }

    public DrainLife(String cardName, int manaCost, int gemCost, CardClass cardClass, Rarity rarity, String cardDescription) {
        super(cardName,manaCost,gemCost,cardClass,rarity,cardDescription);
        this.targetType=3;
    }
    @Override
    public void accept(CardVisitor cardVisitor, GameChar target) {
        cardVisitor.drainLifeVisit(this,target);
    }

}
