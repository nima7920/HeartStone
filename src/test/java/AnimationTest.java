import GUI_components.GCard;
import GUI_components.AnimationEngine;

import javax.swing.*;
import java.awt.*;

public class AnimationTest extends JFrame {

    private AnimationEngine animationEngine;
    private GCard myCard;

    public AnimationTest() {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(0, 0, 1000, 500);
        setVisible(true);

        myCard = new GCard(new Dimension(100, 140), "Arcane Servant", 0, 300);

        MyPanel myPanel = new MyPanel();
        myPanel.setLayout(null);
        myPanel.setBounds(0, 0, 1000, 500);
        add(myPanel);
        animationEngine = new AnimationEngine(myPanel);
        animationEngine.setDelay(25);
        animationEngine.setDuration(500);
        animationEngine.move(myCard, new Point(500, 100));
    }

    private class MyPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = addRenderingHint(g);
            myCard.render(g2d);
        }

        private Graphics2D addRenderingHint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            rh.put(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHints(rh);
            return g2d;

        }
    }

    public static void main(String[] args) {
        new AnimationTest();
    }
}
