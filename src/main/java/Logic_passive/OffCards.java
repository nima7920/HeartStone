package Logic_passive;

public class OffCards extends Passive {

    public OffCards(){
        setPassiveName("Off Cards");
        setPassiveText("Spend -1 mana for each card");
    }

    @Override
    public void accept(PassiveVisitor passiveVisitor) {
        passiveVisitor.offCardsVisit(this);
    }
}
