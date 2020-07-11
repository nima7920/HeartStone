package Logic_GUIInterfaces;

import GameCards.Card;
import GameCards.Weapon;
import GameHeros.Mage;
import GamePlayer.Player;
import GamePlayer.PlayerHandler;

import java.util.ArrayList;

public class Gamer {
    // a class to hold the state of the game players; instances of this class are edited by logic and used by gui

    private int mana = 1,turnNumber=1;
    private GamerHero gamerHero = null;
    private GamerHeroPower gamerHeroPower=null;
    private ArrayList<Card> deckCards;
    private Card[] handCards = new Card[12];

// passive and first 3 cards

    private static Gamer friendly, enemy;

    private Gamer() {

    }

    public static Gamer getFriendlyInstance() {
        if (friendly == null)
            friendly = new Gamer();
        return friendly;
    }

    public static Gamer getEnemyInstance() {
        if (enemy == null)
            enemy = new Gamer();
        return enemy;
    }

    public void loadFromReader(ArrayList<String> deckCardsName) {
        this.gamerHero = new GamerHero(new Mage());

    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public GamerHero getGamerHero() {
        return gamerHero;
    }

    public void setGamerHero(GamerHero gamerHero) {
        this.gamerHero = gamerHero;

    }

    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(ArrayList<Card> deckCards) {
        this.deckCards = deckCards;
    }

    public Card[] getHandCards() {
        return handCards;
    }

    public void setHandCards(Card[] handCards) {
        this.handCards = handCards;
    }

    public GamerHeroPower getGamerHeroPower() {
        return gamerHeroPower;
    }

    public void setGamerHeroPower(GamerHeroPower gamerHeroPower) {
        this.gamerHeroPower = gamerHeroPower;
    }


}
