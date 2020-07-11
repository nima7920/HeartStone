package Logic_cards;

import AllCards.*;
import Logic_GUIInterfaces.GameChar;

public class CardDrawVisitor implements CardVisitor {
    @Override
    public void polymorphVisit(Polymorph polymorph, GameChar target) {

    }

    @Override
    public void friendlySmithVisit(FriendlySmith friendlySmith, GameChar target) {

    }

    @Override
    public void dreadscaleVisit(Dreadscale dreadscale, GameChar target) {

    }

    @Override
    public void riverCrocoliskVisit(RiverCrocolisk riverCrocolisk, GameChar target) {

    }

    @Override
    public void arcaneServantVisit(ArcaneServant arcaneServant, GameChar target) {

    }

    @Override
    public void silverbackPatriarchVisit(SilverbackPatriarch silverbackPatriarch, GameChar target) {

    }

    @Override
    public void phantomMilitiaVisit(PhantomMilitia phantomMilitia, GameChar target) {

    }

    @Override
    public void murlocRaiderVisit(MurlocRaider murlocRaider, GameChar target) {

    }

    @Override
    public void stonetuskBoarVisit(StonetuskBoar stonetuskBoar, GameChar target) {

    }

    @Override
    public void rottenApplebaumVisit(RottenApplebaum rottenApplebaum, GameChar target) {

    }

    @Override
    public void theBlackKnightVisit(TheBlackKnight theBlackKnight, GameChar target) {

    }

    @Override
    public void proudDefenderVisit(ProudDefender proudDefender, GameChar target) {

    }

    @Override
    public void fireballVisit(Fireball fireball, GameChar target) {

    }

    @Override
    public void arcaneMissilesVisit(ArcaneMissiles arcaneMissiles, GameChar target) {

    }

    @Override
    public void assassinateVisit(Assassinate assassinate, GameChar target) {

    }

    @Override
    public void sinisterStrikeVisit(SinisterStrike sinisterStrike, GameChar target) {

    }

    @Override
    public void hellfireVisit(Hellfire hellfire, GameChar target) {

    }

    @Override
    public void drainLifeVisit(DrainLife drainLife, GameChar target) {

    }

    @Override
    public void arcaneExplosionVisit(ArcaneExplosion arcaneExplosion, GameChar target) {

    }

    @Override
    public void ironFistVisit(IronFist ironFist, GameChar target) {

    }

    @Override
    public void bookOfSpectersVisit(BookOfSpecters bookOfSpecters, GameChar target) {

    }

    @Override
    public void sprintVisit(Sprint sprint, GameChar target) {

    }

    @Override
    public void swarmOfLocustsVisit(SwarmOfLocusts swarmOfLocusts, GameChar target) {

    }

    @Override
    public void pharaohBlessingVisit(PharaohsBlessing pharaohsBlessing, GameChar target) {

    }

    @Override
    public void sathrovarrVisit(Sathrovarr sathrovarr, GameChar target) {

    }

    @Override
    public void tombWardenVisit(TombWarden tombWarden, GameChar target) {

    }

    @Override
    public void securityRoverVisit(SecurityRover securityRover, GameChar target) {

    }

    @Override
    public void curioCollectorVisit(CurioCollector curioCollector, GameChar target) {
        curioCollector.setAttack(curioCollector.getAttack() + 1);
        curioCollector.setHp(curioCollector.getHp() + 1);
    }

    @Override
    public void strengthInNumbersVisit(StrengthInNumbers strengthinNumbers, GameChar target) {

    }

    @Override
    public void learnDraconicVisit(LearnDraconic learnDraconic, GameChar target) {

    }

    @Override
    public void wickedKnifeVisit(WickedKnife wickedKnife, GameChar target) {

    }

    @Override
    public void bloodFuryVisit(BloodFury bloodFury, GameChar target) {

    }

    @Override
    public void heavyAxeVisit(HeavyAxe heavyAxe, GameChar target) {

    }

    @Override
    public void swampKingDredVisit(SwampKingDred swampKingDred, GameChar target) {

    }

    @Override
    public void multiShotVisit(MultiShot multiShot, GameChar target) {

    }

    @Override
    public void gnomishArmyKnifeVisit(GnomishArmyKnife gnomishArmyKnife, GameChar target) {

    }

    @Override
    public void consecrationVisit(Consecration consecration, GameChar target) {

    }

    @Override
    public void customMinionVisit(CustomMinion customMinion, GameChar target) {

    }
}
