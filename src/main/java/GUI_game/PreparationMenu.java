package GUI_game;

import GUI_components.GButton;
import GUI_components.GUIConfigLoader;
import GUI_menus.GameMenu;
import GUI_menus.GameScreen;
import Logic_GUIInterfaces.PlayAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreparationMenu extends GameMenu {

    private GButton backButton;
    private Actions actions = new Actions();
    private PlayModePanel playModePanel;
    private PassivePanel passivePanel;
    private String currentPanelName;
    private PlayAdmin playAdmin = PlayAdmin.getInstance();

    public PreparationMenu() {
        guiConfigLoader = new GUIConfigLoader("preparation");
        initMenu();
    }

    private void initMenu() {
        setLayout(null);
        setBounds(guiConfigLoader.getBounds("menuBounds"));
        setSize(guiConfigLoader.getSize("menuBounds"));
        setPreferredSize(guiConfigLoader.getSize("menuBounds"));
        initButtons();
        initPanels();
    }

    private void initPanels() {
        playModePanel = new PlayModePanel();
        passivePanel = new PassivePanel();
        add(playModePanel);
        add(passivePanel);
        gotoPanel("play mode");
    }

    private void initButtons() {
        backButton = new GButton();
        backButton.setText("Back");
        backButton.setBounds(guiConfigLoader.getBounds("backButton_bounds"));
        backButton.addActionListener(actions);
        add(backButton);
    }

    private void gotoPanel(String panelName) {
        this.currentPanelName = panelName;
        playModePanel.setVisible(false);
        passivePanel.setVisible(false);

        if (currentPanelName.equals("play mode")) {
            playModePanel.setVisible(true);

        } else if (currentPanelName.equals("passive")) {
            passivePanel.setVisible(true);

        }
    }

    private class PlayModePanel extends JPanel {
        private GButton selfPlayButton, preparedModeButton, computerModeButton;

        public PlayModePanel() {
            initPanel();

        }

        private void initPanel() {
            setLayout(null);
            setBounds(guiConfigLoader.getBounds("playModePanel_bounds"));
            setBorder(GUIConfigLoader.playMenu.passivePanel_border);
            setOpaque(false);
            initButtons();
        }

        private void initButtons() {
            selfPlayButton = new GButton(guiConfigLoader.getString("selfPlayButton_text"));
            selfPlayButton.setBounds(guiConfigLoader.getBounds("selfPlayButton_bounds"));
            selfPlayButton.addActionListener(actions);
            add(selfPlayButton);

            preparedModeButton = new GButton(guiConfigLoader.getString("preparedModeButton_text"));
            preparedModeButton.setBounds(guiConfigLoader.getBounds("preparedModeButton_bounds"));
            preparedModeButton.addActionListener(actions);
            add(preparedModeButton);

            computerModeButton = new GButton(guiConfigLoader.getString("computerModeButton_text"));
            computerModeButton.setBounds(guiConfigLoader.getBounds("computerModeButton_bounds"));
            computerModeButton.addActionListener(actions);
            add(computerModeButton);
        }

        private void refresh() {


        }
    }

    // a panel for passive info
    private class PassivePanel extends JPanel {
        // passive buttons
        private GButton passive1, passive2, passive3;

        public PassivePanel() {
            initPanel();
        }

        private void initPanel() {
            setLayout(null);
            setBounds(guiConfigLoader.getBounds("passivePanel_bounds"));
            setBorder(GUIConfigLoader.playMenu.passivePanel_border);
            setOpaque(false);
            initButtons();
        }

        private void initButtons() {
            passive1 = new GButton(playAdmin.getPassiveText(0));
            passive1.setBounds(guiConfigLoader.getBounds("passive1Button_bounds"));
            passive1.addActionListener(actions);
            add(passive1);

            passive2 = new GButton(playAdmin.getPassiveText(1));
            passive2.setBounds(guiConfigLoader.getBounds("passive2Button_bounds"));
            passive2.addActionListener(actions);
            add(passive2);

            passive3 = new GButton(playAdmin.getPassiveText(2));
            passive3.setBounds(guiConfigLoader.getBounds("passive3Button_bounds"));
            passive3.addActionListener(actions);
            add(passive3);
        }

        private void refresh() {
            passive1.setText(playAdmin.getPassiveText(0));
            passive2.setText(playAdmin.getPassiveText(1));
            passive3.setText(playAdmin.getPassiveText(2));
        }


    }


    private class Actions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // back button
            if (e.getSource() == backButton) {
                GameScreen.getInstance().gotoMenu("main");
            }

            // play mode panel actions
            if (e.getSource() == playModePanel.selfPlayButton) {
                playAdmin.setPlayMode(0);
                gotoPanel("passive");
            } else if (e.getSource() == playModePanel.preparedModeButton) {
                playAdmin.setPlayMode(1);
                gotoPanel("passive");
            } else if (e.getSource() == playModePanel.computerModeButton) {
                playAdmin.setPlayMode(2);
                gotoPanel("passive");
            }

            // passive panel actions
            if (e.getSource() == passivePanel.passive1) {
                playAdmin.selectPassive(0);
                GameScreen.getInstance().gotoMenu("play");
            } else if (e.getSource() == passivePanel.passive2) {
                playAdmin.selectPassive(1);
                GameScreen.getInstance().gotoMenu("play");
            } else if (e.getSource() == passivePanel.passive3) {
                playAdmin.selectPassive(2);
                GameScreen.getInstance().gotoMenu("play");
            }
        }
    }
}
