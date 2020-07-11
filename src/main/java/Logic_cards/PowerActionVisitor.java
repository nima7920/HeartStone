package Logic_cards;

import AllCards.CustomMinion;
import GameHeros.*;
import Logic_GUIInterfaces.GameChar;
import Logic_GUIInterfaces.PlayGround;
import Logic_game.PlayGroundEditor;

public class PowerActionVisitor implements HeroPowerVisitor {

    private PlayGroundEditor playGroundEditor = PlayGroundEditor.getInstance();

    @Override
    public void mageVisit(Mage mage, GameChar target) {
        target.setHp(target.getHp() - 1);
    }

    @Override
    public void rogueVisit(Rogue rogue, GameChar target) {

    }

    @Override
    public void warlockVisit(Warlock warlock, GameChar target) {

    }

    @Override
    public void hunterVisit(Hunter hunter, GameChar target) {

    }

    @Override
    public void paladinVisit(Paladin paladin, GameChar target) {
        playGroundEditor.summonMinion(new CustomMinion(1, 1), 2);
    }
}
