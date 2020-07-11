package Logic_game;

import GameCards.Minion;
import Logic_GUIInterfaces.GameChar;
import Logic_GUIInterfaces.GamerMinion;
import Logic_GUIInterfaces.PlayGround;
import Logic_cards.CardDrawVisitor;

import java.util.ArrayList;
import java.util.Collections;

// a class with methods to edit playGround
// this class is made to handle the actions that are done by visitor classes
public class PlayGroundEditor {
    private PlayGround playGround;

    private static PlayGroundEditor playGroundEditor;

    private PlayGroundEditor() {
        playGround = PlayGround.getInstance();
    }

    public static PlayGroundEditor getInstance() {
        if (playGroundEditor == null)
            playGroundEditor = new PlayGroundEditor();
        return playGroundEditor;

    }

    public void damageHero(int damage) {
        playGround.getOpponentGamer().getGamerHero().setHp(playGround.getOpponentGamer().getGamerHero().getHp() - damage);
    }

    public void damageToAllMinions(int damage) {
        for (int i = 0; i < 7; i++) {
            if (playGround.getOpponentGround()[i] != null) {
                playGround.getOpponentGround()[i].setHp(playGround.getOpponentGround()[i].getHp() - damage);
            }
        }

    }

    public void damageToAll(int damage) {
        damageToAllMinions(damage);
        damageHero(damage);

    }

    public void randomDamageToAll(int damage) {
        ArrayList<Integer> notNullIndexes = new ArrayList<>();
        // for hero
        notNullIndexes.add(-1);
// minions
        for (int i = 0; i < 7; i++) {
            if (playGround.getEnemyGround()[i] != null) {
                notNullIndexes.add(i);
            }
        }
        for (int i = 0; i < damage; i++) {
            Collections.shuffle(notNullIndexes);
            if (notNullIndexes.get(0) == -1)
                damageHero(1);
            else
                playGround.getEnemyGround()[notNullIndexes.get(i)].setHp
                        (playGround.getEnemyGround()[notNullIndexes.get(i)].getHp() - damage);
        }
    }

    public void randomDamageToMinions(int damage) {
        ArrayList<Integer> notNullIndexes = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            if (playGround.getEnemyGround()[i] != null) {
                notNullIndexes.add(i);
            }
        }
        for (int i = 0; i < damage; i++) {
            Collections.shuffle(notNullIndexes);
            playGround.getEnemyGround()[notNullIndexes.get(i)].setHp
                    (playGround.getEnemyGround()[notNullIndexes.get(i)].getHp() - damage);
        }

    }

    public void restoreHero(int hp) {
        playGround.getCurrentGamer().getGamerHero().setHp(playGround.getCurrentGamer().getGamerHero().getHp() + hp);
    }

    // this method is used by polymorh
    public void transformMinion(GameChar target, Minion minion) {
        for (int i = 0; i < 7; i++) {
            if (playGround.getOpponentGround()[i] == target) {
                playGround.getOpponentGround()[i] = new GamerMinion(minion);
                break;
            }
        }

    }

    public void summonMinion(Minion minion, int number) {
        int n = number;
        for (int i = 0; i < 7; i++) {
            if (playGround.getCurrentGround()[i] == null && n > 0) {
                playGround.getCurrentGround()[i] = new GamerMinion(minion);
                n--;
            }
        }

    }

    public void drawOneCard() {
        if (playGround.getCurrentGamer().getDeckCards().size() > 0) {
            for (int i = 0; i < 12; i++) {
                if (playGround.getCurrentGamer().getHandCards()[i] == null) {
                    playGround.getCurrentGamer().getHandCards()[i] = playGround.getCurrentGamer().getDeckCards().get(0);
                    playGround.getCurrentGamer().getDeckCards().remove(0);
                    checkCurioCollector();
                    break;
                }
            }
        }
    }

    private void checkCurioCollector() {
        for (int i = 0; i < 7; i++) {
            if (playGround.getCurrentGround()[i] != null && playGround.getCurrentGround()[i].getName().equals("Curio Collector")){
                playGround.getCurrentGround()[i].setAttack(playGround.getCurrentGround()[i].getAttack() + 1);
            playGround.getCurrentGround()[i].setHp(playGround.getCurrentGround()[i].getHp() + 1);
        }
        }
    }

    public void addMana() {
        playGround.getCurrentGamer().setMana(playGround.getCurrentGamer().getMana() + 1);
    }
}
