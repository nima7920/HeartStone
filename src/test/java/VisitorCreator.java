import GameCards.Card;
import GameCards.CardFactory;

import java.util.ArrayList;


public class VisitorCreator {
    private static String getVisitorCommand(String cardName){
        String newName="",parameterName="";
        String[] words=cardName.split(" ");
        for (int i = 0; i <words.length ; i++) {
            if(i==0)
                parameterName=parameterName+words[i].toLowerCase();
            else
                parameterName=parameterName+words[i];
            newName=newName+words[i];

        }
return "void "+parameterName+"Visitor("+newName+" "+parameterName+",GameChar target);";
    }

    public static void main(String[] args) {
        CardFactory cardFactory =new CardFactory();
        ArrayList<Card> cards= (ArrayList) cardFactory.getAllCards();
        for (int i = 0; i <cards.size() ; i++) {
            System.out.println(getVisitorCommand(cards.get(i).getCardName()));
        }
    }
}
