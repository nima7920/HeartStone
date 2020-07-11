package Logic_menus;

import GameCards.Card;
import GameCards.CardFactory;
import GamePlayer.PlayerHandler;


import java.util.ArrayList;

public class State {
    protected PlayerHandler playerHandler;
    protected ArrayList<Card> allCards;
    protected ArrayList<String> allCardsName;
    protected CardFactory cardFactory;

    public State() {
        playerHandler =PlayerHandler.getInstance();
        cardFactory = new CardFactory();
        initAllCards();
    }

    private void initAllCards() {
        allCards = (ArrayList) cardFactory.getAllCards();
        allCardsName = (ArrayList) cardFactory.getAllCardsName();
    }
}
