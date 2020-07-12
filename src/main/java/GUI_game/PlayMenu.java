package GUI_game;

import GUI_menus.GameMenu;
import GUI_components.*;
import GUI_menus.GameScreen;
import Logic_GUIInterfaces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayMenu extends GameMenu {

    private PlayAdmin playAdmin = PlayAdmin.getInstance();
    private PlayGround playGround = PlayGround.getInstance();
    private Actions actions;
    private AnimationEngine animationEngine;
    private PlayGroundPanel playGroundPanel;
    private CardSelectionPanel cardSelectionPanel;
    private InfoPanel infoPanel;
    private DiscoverPanel discoverPanel;

    public PlayMenu() {
        guiConfigLoader = new GUIConfigLoader("play");
        actions = new Actions();
//        animationEngine = new AnimationEngine(this);
        initMenu();
    }

    private void initMenu() {
        setLayout(null);
        setBounds(guiConfigLoader.getBounds("menuBounds"));
        setSize(guiConfigLoader.getSize("menuBounds"));
        setPreferredSize(guiConfigLoader.getSize("menuBounds"));

        initPanels();
        repaint();
        openPanel("card selection");
    }

    private void initPanels() {
        playGroundPanel = new PlayGroundPanel();
        add(playGroundPanel);
//        animationEngine = new AnimationEngine(playGroundPanel);
        cardSelectionPanel = new CardSelectionPanel();
        add(cardSelectionPanel);
        infoPanel = new InfoPanel();
        add(infoPanel);
        discoverPanel = new DiscoverPanel();
        add(discoverPanel);

    }


    private void openPanel(String panelName) {
        playGroundPanel.setVisible(false);
        infoPanel.setVisible(false);
        cardSelectionPanel.setVisible(false);
        discoverPanel.setVisible(false);
        if (panelName.equals("card selection")) {
            cardSelectionPanel.setVisible(true);

        } else if (panelName.equals("discover")) {

        } else if (panelName.equals("playground")) {
            cardSelectionPanel.setVisible(false);
            discoverPanel.setVisible(false);
            playGroundPanel.setVisible(true);
            infoPanel.setVisible(true);
            playGroundPanel.repaint();
        }

    }

    // a void that refreshes the playGround
    private void refresh() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2d = addRenderingHint(g);
//        try {
//            BufferedImage backGroundImage = ImageIO.read(new File("Game Data\\GUI\\background images\\Playground.jpg"));
//            g2d.drawImage(backGroundImage, 0, 0, 1000, 800, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public class PlayGroundPanel extends JPanel {

        public void redraw(){
            revalidate();
            repaint();
            this.paintComponent(this.getGraphics());
        }
        private Point[] friendlyGroundLocations, enemyGroundLocations;
        private Point[] friendlyHandLocations, enemyHandLocations;
        private Point friendlyHeroLocation, enemyHeroLocation;
        private Point friendlyWeaponLocation, enemyWeaponLocation;
        private Point friendlyHeroPowerLocation, enemyHeroPowerLocation;

        private Dimension handCardDimension, groundMinionDimension, groundWeaponDimension;
        private Dimension heroDimension, heroPowerDimension;

        private Rectangle friendlyField, enemyField;
        // graphical objects
        private GCard[] friendlyHand, enemyHand;
        private GMinion[] friendlyGround, enemyGround;
        private GHero friendlyHero, enemyHero;
        private GHeroPower friendlyPower, enemyPower;
        private GButton endTurnButton;

        // other fields used in game play
        private int friendlyRemainingCards, enemyRemainingCards;
        private int friendlyMana = 1, enemyMana = 1;

        private String turn = "friendly";
        private String originType = ""; // a field which takes the values "","character","card","hero power" depending on the selected object
        // 0 for no target, 1 for friendly minion , 2 for enemy minion , 3 for enemy character
        private int targetType = 0;
        private boolean originSelected = false; // indicates whether an origin is selected or not
        private int originIndex, targetIndex;// two fields for indicating origin and target
        private int destinationIndex; // shows the place of a card which is going to be played

        public PlayGroundPanel() {
            setLayout(null);
            setBounds(guiConfigLoader.getBounds("playGroundPanel_bounds"));
            addMouseListener(actions);
            addMouseMotionListener(actions);
            initComponents();
            initFields();
            loadScales();
            initGraphicalObjects();
            animationEngine=new AnimationEngine(this);
            PlayMenu.this.repaint();
        }

        private void initComponents() {
// end turn button
            endTurnButton = new GButton("end turn");
            endTurnButton.setBounds(guiConfigLoader.getBounds("playGroundPanel_endTurn_bounds"));
            endTurnButton.addActionListener(actions);
            add(endTurnButton);

        }

        private void initFields() {
            // initializing locations
            friendlyGroundLocations = new Point[7];
            enemyGroundLocations = new Point[7];
            friendlyHandLocations = new Point[12];
            enemyHandLocations = new Point[12];

            // hand,deck.
            friendlyHand = new GCard[12];
            friendlyGround = new GMinion[7];
            enemyHand = new GCard[12];
            enemyGround = new GMinion[7];
        }

        private void loadScales() {
            // player fields
            friendlyField = guiConfigLoader.getBounds("playGroundPanel_friendlyField");
            enemyField = guiConfigLoader.getBounds("playGroundPanel_enemyField");
// locations
            Point fPoint = guiConfigLoader.getPoint("playGroundPanel_friendlyGround"),
                    ePoint = guiConfigLoader.getPoint("playGroundPanel_enemyGround");
            int align = guiConfigLoader.getInt("playGroundPanel_groundMinion_align");

            for (int i = 0; i < 7; i++) {
                friendlyGroundLocations[i] = new Point(fPoint.x + i * align, fPoint.y);
                enemyGroundLocations[i] = new Point(ePoint.x + i * align, ePoint.y);
            }

            fPoint = guiConfigLoader.getPoint("playGroundPanel_friendlyHand");
            ePoint = guiConfigLoader.getPoint("playGroundPanel_enemyHand");
            align = guiConfigLoader.getInt("playGroundPanel_handCards_align");

            for (int i = 0; i < 12; i++) {
                friendlyHandLocations[i] = new Point(fPoint.x + i * align, fPoint.y);
                enemyHandLocations[i] = new Point(ePoint.x + i * align, ePoint.y);
            }
            friendlyHeroLocation = guiConfigLoader.getPoint("playGroundPanel_friendlyHero");
            enemyHeroLocation = guiConfigLoader.getPoint("playGroundPanel_enemyHero");

            friendlyHeroPowerLocation = guiConfigLoader.getPoint("playGroundPanel_friendlyHeroPower");
            enemyHeroPowerLocation = guiConfigLoader.getPoint("playGroundPanel_enemyHeroPower");

            friendlyWeaponLocation = guiConfigLoader.getPoint("playGroundPanel_friendlyWeapon");
            enemyWeaponLocation = guiConfigLoader.getPoint("playGroundPanel_enemyWeapon");
            //dimensions
            handCardDimension = guiConfigLoader.getSize("playGroundPanel_handCardSize");
            groundMinionDimension = guiConfigLoader.getSize("playGroundPanel_GMinionSize");
            heroDimension = guiConfigLoader.getSize("playGroundPanel_heroSize");
            heroPowerDimension = guiConfigLoader.getSize("playGroundPanel_heroPowerSize");
            groundWeaponDimension = guiConfigLoader.getSize("playGroundPanel_weaponSize");

        }

        private void initGraphicalObjects() {
            // initializing heroes
            friendlyHero = new GHero(heroDimension, playGround.getFriendlyGamer().getGamerHero(), friendlyHeroLocation, friendlyWeaponLocation);
            enemyHero = new GHero(heroDimension, playGround.getEnemyGamer().getGamerHero(), enemyHeroLocation, enemyWeaponLocation);
            // initializing hero powers
            friendlyPower = new GHeroPower(heroPowerDimension, playGround.getFriendlyGamer().getGamerHeroPower(), friendlyHeroPowerLocation);
            enemyPower = new GHeroPower(heroPowerDimension, playGround.getEnemyGamer().getGamerHeroPower(), enemyHeroPowerLocation);
            //     initializing hand cards
            for (int i = 0; i < 12; i++) {
                if (playGround.getFriendlyGamer().getHandCards()[i] != null)
                    friendlyHand[i] = new GCard(handCardDimension, playGround.getFriendlyGamer().getHandCards()[i].getCardName(), friendlyHandLocations[i]);
                if (playGround.getEnemyGamer().getHandCards()[i] != null)
                    enemyHand[i] = new GCard(handCardDimension, playGround.getEnemyGamer().getHandCards()[i].getCardName(), enemyHandLocations[i]);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = addRenderingHint(g);
            drawBackground(g2d);
            System.out.println("painting");
            if (turn.equals("friendly")) {
                drawFriendlyObjects(g2d);
                drawEnemyObjects(g2d);
            } else {
                drawEnemyObjects(g2d);
                drawFriendlyObjects(g2d);
            }
            drawManaAndRemainingCards(g2d);
        }

        private void drawBackground(Graphics2D g2d) {
            g2d.drawImage(imageLoader.getBackgroundImage("Playground"), 0, 0, 1100, 800, null);
//            try {
//                BufferedImage backGroundImage = ImageIO.read(new File(guiConfigLoader.getString("playGroundPanel_backgroundImage_path")));
//                g2d.drawImage(backGroundImage, 0, 0, 1100, 800, null);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


        private void drawFriendlyObjects(Graphics2D g2d) {
            // drawing ground minions
            for (int i = 0; i < 7; i++) {
                if (friendlyGround[i] != null)
                    friendlyGround[i].render(g2d);
            }
            // hero power must be drawn
            friendlyPower.render(g2d);
            // drawing hero and weapon
            friendlyHero.render(g2d);
            // drawing hand cards
            for (int i = 0; i < 12; i++) {
                if (friendlyHand[i] != null) {
                    friendlyHand[i].render(g2d);
                }
            }
        }

        // draws hand cards of players
        private void drawEnemyObjects(Graphics2D g2d) {
            // drawing ground minions
            for (int i = 0; i < 7; i++) {
                if (enemyGround[i] != null)
                    enemyGround[i].render(g2d);
            }
            // hero power must be drawn
            enemyPower.render(g2d);
            // drawing her and weapon
            enemyHero.render(g2d);
            // drawing hand cards
            for (int i = 0; i < 12; i++) {
                if (enemyHand[i] != null)
                    enemyHand[i].render(g2d);
            }
        }

        private void drawManaAndRemainingCards(Graphics2D g2d) {
            g2d.setPaint(guiConfigLoader.getColor("playGroundPanel_friendlyMana_color"));
            g2d.setFont(guiConfigLoader.getFont("playGroundPanel_friendlyMana_font"));
            g2d.drawString("Mana: " + friendlyMana + "/" + playGround.getFriendlyGamer().getTurnNumber(),
                    guiConfigLoader.getInt("playGroundPanel_friendlyMana_x"),
                    guiConfigLoader.getInt("playGroundPanel_friendlyMana_y"));

            g2d.setPaint(guiConfigLoader.getColor("playGroundPanel_enemyMana_color"));
            g2d.setFont(guiConfigLoader.getFont("playGroundPanel_enemyMana_font"));
            g2d.drawString("Mana: " + enemyMana + "/" + playGround.getEnemyGamer().getTurnNumber(),
                    guiConfigLoader.getInt("playGroundPanel_enemyMana_x"),
                    guiConfigLoader.getInt("playGroundPanel_enemyMana_y"));

            g2d.setPaint(guiConfigLoader.getColor("playGroundPanel_friendlyRemaining_color"));
            g2d.setFont(guiConfigLoader.getFont("playGroundPanel_friendlyRemaining_font"));
            g2d.drawString("Remaining cards: " + friendlyRemainingCards,
                    guiConfigLoader.getInt("playGroundPanel_friendlyRemaining_x"),
                    guiConfigLoader.getInt("playGroundPanel_friendlyRemaining_y"));

            g2d.setPaint(guiConfigLoader.getColor("playGroundPanel_enemyRemaining_color"));
            g2d.setFont(guiConfigLoader.getFont("playGroundPanel_enemyRemaining_font"));
            g2d.drawString("Remaining cards: " + enemyRemainingCards,
                    guiConfigLoader.getInt("playGroundPanel_enemyRemaining_x"),
                    guiConfigLoader.getInt("playGroundPanel_enemyRemaining_y"));
        }

        // syncing graphical objects with gamer objects
        private void sync() {
            clearObjects();
            friendlyHero.sync();
            friendlyPower.sync();
            enemyHero.sync();
            enemyPower.sync();
            for (int i = 0; i < 7; i++) {
                if (friendlyGround[i] != null)
                    friendlyGround[i].sync();
                if (enemyGround[i] != null)
                    enemyGround[i].sync();
            }
// hero powers must be added
            friendlyPower.sync();
            enemyPower.sync();
            syncFields();
        }

        // a method for syncing mana and remaining cards
        private void syncFields() {
            turn = playGround.getTurn();
            friendlyMana = playGround.getFriendlyGamer().getMana();
            enemyMana = playGround.getEnemyGamer().getMana();
            friendlyRemainingCards = playGround.getFriendlyGamer().getDeckCards().size();
            enemyRemainingCards = playGround.getEnemyGamer().getDeckCards().size();
            originSelected = false;
        }

        // this method adds new objects and removes dead ones ( used before syncing )
        private void clearObjects() {
            // hand cards
            for (int i = 0; i < 12; i++) {
                if (playGround.getFriendlyGamer().getHandCards()[i] != null)
                    friendlyHand[i] = new GCard(handCardDimension,
                            playGround.getFriendlyGamer().getHandCards()[i].getCardName(), friendlyHandLocations[i]);
                else
                    friendlyHand[i] = null;

                if (playGround.getEnemyGamer().getHandCards()[i] != null)
                    enemyHand[i] = new GCard(handCardDimension,
                            playGround.getEnemyGamer().getHandCards()[i].getCardName(), enemyHandLocations[i]);
                else
                    enemyHand[i] = null;
            }
            // ground minions
            for (int i = 0; i < 7; i++) {
                if (playGround.getFriendlyGround()[i] != null)
                    friendlyGround[i] = new GMinion(groundMinionDimension,
                            playGround.getFriendlyGround()[i], friendlyGroundLocations[i]);
                else
                    friendlyGround[i] = null;

                if (playGround.getEnemyGround()[i] != null)
                    enemyGround[i] = new GMinion(groundMinionDimension,
                            playGround.getEnemyGround()[i], enemyGroundLocations[i]);
                else
                    enemyGround[i] = null;
            }

        }


    }

    // a panel for the first 3 cards in hand
    private class CardSelectionPanel extends JPanel {

        private GCard[] cards = new GCard[3];
        private GButton doneButton;

        private boolean changedCards[] = new boolean[3];

        public CardSelectionPanel() {
            setLayout(null);
            setBounds(guiConfigLoader.getBounds("cardSelectionPanel_bounds"));
            setOpaque(false);
            addMouseListener(actions);
            initCards();
            initDoneButton();
            repaint();
        }

        private void initCards() {
            ArrayList<String> firstCards = playAdmin.getFirstCards();
            for (int i = 0; i < 3; i++) {
                cards[i] = new GCard(guiConfigLoader.getSize("cardSelection_card" + (i + 1) + "_bounds"),
                        firstCards.get(i),
                        guiConfigLoader.getPoint("cardSelection_card" + (i + 1) + "_bounds"));
            }
//            card1 = new GCard(guiConfigLoader.getSize("cardSelection_card1_bounds"),
//                    firstCards.get(0),
//                    guiConfigLoader.getPoint("cardSelection_card1_bounds"));
//
//            card2 = new GCard(guiConfigLoader.getSize("cardSelection_card2_bounds"),
//                    firstCards.get(1),
//                    guiConfigLoader.getPoint("cardSelection_card2_bounds"));
//
//            card3 = new GCard(guiConfigLoader.getSize("cardSelection_card3_bounds"),
//                    firstCards.get(2),
//                    guiConfigLoader.getPoint("cardSelection_card3_bounds"));
        }

        private void initDoneButton() {
            doneButton = new GButton("Done");
            doneButton.setBounds(guiConfigLoader.getBounds("cardSelection_doneButton_bounds"));
            doneButton.addActionListener(actions);
            add(doneButton);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = addRenderingHint(g);
            paintBackground(g2d);
            paintCards(g2d);
        }

        private void paintBackground(Graphics2D g2d) {
            g2d.setPaint(new Color(100, 100, 100, 200));
            g2d.fillRect(guiConfigLoader.getBounds("cardSelectionPanel_bounds").x,
                    guiConfigLoader.getBounds("cardSelectionPanel_bounds").y,
                    guiConfigLoader.getBounds("cardSelectionPanel_bounds").width,
                    guiConfigLoader.getBounds("cardSelectionPanel_bounds").height);
        }

        private void paintCards(Graphics2D g2d) {
            for (int i = 0; i < 3; i++) {
                if (cards[i] != null)
                    cards[i].render(g2d);
            }
//            if (card1 != null)
//                card1.render(g2d);
//            if (card2 != null)
//                card2.render(g2d);
//            if (card3 != null)
//                card3.render(g2d);

        }
    }


    private class InfoPanel extends JPanel {

        // components
        private JLabel messageLabel, questLabel, timerLabel;
        private GCard infoCard;
        private GButton backButton, exitButton;
        private GTimer gTimer;
        // fields
        private String cardName = "";
        private Dimension cardDimension;
        private Point cardLocation;

        public InfoPanel() {
            setLayout(null);
            setBounds(guiConfigLoader.getBounds("infoPanel_bounds"));
            initComponents();
            initFields();
        }

        private void initComponents() {
            initButtons();
            initLabels();

            gTimer = new GTimer(timerLabel);
        }

        private void initButtons() {
            backButton = new GButton("back");
            backButton.setBounds(guiConfigLoader.getBounds("infoPanel_backButton"));
            backButton.addActionListener(actions);
            add(backButton);

            exitButton = new GButton("exit");
            exitButton.setBounds(guiConfigLoader.getBounds("infoPanel_exitButton"));
            exitButton.addActionListener(actions);
            add(exitButton);
        }

        private void initLabels() {
            messageLabel = new JLabel();
            messageLabel.setBounds(guiConfigLoader.getBounds("infoPanel_messageLabel_bounds"));
            messageLabel.setFont(guiConfigLoader.getFont("infoPanel_messageLabel_font"));
            messageLabel.setForeground(guiConfigLoader.getColor("infoPanel_messageLabel_foreColor"));
            add(messageLabel);

            timerLabel = new JLabel();
            timerLabel.setBounds(guiConfigLoader.getBounds("infoPanel_timerLabel_bounds"));
            timerLabel.setFont(guiConfigLoader.getFont("infoPanel_timerLabel_font"));
            timerLabel.setForeground(guiConfigLoader.getColor("infoPanel_timerLabel_foreColor"));
            add(timerLabel);

        }

        private void initFields() {
            cardDimension = guiConfigLoader.getSize("infoPanel_infoCard");
            cardLocation = guiConfigLoader.getPoint("infoPanel_infoCard");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = addRenderingHint(g);
            if (infoCard != null) {
                infoCard.render(g2d);
            }
        }

        private void setInfoCardName(String name) {
            if (name.equals(""))
                infoCard = null;
            else
                infoCard = new GCard(cardDimension, name, cardLocation);
            repaint();
        }

        private void setMessageText(String text) {
            messageLabel.setText(text);
        }
    }

    private class DiscoverPanel extends JPanel {


    }


    private class Actions implements ActionListener, MouseListener, MouseMotionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // card selection done button
            if (e.getSource() == cardSelectionPanel.doneButton) {
//                boolean[] selectedCards = new boolean[]{cardSelectionPanel.cardChange1,
//                        cardSelectionPanel.cardChange2, cardSelectionPanel.cardChange3};
                System.out.println(cardSelectionPanel.changedCards[0] + "," +
                        cardSelectionPanel.changedCards[1] + "," + cardSelectionPanel.changedCards[2]);
                playAdmin.changeCards(cardSelectionPanel.changedCards);
                playGroundPanel.sync();
                openPanel("playground");
                infoPanel.gTimer.start();
            }

            // playground end turn button
            if (e.getSource() == playGroundPanel.endTurnButton) {
                playGroundPanel.originSelected = false;
                endTurn();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
// handling card selection panel clicks
            if (e.getSource() == cardSelectionPanel) {
                for (int i = 0; i < 3; i++) {
                    if (cardSelectionPanel.cards[i].isMouseInside(e)) {
                        cardSelectionPanel.changedCards[i] = !cardSelectionPanel.changedCards[i];
                        cardSelectionPanel.cards[i].setLocked(cardSelectionPanel.changedCards[i]);
                        cardSelectionPanel.repaint();
                    }
                }
//                if (cardSelectionPanel.card1.isMouseInside(e)) {
//                    cardSelectionPanel.cardChange1 = !cardSelectionPanel.cardChange1;
//                    cardSelectionPanel.card1.setLocked(cardSelectionPanel.cardChange1);
//                    cardSelectionPanel.repaint();
//                }
//                if (cardSelectionPanel.card2.isMouseInside(e)) {
//                    cardSelectionPanel.cardChange2 = !cardSelectionPanel.cardChange2;
//                    cardSelectionPanel.card2.setLocked(cardSelectionPanel.cardChange2);
//                    cardSelectionPanel.repaint();
//                }
//                if (cardSelectionPanel.card3.isMouseInside(e)) {
//                    cardSelectionPanel.cardChange3 = !cardSelectionPanel.cardChange3;
//                    cardSelectionPanel.card3.setLocked(cardSelectionPanel.cardChange3);
//                    cardSelectionPanel.repaint();
//                }

            }

            // playGround panel mouse pressed
            if (e.getSource() == playGroundPanel) {
//                if (playGroundPanel.turn.equals("friendly"))
                handlePlayGroundClick(e);
//                else
//                    enemyClicked(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getSource() == playGroundPanel)
                checkCardHover(e);

        }

        private void actionCompleted() {
            playGroundPanel.originSelected = false;
            playGroundPanel.sync();
            playGroundPanel.repaint();
        }

        // setting infoPanel card
        private void checkCardHover(MouseEvent e) {
            // ground
            for (int i = 11; i >= 0; i--) {
                if (playGroundPanel.friendlyHand[i] != null && playGroundPanel.friendlyHand[i].isMouseInside(e)) {
                    infoPanel.setInfoCardName(playGroundPanel.friendlyHand[i].getName());
                    return;
                }
                if (playGroundPanel.enemyHand[i] != null && playGroundPanel.enemyHand[i].isMouseInside(e)) {
                    infoPanel.setInfoCardName(playGroundPanel.enemyHand[i].getName());
                    return;
                }
            }
            // hand
            for (int i = 0; i < 7; i++) {
                if (playGroundPanel.friendlyGround[i] != null && playGroundPanel.friendlyGround[i].isMouseInside(e)) {
                    infoPanel.setInfoCardName(playGroundPanel.friendlyGround[i].getName());
                    return;
                }
                if (playGroundPanel.enemyGround[i] != null && playGroundPanel.enemyGround[i].isMouseInside(e)) {
                    infoPanel.setInfoCardName(playGroundPanel.enemyGround[i].getName());
                    return;
                }
            }
            infoPanel.setInfoCardName("");
        }

        // voids for handling playGround mouse clicks
        private void handlePlayGroundClick(MouseEvent e) {
            String turn = playGroundPanel.turn;
            String nonTurn = (turn.equals("friendly") ? "enemy" : "friendly");
            if (playGroundPanel.originSelected) { // an origin is already selected
                switch (playGroundPanel.originType) {
                    case "character": {
                        for (int i = 0; i < 7; i++) {
                            // checking whether target is a minion
                            if (getGround(nonTurn)[i] != null && getGround(nonTurn)[i].isMouseInside(e)) {
                                playGroundPanel.targetIndex = i;
                                performAttack();
                                return;
                            }
                        }
                        if (getHero(nonTurn).isMouseInside(e)) {
                            playGroundPanel.targetIndex = -1;
                            performAttack();
                            return;
                        }
                        infoPanel.setMessageText("No target selected");
                        playGroundPanel.originSelected = false;
                        break;
                    }

                    case "card": {
                        if (getField(turn).contains(e.getPoint())) {
                            playGroundPanel.targetType = playAdmin.getCardTargetType(playGroundPanel.originIndex);
                            playGroundPanel.destinationIndex = getDestinationIndex(e);
                            if (playGroundPanel.targetType == 0) { // card must be played with no target
                                playCard();
                                playGroundPanel.originSelected = false;
                            } else { // card takes a target before playing

                                playGroundPanel.originType = "card play";
                                infoPanel.setMessageText("Choose a target");

                            }
                        } else { // selected point is not in field
                            playGroundPanel.originSelected = false;
                        }
                        return;

                    }
                    case "card play": { // for spells with target and minions with battlecry

                        if (verifyCardTarget(e)) { // checking whether selected target is legal
                            playGroundPanel.targetIndex = getTargetIndex(e);
                            playCard();
                            playGroundPanel.originSelected = false;
                        } else {
                            playGroundPanel.originSelected = false;
                            infoPanel.setMessageText("Illegal target");
                        }
                        return;

                    }
                    case "hero power": { // target of hero power is selected
                        if (verifyPowerTarget(e)) {
                            useHeroPower();
                            playGroundPanel.originSelected = false;
                        } else {
                            playGroundPanel.originSelected = false;
                            infoPanel.setMessageText("Illegal target");
                        }
                        break;
                    }
                }
            } else { // nothing is selected yet
                for (int i = 11; i >= 0; i--) { // checking hand cards
                    if (getHand(turn)[i] != null && getHand(turn)[i].isMouseInside(e)) {
                        playGroundPanel.originType = "card";
                        playGroundPanel.originSelected = true;
                        playGroundPanel.originIndex = i;
                        break;
                    }
                }
                for (int i = 0; i < 7; i++) { // checking ground minions
                    if (getGround(turn)[i] != null && getGround(turn)[i].isMouseInside(e)) {
                        playGroundPanel.originType = "character";
                        playGroundPanel.originSelected = true;
                        playGroundPanel.originIndex = i;
                        break;
                    }
                }
                if (getHero(turn).isMouseInside(e)) {
                    playGroundPanel.originType = "character";
                    playGroundPanel.originSelected = true;
                    playGroundPanel.originIndex = -1;
                    playGroundPanel.targetType = 3;
                }
                if (getHeroPower(turn).isMouseInside(e)) {
                    playGroundPanel.originType = "hero power";
                    playGroundPanel.originSelected = true;
                    playGroundPanel.originIndex = -1;
                    // 0 for passive (does nothing ), 1 for no target , 2 for enemy target
                    playGroundPanel.targetType = playGround.getCurrentGamer().getGamerHeroPower().getTargetType();
                }
            }

        }

        private boolean verifyCardTarget(MouseEvent e) {
            // checking ground minions
            for (int i = 0; i < 7; i++) {
                if (playGroundPanel.friendlyGround[i] != null && playGroundPanel.friendlyGround[i].isMouseInside(e)) {
                    if (playGroundPanel.turn.equals("friendly") && playGroundPanel.targetType == 1)
                        return true;
                    else if (playGroundPanel.turn.equals("enemy") && playGroundPanel.targetType > 1)
                        return true;
                    else
                        return false;
                }
                if (playGroundPanel.enemyGround[i] != null && playGroundPanel.enemyGround[i].isMouseInside(e)) {
                    if (playGroundPanel.turn.equals("enemy") && playGroundPanel.targetType == 1)
                        return true;
                    else if (playGroundPanel.turn.equals("friendly") && playGroundPanel.targetType > 1)
                        return true;
                    else
                        return false;
                }
            }

            // checking heroes
            if (playGroundPanel.friendlyHero.isMouseInside(e)) {
                if (playGroundPanel.turn.equals("enemy") && playGroundPanel.targetType == 3)
                    return true;
                else
                    return false;
            }
            if (playGroundPanel.enemyHero.isMouseInside(e)) {
                if (playGroundPanel.turn.equals("friendly") && playGroundPanel.targetType == 3)
                    return true;
                else
                    return false;
            }
            return false;
        }

        private int getTargetIndex(MouseEvent e) {
            for (int i = 0; i < 7; i++) {
                if ((playGroundPanel.friendlyGround[i] != null && playGroundPanel.friendlyGround[i].isMouseInside(e))
                        || (playGroundPanel.enemyGround[i] != null && playGroundPanel.enemyGround[i].isMouseInside(e)))
                    return i;
            }
            return -1;
        }

        private int getDestinationIndex(MouseEvent e) {
            int minionWidth = guiConfigLoader.getInt("playGroundPanel_GMinionSize_width");
            int firstMinionX = guiConfigLoader.getInt("playGroundPanel_friendlyGround_x");
            int align = guiConfigLoader.getInt("playGroundPanel_groundMinion_align");
            if (e.getPoint().x <= firstMinionX + minionWidth)
                return 0;
            return ((e.getPoint().x - minionWidth - firstMinionX) / align + 1);
        }

        // methods for playing cards
        // method uses fields declared in playGroundPanel to communicate with admin
        // it is assumed that the fields already have valid values
        private void playCard() {
//            System.out.println(playGroundPanel.originIndex + "," + playGroundPanel.destinationIndex + "," + playGroundPanel.targetIndex);
            switch (playAdmin.playCard(playGroundPanel.originIndex, playGroundPanel.destinationIndex, playGroundPanel.targetIndex)) {
                case -1: { // ground is full
                    infoPanel.setMessageText("Not enough Place");
                    break;
                }
                case 0: { // not enough mana
                    infoPanel.setMessageText("Not enough mana");
                    break;
                }
                case 1: { // play card successfully
                    moveCard();
                    playGroundPanel.sync();
                    playGroundPanel.repaint();
                    break;
                }
            }

        }

        private void performAttack() {
            int result = playAdmin.attack(playGroundPanel.originIndex, playGroundPanel.targetIndex);
            switch (result) {
                case -2: { // rush error
                    infoPanel.setMessageText("Rush Minion can't attack Hero");
                    break;
                }
                case -1: { // taunt error
                    infoPanel.setMessageText("You should attack taunt");
                    break;
                }
                case 0: { // active error
                    infoPanel.setMessageText("minion is not active");
                    break;
                }
                case 1: { // successful attack
// animation part must be added
                    break;
                }
            }
            actionCompleted();
        }

        // method uses originIndex and destinationIndex in playGround panel combined with animationEngine
        // to move a card from hand to field
        private void moveCard() {
            animationEngine.setDelay(guiConfigLoader.getInt("animationEngine_move_delay"));
            animationEngine.setDuration(guiConfigLoader.getInt("animationEngine_move_duration"));
            if (playGroundPanel.turn.equals("friendly")) {
                animationEngine.move(playGroundPanel.friendlyHand[playGroundPanel.originIndex],
                        playGroundPanel.friendlyGroundLocations[playGroundPanel.destinationIndex]);

            } else {
                animationEngine.move(playGroundPanel.enemyHand[playGroundPanel.originIndex],
                        playGroundPanel.enemyGroundLocations[playGroundPanel.destinationIndex]);
            }
        }

        // verifies hero powers selected target
        // sets playGroundPanel.targetIndex as well
        private boolean verifyPowerTarget(MouseEvent e) {
            String turn = playGroundPanel.turn;
            String nonTurn = (turn.equals("friendly") ? "enemy" : "friendly");
            if (playGroundPanel.targetType == 0)
                return true;
            if (playGroundPanel.targetType == 1) {
                if (getField(turn).contains(e.getPoint()))
                    return true;
                else
                    return false;
            }
            // target type is 2
            for (int i = 0; i < 7; i++) {
                if (getGround(nonTurn)[i] != null && getGround(nonTurn)[i].isMouseInside(e)) {
                    playGroundPanel.targetIndex = i;
                    return true;
                }
            }
            if (getHero(nonTurn).isMouseInside(e)) {
                playGroundPanel.targetIndex = -1;
                return true;
            }
            return false;
        }

        // uses selected hero power
        private void useHeroPower() {
            switch (playAdmin.useHeroPower(playGroundPanel.targetIndex)) {
                case 1: { // action done successfully
                    playGroundPanel.sync();
                    playGroundPanel.repaint();
                    break;
                }
                case 0: { // not enough mana
                    infoPanel.setMessageText("Not enough mana");
                    break;
                }
                case -1: { // power not active
                    infoPanel.setMessageText("Power not active");
                    break;
                }
            }
        }

        private void endTurn() {
            infoPanel.gTimer.resetTurn();
            playAdmin.endTurn();
            playGroundPanel.sync();
            playGroundPanel.repaint();
        }

        // some methods for handling clicks easier
        private Rectangle getField(String turn) {
            if (turn.equals("friendly"))
                return playGroundPanel.friendlyField;
            else
                return playGroundPanel.enemyField;
        }

        private GHero getHero(String turn) {
            if (turn.equals("friendly"))
                return playGroundPanel.friendlyHero;
            else
                return playGroundPanel.enemyHero;
        }

        private GHeroPower getHeroPower(String turn) {
            if (turn.equals("friendly"))
                return playGroundPanel.friendlyPower;
            else
                return playGroundPanel.enemyPower;
        }

        private GCard[] getHand(String turn) {
            if (turn.equals("friendly"))
                return playGroundPanel.friendlyHand;
            else
                return playGroundPanel.enemyHand;
        }

        private GMinion[] getGround(String turn) {
            if (turn.equals("friendly"))
                return playGroundPanel.friendlyGround;
            else
                return playGroundPanel.enemyGround;
        }

    }

    private class GTimer extends Thread {

        private int currentTime = 60;
        private JLabel timerLabel;
        private Color defaultColor;

        public GTimer(JLabel timerLabel) {
            this.timerLabel = timerLabel;
            defaultColor = timerLabel.getForeground();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime--;
                if (currentTime <= 20)
                    timerLabel.setForeground(Color.RED);
                timerLabel.setText(currentTime + "");
                if (currentTime == 0) {
                    actions.endTurn();
                }
            }
        }

        private void resetTurn() {
            this.currentTime = 60;
            timerLabel.setForeground(defaultColor);
        }
    }
//    private class Actions implements ActionListener, MouseListener,
//    private Actions action;
//    private Playground playground;
//    private PassivePanel passivePanel;
//
//    public PlayMenu() {
//        action = new Actions();
//        setLayout(null);
//        setBounds(GUIConfigLoader.playMenu.menuBounds);
//        setSize(GUIConfigLoader.playMenu.menuSize);
//        setPreferredSize(GUIConfigLoader.playMenu.menuSize);
//        initComponents();
//    }
//
//    private void initComponents() {
//        playground = new Playground();
//        passivePanel = new PassivePanel();
//        add(playground);
//        add(passivePanel);
//        playground.setVisible(false);
//    }
//
//    public void refresh() {
//        playground.setVisible(false);
//        passivePanel.setVisible(true);
//        passivePanel.refresh();
//        playground.refresh();
//    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d=addRenderingHint(g);
//        paintGraphics(g2d);
//    }
//
//    private void paintGraphics(Graphics2D g2d) {
//        try {
//            BufferedImage backgroundImage = ImageIO.read(GUIConfigLoader.playMenu.menuBackground_image);
//            g2d.drawImage(backgroundImage, 0, 0, GUIConfigLoader.playMenu.menuBounds.width, GUIConfigLoader.playMenu.menuBounds.height, null);
//
//        } catch (IOException e) {
//
//
//        }
//    }
//    private void gotoPlayground() {
//        playground.setVisible(true);
//        passivePanel.setVisible(false);
//        playground.updateInfo();
//    }
//
//    // a panel for playground
//    private class Playground extends JPanel {
//        // panel components
//        private GCard[] handCards = new GCard[12], groundCards = new GCard[7];
//        private GCard weaponCard, cardOverview;
//        private GHero playerHero;
//        private GHeroPower heroPower;
//        private JButton endTurnButton, backButton;
//        private JList<String> gameEvents;
//        private JScrollPane eventsPane;
//        private JLabel manaLabel, heroHpLabel, remainingCardsLabel;
//
//        // panel fields
//        private ArrayList<String> gameEventsName;
//        private ArrayList<String> handCardsName, groundCardsName;
//        private String playerHeroName;
//
//        public Playground() {
//            setLayout(null);
//            setBounds(GUIConfigLoader.playMenu.playground_bounds);
//            gameEventsName = new ArrayList<>();
//            addMouseMotionListener(action);
//            addMouseListener(action);
//            initComponents();
//            repaint();
//        }
//
//        private void initComponents() {
//            // end turn button,back button
//            endTurnButton = new JButton("end turn");
//            endTurnButton.setBounds(GUIConfigLoader.playMenu.endTurnButton_bounds);
//            endTurnButton.addActionListener(action);
//            add(endTurnButton);
//
//            backButton = new JButton("Back");
//            backButton.setBounds(GUIConfigLoader.playMenu.backButton_bounds);
//            backButton.addActionListener(action);
//            add(backButton);
//
//            // mana,hero hp,remaining cards labels
//            heroHpLabel = new JLabel("30");
//            heroHpLabel.setBounds(GUIConfigLoader.playMenu.heroHpLabel_bounds);
////            heroHpLabel.setVisible(true);
//            heroHpLabel.setFont(GUIConfigLoader.playMenu.heroHpLabel_font);
//            heroHpLabel.setForeground(GUIConfigLoader.playMenu.heroHpLabel_foreColor);
//            add(heroHpLabel);
//
//            manaLabel = new JLabel("1");
//            manaLabel.setBounds(GUIConfigLoader.playMenu.manaLabel_bounds);
//            manaLabel.setFont(GUIConfigLoader.playMenu.manaLabel_font);
//            manaLabel.setForeground(GUIConfigLoader.playMenu.manaLabel_foreColor);
//            add(manaLabel);
//
//            remainingCardsLabel = new JLabel("Cards in Deck:");
//            remainingCardsLabel.setBounds(GUIConfigLoader.playMenu.remainingCard_bounds);
//            remainingCardsLabel.setFont(GUIConfigLoader.playMenu.remainingCardsLabel_font);
//            remainingCardsLabel.setForeground(GUIConfigLoader.playMenu.remainingCardsLabel_foreColor);
//            add(remainingCardsLabel);
//
//            // event list
//            eventsPane = new JScrollPane();
//            gameEvents = new JList<>();
//            eventsPane.setBounds(GUIConfigLoader.playMenu.gameEventList_bounds);
//            eventsPane.setViewportView(gameEvents);
//            gameEvents.setLayoutOrientation(JList.VERTICAL);
//            add(eventsPane);
//        }
//
//
//        private void setCardOverview(int i) {
//            cardOverview = new GCard(GUIConfigLoader.playMenu.cardOverview_size, handCards[i].getCardName(), GUIConfigLoader.playMenu.cardOverview_location);
//            repaint();
//        }
//
//        // a void for updating all labels and cards
//        private void updateInfo() {
//            manaLabel.setText(admin.getMana() + "/" + Math.min(admin.getTurn(), 10));
//            remainingCardsLabel.setText("Cards in Deck: " + admin.getRemainingCards());
//            playerHeroName = admin.getDeckHero();
//            handCardsName = (ArrayList) admin.getHandCards().clone();
//            groundCardsName = (ArrayList) admin.getGroundCards().clone();
//            updateGCards_GHero();
//            updateEventList();
//            repaint();
//        }
//
//        private void updateGCards_GHero() {
//            handCards = new GCard[12];
//            groundCards = new GCard[7];
//            playerHero = new GHero(GUIConfigLoader.playMenu.playerHero_size, playerHeroName, GUIConfigLoader.playMenu.playerHero_location);
//            heroPower = new GHeroPower(GUIConfigLoader.playMenu.heroPower_size, playerHeroName, GUIConfigLoader.playMenu.heroPower_location);
//            for (int i = 0; i < handCardsName.size(); i++) {
//                handCards[i] = new GCard(GUIConfigLoader.playMenu.handCard_size, handCardsName.get(i), GUIConfigLoader.playMenu.handCards_location[i]);
//            }
//            for (int i = 0; i < groundCardsName.size(); i++) {
//                groundCards[i] = new GCard(GUIConfigLoader.playMenu.groundCard_size,
//                        groundCardsName.get(i),
//                        GUIConfigLoader.playMenu.groundCards_location[i]);
//            }
//        }
//
//        private void updateEventList() {
//
//            String[] events=new String[gameEventsName.size()];
//            events = gameEventsName.toArray(events);
//            gameEvents.setListData(events);
//
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = addRenderingHint(g);
//            g2d.drawImage(new ImageIcon(GUIConfigLoader.playMenu.backgroundImage.getPath()).getImage(), 0, 0, 1200, 700, null);
//
//            paintCards_Hero(g2d);
//            if (cardOverview != null)
//                cardOverview.render(g2d);
//        }
//
//        private void paintCards_Hero(Graphics2D g2d) {
//            // rendering cards
//            for (int i = 0; i < 12; i++) {
//                if (handCards[i] != null) {
//                    handCards[i].render(g2d);
//                }
//            }
//            for (int i = 0; i < 7; i++) {
//                if (groundCards[i] != null) {
//                    groundCards[i].render(g2d);
//                }
//            }
//            if (playerHero != null)
//                playerHero.render(g2d);
//            if (heroPower != null)
//                heroPower.render(g2d);
//        }
//
//
//        private void refresh() {
//
//        }
//    }
//
//    // a panel for passive info
//    private class PassivePanel extends JPanel {
//        // passive buttons
//        private JButton passive1, passive2, passive3, passiveBackButton;
//
//        public PassivePanel() {
//            setLayout(null);
//
//            setBounds(GUIConfigLoader.playMenu.passivePanel_bounds);
//            setBorder(GUIConfigLoader.playMenu.passivePanel_border);
//            setOpaque(false);
//            initButtons();
//
//        }
//
//        private void initButtons() {
//            passive1 = new JButton(admin.getPassiveText(0));
//            passive1.setBounds(GUIConfigLoader.playMenu.passive1Button_bounds);
//            passive1.addActionListener(action);
//            add(passive1);
//
//            passive2 = new JButton(admin.getPassiveText(1));
//            passive2.setBounds(GUIConfigLoader.playMenu.passive2Button_bounds);
//            passive2.addActionListener(action);
//            add(passive2);
//
//            passive3 = new JButton(admin.getPassiveText(2));
//            passive3.setBounds(GUIConfigLoader.playMenu.passive3Button_bounds);
//            passive3.addActionListener(action);
//            add(passive3);
//
//            passiveBackButton = new JButton("Back");
//            passiveBackButton.setBounds(GUIConfigLoader.playMenu.passiveBackButton_bounds);
//            passiveBackButton.addActionListener(action);
//            add(passiveBackButton);
//
//        }
//
//        private void refresh() {
//            passive1.setText(admin.getPassiveText(0));
//            passive2.setText(admin.getPassiveText(1));
//            passive3.setText(admin.getPassiveText(2));
//        }
//    }
//
//    private class Actions implements ActionListener, MouseListener, MouseMotionListener {
//
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//// passive panel actions
//            if (e.getSource() == passivePanel.passive1) {
//                admin.writeLog("Passive Selected",admin.getPassiveText(0));
//                admin.selectPassive(0);
//                gotoPlayground();
//            } else if (e.getSource() == passivePanel.passive2) {
//                admin.writeLog("Passive Selected",admin.getPassiveText(1));
//
//                admin.selectPassive(1);
//                gotoPlayground();
//            } else if (e.getSource() == passivePanel.passive3) {
//                admin.writeLog("Passive Selected",admin.getPassiveText(2));
//
//                admin.selectPassive(2);
//                gotoPlayground();
//
//            } else if (e.getSource() == passivePanel.passiveBackButton) {
//
//                admin.writeLog("Back button","Clicked");
//                GameScreen.getInstance().gotoMenu("main");
//
//            }
//
//            // playground panel actions
//            if (e.getSource() == playground.endTurnButton) {
//                admin.writeLog("End Turn button","Clicked");
//
//                admin.nextTurn();
//                playground.gameEventsName.add("end turn");
//                playground.updateInfo();
//
//            } else if (e.getSource() == playground.backButton) {
//                if (Message.showConfirmMessage("Back to main menu", "Are you sure??")) {
//                    admin.writeLog("Back button","Clicked");
//                    playground.gameEventsName = new ArrayList<>();
//                    playground.gameEvents.removeAll();
//                    GameScreen.getInstance().gotoMenu("main");
//                }
//
//            }
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//            // playground panel cards
//            if (e.getSource() == playground) {
//                for (int i = 11; i >= 0; i--) {
//                    // checking whether a card is clicked
//                    if (playground.handCards[i] != null && playground.handCards[i].isClicked(e)) { // card is clicked
//                        int a = admin.playCard(playground.handCards[i].getCardName());
//
//                        // if block is just created for writing event list
//                        if (a == 1) {  // minion card played
//                            admin.writeLog("Minion Card Played",playground.handCards[i].getCardName());
//                            playground.gameEventsName.add("Minion Played: "+playground.handCards[i].getCardName());
//
//                        } else if (a == 2) {  // spell card played
//                            admin.writeLog("Spell Card Played",playground.handCards[i].getCardName());
//                            playground.gameEventsName.add("Spell Played: "+playground.handCards[i].getCardName());
//
//                        } else if (a == 3) { // weapon card played
//                            admin.writeLog("Weapon Card Played",playground.handCards[i].getCardName());
//                            playground.gameEventsName.add("Weapon Played: "+playground.handCards[i].getCardName());
//
//                        }else if(a==-1){
//                            admin.writeLog("Error","Deck is full");
//                        }else if(a==0){
//
//                            admin.writeLog("Error","Not enough mana for " +playground.handCards[i].getCardName());
//
//                        }
//                        playground.updateInfo();
//                        break;
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseDragged(MouseEvent e) {
//
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//            boolean onCard = false;
//            for (int i = 11; i >= 0; i--) {
//                if (playground.handCards[i] != null && playground.handCards[i].isClicked(e)) {
//                    playground.setCardOverview(i);
//                    onCard = true;
//                    break;
//                }
//            }
//            if (onCard == false) {
//                playground.cardOverview = null;
//                playground.repaint();
//            }
//        }
//    }
}

