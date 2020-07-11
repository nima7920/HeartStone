package GameHeros;

import Logic_GUIInterfaces.GameChar;
import Logic_cards.HeroPowerVisitor;

public class Hunter extends Hero {
    public Hunter() {
        setHeroClass(HeroClass.Hunter);
        setHp(30);
        setHeroPower(new HeroPower(HeroClass.Hunter,0,0));
    }

    @Override
    public void accept(HeroPowerVisitor heroPowerVisitor, GameChar target) {
        heroPowerVisitor.hunterVisit(this,target);
    }
}
