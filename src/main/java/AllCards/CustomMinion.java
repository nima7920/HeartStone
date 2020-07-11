package AllCards;

import GameCards.CardClass;
import GameCards.Minion;
import GameCards.Rarity;
import Logic_GUIInterfaces.GameChar;
import Logic_cards.CardVisitor;

public class CustomMinion extends Minion {

public CustomMinion(int attack,int hp){
    this.attack=attack;
    this.hp=hp;
    this.cardName="Custom Minion";
}

    @Override
    public void accept(CardVisitor cardVisitor, GameChar target) {
       cardVisitor.customMinionVisit(this,target);
    }
}
