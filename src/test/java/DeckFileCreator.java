import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;

// a method for creating deck files for DeckReader class
public class DeckFileCreator {

    private String hero = "Mage";
    private ArrayList<String> deckCards = new ArrayList<>();

    public DeckFileCreator() {
        addCards();
        try {
            FileWriter fileWriter = new FileWriter("properties//Logic//enemy.JSON");
            JSONObject enemyFields = new JSONObject();
            enemyFields.put("hero", hero);
            enemyFields.put("deck", deckCards);
            fileWriter.write(enemyFields.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {

        }
    }

    private void addCards() {
        deckCards.add("Polymorph");
        deckCards.add("Arcane Servant");
        deckCards.add("Arcane Explosion");
        deckCards.add("Arcane Explosion");
        deckCards.add("Arcane Explosion");
        deckCards.add("Polymorph");
        deckCards.add("Arcane Servant");
        deckCards.add("Arcane Explosion");
        deckCards.add("Arcane Explosion");
        deckCards.add("Arcane Explosion");
        deckCards.add("Polymorph");
        deckCards.add("Arcane Servant");
        deckCards.add("Arcane Explosion");
        deckCards.add("Arcane Explosion");
        deckCards.add("Arcane Explosion");
    }

    public static void main(String[] args) {
        new DeckFileCreator();
    }
}
