package AllCards;

import GameCards.CardClass;
import GameCards.Rarity;
import GameCards.Spell;
import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;

import javax.persistence.Entity;

@Entity
public class ArcaneExplosion extends Spell {

    public ArcaneExplosion(){

    }
    public ArcaneExplosion(String cardName, int manaCost, int gemCost, CardClass cardClass, Rarity rarity, String cardDescription) {
      super(cardName,manaCost,gemCost,cardClass,rarity,cardDescription);
    }
    @Override
    public void accept(CardVisitor cardVisitor, GameChar target) {
    cardVisitor.arcaneExplosionVisit(this,target);
    }
}
