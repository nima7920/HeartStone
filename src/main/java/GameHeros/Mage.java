package GameHeros;

import Logic_GUIInterfaces.GameChar;
import Logic_cards.HeroPowerVisitor;

public class Mage extends Hero {
    public Mage() {
        setHeroClass(HeroClass.Mage);
        setHp(30);
        setHeroPower(new HeroPower(HeroClass.Mage,2,2));

    }

    @Override
    public void accept(HeroPowerVisitor heroPowerVisitor, GameChar target) {
        heroPowerVisitor.mageVisit(this,target);
    }
}
