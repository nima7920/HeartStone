package Logic_passive;

import Logic_game.PlayGroundEditor;

public class PassiveEndTurnVisitor implements PassiveVisitor {
    private PlayGroundEditor playGroundEditor = PlayGroundEditor.getInstance();

    @Override
    public void twiceDrawVisit(TwiceDraw twiceDraw) {
        playGroundEditor.drawOneCard();
    }

    @Override
    public void manaJumpVisit(ManaJump manaJump) {
        playGroundEditor.addMana();
    }

    @Override
    public void offCardsVisit(OffCards offCards) {

    }

    @Override
    public void freePowerVisit(FreePower freePower) {

    }

    @Override
    public void warriorsVisit(Warriors warriors) {

    }
}
