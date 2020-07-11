package Logic_game;

import GameCards.Card;
import GameCards.CardFactory;
import GameHeros.Hero;
import Logic_GUIInterfaces.Gamer;
import Logic_GUIInterfaces.GamerHero;
import Logic_GUIInterfaces.GamerHeroPower;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DeckReader {

    private CardFactory cardFactory = new CardFactory();
    private ArrayList<Card> deckCards;
    private Hero hero;

    public void loadDefaultEnemy(Gamer gamer) {
        JSONObject enemyProperties = getProperties("properties//Logic//enemy.JSON");
        hero = Hero.getHeroFromName((String) enemyProperties.get("hero"));
        deckCards = cardFactory.getCardListFromNames((ArrayList) enemyProperties.get("deck"));
        gamer.setDeckCards(deckCards);
        gamer.setGamerHero(new GamerHero(hero));
        gamer.setGamerHeroPower(new GamerHeroPower(hero.getHeroPower()));

    }

    private JSONObject getProperties(String filePath) {

        try {
            FileReader fileReader = new FileReader(filePath);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(fileReader);
            JSONObject propertiesObject = (JSONObject) obj;
            return propertiesObject;
        } catch (IOException | ParseException e) {

        }
        return null;
    }
}
