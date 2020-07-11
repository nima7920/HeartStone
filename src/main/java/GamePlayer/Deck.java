package GamePlayer;

import GameCards.Card;
import GameCards.CardFactory;
import GameHeros.HeroClass;
import com.google.gson.annotations.Expose;


import java.util.ArrayList;

public class Deck {
    @Expose
    private String name;
    @Expose
    private HeroClass heroClass;
    private ArrayList<Card> cards;
    @Expose
    private ArrayList<String> cardsName;

    private CardFactory cardFactory;

    public Deck(String name, HeroClass heroClass) {
        this.heroClass = heroClass;
        this.name = name;
        cards = new ArrayList<>();
        cardsName = new ArrayList<>();
        cardFactory = new CardFactory();
    }

    public int addCard(String cardName) {
        if (cardFactory == null)
            cardFactory = new CardFactory();
        Card card = cardFactory.getCard(cardName);
        if (!(card.getCardClass().toString().equals(heroClass.toString()) || card.getCardClass().toString().equals("Neutral"))) {
            // card is in illegal class
            return -1;
        } else if (cardsName.indexOf(cardName) != cardsName.lastIndexOf(cardName)) {
// there are two cards of this in deck
            return 0;
        }
        if (cardsName.size() == 30) // deck is full
            return -2;

        cardsName.add(cardName);
        syncCards();
        return 1;
    }

    public void removeCard(String cardName) {
        cardsName.remove(cardName);
        syncCards();
    }

    public void syncCards() {
        cards = new ArrayList<>();
        if (cardFactory == null)
            cardFactory = new CardFactory();
        for (int i = 0; i < cardsName.size(); i++) {
            cards.add(cardFactory.getCard(cardsName.get(i)));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<String> getCardsName() {
        return cardsName;
    }

    public void setCardsName(ArrayList<String> cardsName) {
        this.cardsName = cardsName;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
