package GameHeros;

import Logic_GUIInterfaces.GameChar;
import Logic_cards.HeroPowerVisitor;

public class Paladin extends Hero {
    public Paladin() {
        setHeroClass(HeroClass.Paladin);
        setHp(30);
        setHeroPower(new HeroPower(HeroClass.Paladin,1,2));
    }

    @Override
    public void accept(HeroPowerVisitor heroPowerVisitor, GameChar target) {
        heroPowerVisitor.paladinVisit(this,target);
    }
}
