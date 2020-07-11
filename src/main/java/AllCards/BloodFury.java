package AllCards;

import GameCards.CardClass;
import GameCards.Rarity;
import GameCards.Weapon;
import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;
import javax.persistence.Entity;

@Entity
public class BloodFury extends Weapon {
    public BloodFury() {
    }

    public BloodFury(String cardName, String cardDescription, Rarity rarity, CardClass cardClass, int gemCost, int manaCost, int attack, int durability) {
     super(cardName,cardDescription,rarity,cardClass,gemCost,manaCost,attack,durability);
    }

    @Override
    public void accept(CardVisitor cardVisitor, GameChar target) {
        cardVisitor.bloodFuryVisit(this,target);
    }
}
