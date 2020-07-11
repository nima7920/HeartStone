package Logic_cards;

import AllCards.FriendlySmith;
import AllCards.*;
import Logic_GUIInterfaces.GameChar;

public interface CardVisitor {
    void polymorphVisit(Polymorph polymorph, GameChar target);
    void friendlySmithVisit(FriendlySmith friendlySmith, GameChar target);
    void dreadscaleVisit(Dreadscale dreadscale,GameChar target);
    void riverCrocoliskVisit(RiverCrocolisk riverCrocolisk,GameChar target);
    void arcaneServantVisit(ArcaneServant arcaneServant,GameChar target);
    void silverbackPatriarchVisit(SilverbackPatriarch silverbackPatriarch,GameChar target);
    void phantomMilitiaVisit(PhantomMilitia phantomMilitia,GameChar target);
    void murlocRaiderVisit(MurlocRaider murlocRaider,GameChar target);
    void stonetuskBoarVisit(StonetuskBoar stonetuskBoar,GameChar target);
    void rottenApplebaumVisit(RottenApplebaum rottenApplebaum,GameChar target);
    void theBlackKnightVisit(TheBlackKnight theBlackKnight,GameChar target);
    void proudDefenderVisit(ProudDefender proudDefender,GameChar target);
    void fireballVisit(Fireball fireball,GameChar target);
    void arcaneMissilesVisit(ArcaneMissiles arcaneMissiles,GameChar target);
    void assassinateVisit(Assassinate assassinate,GameChar target);
    void sinisterStrikeVisit(SinisterStrike sinisterStrike,GameChar target);
    void hellfireVisit(Hellfire hellfire,GameChar target);
    void drainLifeVisit(DrainLife drainLife,GameChar target);
    void arcaneExplosionVisit(ArcaneExplosion arcaneExplosion,GameChar target);
    void ironFistVisit(IronFist ironFist,GameChar target);
    void bookOfSpectersVisit(BookOfSpecters bookOfSpecters,GameChar target);
    void sprintVisit(Sprint sprint,GameChar target);
    void swarmOfLocustsVisit(SwarmOfLocusts swarmOfLocusts,GameChar target);
    void pharaohBlessingVisit(PharaohsBlessing pharaohsBlessing,GameChar target);
    void sathrovarrVisit(Sathrovarr sathrovarr,GameChar target);
    void tombWardenVisit(TombWarden tombWarden,GameChar target);
    void securityRoverVisit(SecurityRover securityRover,GameChar target);
    void curioCollectorVisit(CurioCollector curioCollector,GameChar target);
    void strengthInNumbersVisit(StrengthInNumbers strengthinNumbers,GameChar target);
    void learnDraconicVisit(LearnDraconic learnDraconic,GameChar target);
    void wickedKnifeVisit(WickedKnife wickedKnife,GameChar target);
    void bloodFuryVisit(BloodFury bloodFury,GameChar target);
    void heavyAxeVisit(HeavyAxe heavyAxe,GameChar target);
    void swampKingDredVisit(SwampKingDred swampKingDred,GameChar target);
    void multiShotVisit(MultiShot multiShot,GameChar target);
    void gnomishArmyKnifeVisit(GnomishArmyKnife gnomishArmyKnife,GameChar target);
    void consecrationVisit(Consecration consecration,GameChar target);
    void customMinionVisit(CustomMinion customMinion,GameChar target);
}
