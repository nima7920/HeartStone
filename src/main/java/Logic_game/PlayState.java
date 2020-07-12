package Logic_game;

import GameCards.*;
import GameHeros.Hero;
import GamePlayer.PlayerHandler;
import Logic_GUIInterfaces.*;
import Logic_cards.CardPlayedVisitor;
import Logic_cards.PowerActionVisitor;
import Logic_menus.State;
import Logic_passive.*;

import java.util.ArrayList;
import java.util.Collections;

public class PlayState extends State {

    // 0 for self play,1 for prepared game , 2 for computer mode
    private int playMode;
    private Passive currentPassive;
    private ArrayList<Passive> allPassives;
    private PlayGround playGround = PlayGround.getInstance();
    private DeckReader deckReader = new DeckReader();

    public PlayState() {
        initAllPassives();

    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
        loadPlayGround();
    }

    private void loadPlayGround() {
        // for now , we just complete this part
        if (playMode == 0) {
            loadDefaultGame();
        } else if (playMode == 1) {


        } else if (playMode == 2) {


        }

    }

    private void loadDefaultGame() {
        deckReader.loadDefaultEnemy(playGround.getOpponentGamer());
        loadFriendlyGamer();
    }

    // this method loads friendly gamer from player profile
    private void loadFriendlyGamer() {
        playGround.getCurrentGamer().setDeckCards(PlayerHandler.getInstance().getCurrentDeck().getCards());
        playGround.getCurrentGamer().setGamerHero(
                new GamerHero(Hero.getHeroFromName(playerHandler.getCurrentDeck().getHeroClass().toString())));
        playGround.getCurrentGamer().setGamerHeroPower(
                new GamerHeroPower(Hero.getHeroFromName(playerHandler.getCurrentDeck().getHeroClass().toString()).getHeroPower()));
        ;
    }

    private void loadFromDeckReader() {


    }


    private void initAllPassives() {
        allPassives = new ArrayList<>();
        allPassives.add(new FreePower());
        allPassives.add(new OffCards());
        allPassives.add(new Warriors());
        allPassives.add(new ManaJump());
        allPassives.add(new TwiceDraw());
        Collections.shuffle(allPassives);
    }

    // checking whether initialization is possible
    boolean isCurrentDeckReady() {
        if (playerHandler.getCurrentDeck() != null && playerHandler.getCurrentDeck().getCards().size() >= 15) {
            return true;
        } else {
            return false;
        }
    }

    // passives
    public String getPassiveText(int i) {
        String str = allPassives.get(i).getPassiveName() + ": " + allPassives.get(i).getPassiveText();
        return str;
    }

    public void selectPassive(int i) {
        currentPassive = allPassives.get(i);
    }

    public ArrayList<String> getFirstCards() {
        ArrayList<String> firstCards = new ArrayList<>();
        firstCards.add(playGround.getCurrentGamer().getDeckCards().get(0).getCardName());
        firstCards.add(playGround.getCurrentGamer().getDeckCards().get(1).getCardName());
        firstCards.add(playGround.getCurrentGamer().getDeckCards().get(2).getCardName());
        return firstCards;
    }

    // methods for card selection panel
    public void changeCards(boolean[] selectedCards) {
        for (int i = 0; i <3 ; i++) {
            if (selectedCards[i]) {
                Collections.swap(playGround.getCurrentGamer().getDeckCards(), i, i+3);
            }
        }
//        if (selectedCards[0]) {
//            Collections.swap(playGround.getCurrentGamer().getDeckCards(), 0, 3);
//        }
//        if (selectedCards[1]) {
//            Collections.swap(playGround.getCurrentGamer().getDeckCards(), 1, 4);
//        }
//        if (selectedCards[2]) {
//            Collections.swap(playGround.getCurrentGamer().getDeckCards(), 2, 5);
//        }
        initHands();
    }

    private void initHands() {
        for (int i = 0; i <3 ; i++) {
            playGround.getCurrentGamer().getHandCards()[i] = playGround.getCurrentGamer().getDeckCards().get(i);
            playGround.getCurrentGamer().getDeckCards().remove(i);

            playGround.getOpponentGamer().getHandCards()[i] = playGround.getOpponentGamer().getDeckCards().get(i);
            playGround.getOpponentGamer().getDeckCards().remove(i);
        }
//        playGround.getCurrentGamer().getHandCards()[0] = playGround.getCurrentGamer().getDeckCards().get(0);
//        playGround.getCurrentGamer().getDeckCards().remove(0);
//        playGround.getCurrentGamer().getHandCards()[1] = playGround.getCurrentGamer().getDeckCards().get(1);
//        playGround.getCurrentGamer().getDeckCards().remove(1);
//        playGround.getCurrentGamer().getHandCards()[2] = playGround.getCurrentGamer().getDeckCards().get(2);
//        playGround.getCurrentGamer().getDeckCards().remove(2);
//
//        playGround.getOpponentGamer().getHandCards()[0] = playGround.getOpponentGamer().getDeckCards().get(0);
//        playGround.getOpponentGamer().getDeckCards().remove(0);
//        playGround.getOpponentGamer().getHandCards()[1] = playGround.getOpponentGamer().getDeckCards().get(1);
//        playGround.getOpponentGamer().getDeckCards().remove(1);
//        playGround.getOpponentGamer().getHandCards()[2] = playGround.getOpponentGamer().getDeckCards().get(2);
//        playGround.getOpponentGamer().getDeckCards().remove(2);
    }

// playground methods
    /*
       main attack method for handling minion attacks and hero attacks
       if index is -1 ,means that hero is attacking (or being attacked )

       */
// it is guarantied that characters with given indices exist (corresponding objects are not null )
    /*
    returns 1 if attack is completed , 0 if minion is not active or hero doesn't have active weapon,
    -1 if enemy has taunt , -2 for rush error

     */

    // end game and dead minions must be handled
    public int attack(int originIndex, int targetIndex) {
        // checking attack conditions
        if (!enabledCheck(originIndex))
            return 0;
        if (!tauntCheck(targetIndex))
            return -1;
        if (!rushCheck(originIndex, targetIndex))
            return -2;

        if (originIndex == -1 && targetIndex == -1)
            heroToHeroAttack();
        else if (originIndex == -1)
            heroToMinionAttack(targetIndex);
        else if (targetIndex == -1)
            minionToHeroAttack(originIndex);
        else
            minionToMinionAttack(originIndex, targetIndex);

        cleanDeadObjects();
        checkEndGame();
        return 1;
    }

    // in following voids , divine shield,poisonous and life steal must be applied
// , and enable,rush,charge,weapon (if hero) must be disabled
    private void minionToMinionAttack(int originIndex, int targetIndex) {
        GamerMinion origin = playGround.getCurrentGround()[originIndex],
                target = playGround.getOpponentGround()[targetIndex];

        if (target.getHasDivineShield()) {
            if (!origin.getHasDivineShield()) { // target has divine shield but origin doesn't
                origin.setHp(origin.getHp() - target.getAttack());
            }
        } else {
            if (origin.getHasDivineShield()) {
                if (origin.getIsPoisonous()) { // origin has divine shield and poisonous , target doesn't has divine shield
                    target.setHp(0);
                } else { // origin has divine shield but doesn't have poisonous , target doesn't have divine shield
                    target.setHp(target.getHp() - origin.getAttack());
                }

            } else {
                if (origin.getIsPoisonous()) { // non have divine shield , origin has poisonous
                    target.setHp(0);
                } else { // non has divine shield , origin doesn't have poisonous
                    target.setHp(target.getHp() - origin.getAttack());
                }
                origin.setHp(origin.getHp() - target.getAttack());
            }
        }

        // handling life steal
        if (origin.getIsLifeSteal())
            playGround.getCurrentGamer().getGamerHero().setHp(
                    playGround.getCurrentGamer().getGamerHero().getHp() + origin.getAttack());
        cleanMinionToMinionAbilities(origin, target);
    }

    private void heroToMinionAttack(int targetIndex) {
        GamerMinion target = playGround.getOpponentGround()[targetIndex];
        GamerHero origin = playGround.getCurrentGamer().getGamerHero();
        if (!target.getHasDivineShield()) {
            target.setHp(target.getHp() - origin.getGamerWeapon().getAttack());
        }
        origin.setHp(origin.getHp() - target.getAttack());
        cleanHeroToMinionAbilities(target);
    }

    private void minionToHeroAttack(int originIndex) {
        GamerMinion origin = playGround.getCurrentGround()[originIndex];
        GamerHero target = playGround.getOpponentGamer().getGamerHero();

        target.setHp(target.getHp() - origin.getAttack());
        // handling life steal
        if (origin.getIsLifeSteal())
            playGround.getCurrentGamer().getGamerHero().setHp(
                    playGround.getCurrentGamer().getGamerHero().getHp() + origin.getAttack());
        cleanMinionToHeroAbilities(origin);
    }

    private void heroToHeroAttack() {
        GamerHero origin = playGround.getCurrentGamer().getGamerHero(),
                target = playGround.getOpponentGamer().getGamerHero();

        target.setHp(target.getHp() - origin.getGamerWeapon().getAttack());
        cleanHeroToHeroAbilities();
    }

    // returns true if it is possible to attack and false otherwise
    private boolean tauntCheck(int enemyIndex) {
        GamerMinion[] opponentGround = playGround.getOpponentGround();
        if (enemyIndex >= 0 && opponentGround[enemyIndex].getIsTaunt())
            return true;
        for (int i = 0; i < 7; i++) {
            if (i != enemyIndex && opponentGround[i] != null) {
                if (opponentGround[i].getIsTaunt())
                    return false;
            }
        }
        return true;
    }

    // returns true if it is possible to attack and false otherwise
    private boolean rushCheck(int originIndex, int targetIndex) {
        if (targetIndex > -1 || originIndex == -1)
            return true;
        if (playGround.getCurrentGround()[originIndex].isInRush())
            return false;
        return true;
    }

    private boolean enabledCheck(int originIndex) {
        if (originIndex == -1) {
            if (playGround.getCurrentGamer().getGamerHero().getGamerWeapon().isEnabled())
                return true;
            else
                return false;
        } else {
            if (playGround.getCurrentGround()[originIndex].isEnabled())
                return true;
            else
                return false;
        }
    }

    // methods for cleaning abilities after attack is done
    private void cleanHeroToHeroAbilities() {
        playGround.getCurrentGamer().getGamerHero().getGamerWeapon().setEnabled(false);
    }

    private void cleanHeroToMinionAbilities(GamerMinion target) {
        playGround.getCurrentGamer().getGamerHero().getGamerWeapon().setEnabled(false);
        target.removeAbility(Ability.DivineShield);

    }

    private void cleanMinionToHeroAbilities(GamerMinion origin) {
        origin.removeAbility(Ability.Rush);
        origin.removeAbility(Ability.Charge);
        origin.setEnabled(false);
    }

    private void cleanMinionToMinionAbilities(GamerMinion origin, GamerMinion target) {
        origin.removeAbility(Ability.Rush);
        origin.removeAbility(Ability.Charge);
        origin.removeAbility(Ability.DivineShield);
        origin.setEnabled(false);
        target.removeAbility(Ability.DivineShield);
    }

    private void cleanDeadObjects() {
        for (int i = 0; i < 7; i++) {
            if (playGround.getEnemyGround()[i] != null && playGround.getEnemyGround()[i].getHp() <= 0)
                playGround.getEnemyGround()[i] = null;
            if (playGround.getFriendlyGround()[i] != null && playGround.getFriendlyGround()[i].getHp() <= 0) {
                playGround.getFriendlyGround()[i] = null;
                currentPassive.accept(new DeathRattleVisitor());
            }
        }
        if (playGround.getEnemyGamer().getGamerHero().getGamerWeapon() != null &&
                playGround.getEnemyGamer().getGamerHero().getGamerWeapon().getDurability() <= 0)
            playGround.getEnemyGamer().getGamerHero().setGamerWeapon(null);
        if (playGround.getFriendlyGamer().getGamerHero().getGamerWeapon() != null &&
                playGround.getFriendlyGamer().getGamerHero().getGamerWeapon().getDurability() <= 0)
            playGround.getFriendlyGamer().getGamerHero().setGamerWeapon(null);
    }

    // returns 0 if game is not over , -1 for enemy win and 1 for friendly win
    private int checkEndGame() {
        if (playGround.getFriendlyGamer().getGamerHero().getHp() <= 0)
            return -1;
        if (playGround.getEnemyGamer().getGamerHero().getHp() <= 0)
            return 1;
        return 0;
    }

    // methods for card play
    public int getCardTargetType(int cardIndex) {
        return playGround.getCurrentGamer().getHandCards()[cardIndex].getTargetType();
    }

    // returns 1 if successful, 0 if mana is not enough , -1 if card is minion and hand is full
    public int playCard(int cardIndex, int placeIndex, int targetIndex) {
        Card cardToBePlayed = playGround.getCurrentGamer().getHandCards()[cardIndex];
        // checking ground
        if (isGroundFull(cardToBePlayed)) {
            return -1;
        }
        // checking mana (passive and special power is applied )
        // if mana was enough , it would be decreased from player
        if (!isManaEnough(cardToBePlayed)) {
            return 0;
        }
        // card will be played , visitors must be applied
        cardToBePlayed.accept(new CardPlayedVisitor(), getTargetOfCard(cardToBePlayed, targetIndex));

        if (cardToBePlayed instanceof Minion)
            summonMinion(cardToBePlayed, placeIndex);
        playGround.getCurrentGamer().getHandCards()[cardIndex] = null; // removing card from hand
        cleanDeadObjects();
        return 1;
    }

    // checks whether player has enough mana to play a card
    private boolean isManaEnough(Card card) {
        int manaDiffrence = 0;

        if (currentPassive instanceof OffCards)
            manaDiffrence++;

        if (card instanceof Spell && playGround.getCurrentGamer().getGamerHero().getName().equals("Mage"))
            manaDiffrence += 2;

        if (playGround.getCurrentGamer().getGamerHero().getName().equals("Rogue") &&
                card.getCardClass() != CardClass.Rogue && card.getCardClass() != CardClass.Neutral)
            manaDiffrence += 2;

        if (playGround.getCurrentGamer().getMana() >= (card.getManaCost() - manaDiffrence)) {
            int cost = Math.max(card.getManaCost() - manaDiffrence, 0);
            playGround.getCurrentGamer().setMana(playGround.getCurrentGamer().getMana() - cost);
            return true;

        } else {
            System.out.println(playGround.getCurrentGamer().getMana() + " " + manaDiffrence);
            return false;
        }
    }

    private boolean isGroundFull(Card card) {
        if (card instanceof Spell || card instanceof Weapon)
            return false;
        for (int i = 0; i < 7; i++) {
            if (playGround.getCurrentGround()[i] == null)
                return false;
        }
        return true;
    }

    private GameChar getTargetOfCard(Card card, int targetIndex) {
        switch (card.getTargetType()) {
            case 0: { // no target
                return null;
            }
            case 1: { // friendly minion
                return playGround.getCurrentGround()[targetIndex];
            }
            case 2: { // enemy minion
                return playGround.getOpponentGround()[targetIndex];
            }
            case 3: {
                if (targetIndex == -1)
                    return playGround.getOpponentGamer().getGamerHero();
                else
                    return playGround.getOpponentGround()[targetIndex];
            }
        }
        return null;
    }

    // a method used for summon ( called after playing a minion)
    private void summonMinion(Card card, int placeIndex) {
        if (playGround.getCurrentGround()[placeIndex] == null)
            playGround.getCurrentGround()[placeIndex] = new GamerMinion((Minion) card);
        else { // opening space for new card
            if (placeIndex == 0) {
                shiftGround(0,1);
                playGround.getCurrentGround()[placeIndex] = new GamerMinion((Minion) card);
            } else if (placeIndex == 6) {
                if (playGround.getCurrentGround()[5] != null)
                    shiftGround(5,-1);
                playGround.getCurrentGround()[placeIndex - 1] = new GamerMinion((Minion) card);
            } else {
                shiftGround(placeIndex,1);
                if (playGround.getCurrentGround()[placeIndex] != null) {
                    shiftGround(placeIndex - 1,-1);
                    playGround.getCurrentGround()[placeIndex - 1] = new GamerMinion((Minion) card);
                } else
                    playGround.getCurrentGround()[placeIndex] = new GamerMinion((Minion) card);
            }
        }
        applySummonVisitors(playGround.getCurrentGround()[placeIndex]);
    }

    // the aim of the following two methods is to open space for the new minion to summon
    private void shiftGround(int index, int direction) {
        int i = index;
        if(playGround.getCurrentGround()[index]==null)
            return;
        while( i>-1 && i<7 && playGround.getCurrentGround()[i]!=null){
            i+=direction;
        }
        if(i==-1 || i==7)
            return;
        for (int j = i; j !=index ; j-=direction) { // shifting
            playGround.getCurrentGround()[j]=playGround.getCurrentGround()[j-direction];
            playGround.getCurrentGround()[j-direction]=null;
        }
    }

    // a method for applying visitors on a summoned minion
    private void applySummonVisitors(GamerMinion gamerMinion) {

    }

    // methods for hero power action
    // returns -1 for not active power ,0 for not enough mana and 1 for successful use
    public int useHeroPower(int targetIndex) {
        if (!playGround.getCurrentGamer().getGamerHeroPower().isEnabled())
            return -1;
        if (!isPowerManaEnough())
            return 0;
        // enough mana and enabled power ,
        playGround.getCurrentGamer().getGamerHero().getHero().accept(new PowerActionVisitor(), getPowerTarget(targetIndex));
        playGround.getCurrentGamer().getGamerHeroPower().setEnabled(false);
        return 1;
    }

    private boolean isPowerManaEnough() {
        int manaDifference = 0;
        if (currentPassive instanceof FreePower)
            manaDifference += 1;
        int cost = Math.max(playGround.getCurrentGamer().getGamerHeroPower().getManaCost() - manaDifference, 0);
        if (playGround.getCurrentGamer().getMana() >= cost) {
            playGround.getCurrentGamer().setMana(playGround.getCurrentGamer().getMana() - cost);
            return true;
        } else {
            return false;
        }
    }

    private GameChar getPowerTarget(int targetIndex) {
        if (targetIndex == -1)
            return playGround.getOpponentGamer().getGamerHero();
        // this value may be null
        return playGround.getOpponentGround()[targetIndex];
    }

    // methods for end turn
    public void endTurn() {
        playGround.setTurn((playGround.getTurn().equals("friendly") ? "enemy" : "friendly"));
        int tn = Math.min(10, playGround.getCurrentGamer().getTurnNumber() + 1);
        playGround.getCurrentGamer().setTurnNumber(tn);
        enableAll();
        drawCard();
        updateMana();
        if (playGround.getTurn().equals("friendly"))
            currentPassive.accept(new PassiveEndTurnVisitor());
    }

    private void enableAll() {
        // minions
        for (int i = 0; i < 7; i++) {
            if (playGround.getCurrentGround()[i] != null)
                playGround.getCurrentGround()[i].setEnabled(true);
        }
        // hero power
        playGround.getCurrentGamer().getGamerHeroPower().setEnabled(true);
        // weapon
        if (playGround.getCurrentGamer().getGamerHero().getGamerWeapon() != null)
            playGround.getCurrentGamer().getGamerHero().getGamerWeapon().setEnabled(true);
    }

    private void drawCard() {
        PlayGroundEditor.getInstance().drawOneCard();
    }

    private void updateMana() {
        playGround.getCurrentGamer().setMana(playGround.getCurrentGamer().getTurnNumber());
    }

}
