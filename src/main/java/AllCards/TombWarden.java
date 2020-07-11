package AllCards;

import GameCards.Ability;
import GameCards.CardClass;
import GameCards.Minion;
import GameCards.Rarity;
import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;
import javax.persistence.Entity;

@Entity
public class TombWarden extends Minion {
    public TombWarden() {
    }

    public TombWarden(String cardName, int manaCost, int gemCost, CardClass cardClass, Rarity rarity, String cardDescription, int attack, int hp) {
        super(cardName,manaCost,gemCost,cardClass,rarity,cardDescription,attack,hp);
abilities.add(Ability.Taunt);
    }
    @Override
    public void accept(CardVisitor cardVisitor, GameChar target) {
        cardVisitor.tombWardenVisit(this,target);
    }
}
