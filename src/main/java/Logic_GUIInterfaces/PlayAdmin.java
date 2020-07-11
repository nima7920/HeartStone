package Logic_GUIInterfaces;

import Logic_game.PlayState;

import java.util.ArrayList;

// a class for connecting game GUI to game logic
public class PlayAdmin {


    private PlayState playState;
    private static PlayAdmin playAdmin;

    private PlayAdmin() {
        playState = new PlayState();
    }

    public static PlayAdmin getInstance() {
        if (playAdmin == null)
            playAdmin = new PlayAdmin();
        return playAdmin;
    }

    public void setPlayMode(int playMode) {
        playState.setPlayMode(playMode);
    }

    public String getPassiveText(int i) {
        return playState.getPassiveText(i);
    }

    public void selectPassive(int i) {
        playState.selectPassive(i);
    }

    // methods for card Selection
    public ArrayList<String> getFirstCards() {
        return playState.getFirstCards();
    }

    public void changeCards(boolean[] selectedCards) {
        playState.changeCards(selectedCards);
    }

    // methods for game actions
    // 1 for successful attack , 0 for in active condition , -1 for taunt error , -2 for rush error
    public int attack(int originIndex, int targetIndex) {
        return playState.attack(originIndex, targetIndex);
    }

    public int getCardTargetType(int cardIndex) {
        return playState.getCardTargetType(cardIndex);
    }

    // returns 1 if successful, 0 if mana is not enough , -1 if card is minion and hand is full
    public int playCard(int cardIndex, int placeIndex, int targetIndex) {
        return playState.playCard(cardIndex, placeIndex, targetIndex);
    }

    // hero power action
    // returns 1 if action is successful , 0 if mana is not enough , -1 if power is not active
    public int useHeroPower(int targetIndex) {
        return playState.useHeroPower(targetIndex);
    }

    public void endTurn() {
        playState.endTurn();
    }

}
