package Logic_passive;

public class TwiceDraw extends Passive{
    public TwiceDraw(){
        setPassiveName("Twice Draw");
        setPassiveText("Draw 2 cards each turn");
    }

    @Override
    public void accept(PassiveVisitor passiveVisitor) {
        passiveVisitor.twiceDrawVisit(this);
    }
}
