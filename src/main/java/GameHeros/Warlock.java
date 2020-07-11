package GameHeros;

import Logic_GUIInterfaces.GameChar;
import Logic_cards.HeroPowerVisitor;

public class Warlock extends Hero {
    public Warlock() {
        setHeroClass(HeroClass.Warlock);
        setHp(35);
        setHeroPower(new HeroPower(HeroClass.Warlock,1,2));
    }

    @Override
    public void accept(HeroPowerVisitor heroPowerVisitor, GameChar target) {
        heroPowerVisitor.warlockVisit(this,target);
    }
}
