package Logic_passive;

public class Warriors extends Passive{
    public Warriors(){
        setPassiveName("Warriors");
        setPassiveText("For each dead minion achieve +1 defense for hero");
    }

    @Override
    public void accept(PassiveVisitor passiveVisitor) {
        passiveVisitor.warriorsVisit(this);
    }
}
