package Logic_GUIInterfaces;

// a class to hold the data related to playGround of the game

public class PlayGround {

    private Gamer friendly, enemy;
    private String turn = "friendly";
    private GamerMinion[] friendlyGround = new GamerMinion[7], enemyGround = new GamerMinion[7];

    private static PlayGround playGround;

    private PlayGround() {
        friendly = Gamer.getFriendlyInstance();
        enemy = Gamer.getEnemyInstance();

    }

    public static PlayGround getInstance() {
        if (playGround == null)
            playGround = new PlayGround();
        return playGround;
    }


    public Gamer getCurrentGamer() {
        if(turn.equals("friendly")) {

            return friendly;
        }else{
            return enemy;
        }
    }

    public Gamer getOpponentGamer() {
        if(turn.equals("friendly")) {
            return enemy;
        }else{
            return friendly;
        }
    }

    public Gamer getFriendlyGamer() {
        return friendly;
    }

    public Gamer getEnemyGamer() {
        return enemy;
    }

    public GamerMinion[] getCurrentGround(){
        if(turn.equals("friendly"))
            return friendlyGround;
        else
            return enemyGround;

    }

    public GamerMinion[] getOpponentGround(){
        if(turn.equals("enemy"))
            return friendlyGround;
        else
            return enemyGround;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public GamerMinion[] getFriendlyGround() {
            return friendlyGround;

    }

//    public void setFriendlyGround(GamerMinion[] friendlyGround) {
//        this.friendlyGround = friendlyGround;
//    }

    public GamerMinion[] getEnemyGround() {
            return enemyGround;

    }

//    public void setEnemyGround(GamerMinion[] enemyGround) {
//        this.enemyGround = enemyGround;
//    }
}
