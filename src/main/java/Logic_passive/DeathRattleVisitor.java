package Logic_passive;

import Logic_GUIInterfaces.PlayGround;
import Logic_game.PlayGroundEditor;

public class DeathRattleVisitor implements PassiveVisitor {

    @Override
    public void twiceDrawVisit(TwiceDraw twiceDraw) {

    }

    @Override
    public void manaJumpVisit(ManaJump manaJump) {

    }

    @Override
    public void offCardsVisit(OffCards offCards) {

    }

    @Override
    public void freePowerVisit(FreePower freePower) {

    }

    @Override
    public void warriorsVisit(Warriors warriors) {
        PlayGround.getInstance().getFriendlyGamer().getGamerHero().setHp(
                PlayGround.getInstance().getFriendlyGamer().getGamerHero().getHp()+1);
    }
}
