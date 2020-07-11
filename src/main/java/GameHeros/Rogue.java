package GameHeros;

import Logic_GUIInterfaces.GameChar;
import Logic_cards.HeroPowerVisitor;

public class Rogue extends Hero {
    public Rogue() {
        setHeroClass(HeroClass.Rogue);
        setHp(30);
        setHeroPower(new HeroPower(HeroClass.Rogue,1,3));
    }

    @Override
    public void accept(HeroPowerVisitor heroPowerVisitor, GameChar target) {
        heroPowerVisitor.rogueVisit(this,target);
    }
}
