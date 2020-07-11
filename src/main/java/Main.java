import GUI_menus.GameScreen;
import GameCards.CardFactory;


public class Main {
    public static void main(String[] args) {
        GameScreen gameScreen=GameScreen.getInstance();
        CardFactory cardFactory =new CardFactory();
        System.out.println(cardFactory.getCardsNameOfMana(1).toString());
    }
}
