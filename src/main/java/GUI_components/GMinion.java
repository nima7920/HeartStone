package GUI_components;

import Logic_GUIInterfaces.GamerMinion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GMinion extends GraphicalObject {

    // class fields
    // these boolean are just used for drawing the card and are not checked by GUI
    private boolean enabled = false;
    private boolean inRush = false;
    private boolean inCharge = false;
    private boolean isTaunt = false;
    private boolean hasDivineShield = false;
    private boolean isLifeSteal = false;
    private boolean isPoisonous = false;
    private boolean isWindFury = false;

    private int attack = 1;
    private int hp = 1;

    private Ellipse2D minionBox;
    private GamerMinion gamerMinion;

    // constructors
    public GMinion(Dimension dimension, GamerMinion gamerMinion, Point location) {
        this.dimension = dimension;
        this.xPos = location.x;
        this.yPos = location.y;
        this.minionBox = new Ellipse2D.Double(xPos, yPos, dimension.width, dimension.height);
        this.gamerMinion = gamerMinion;
        sync();
    }

    public void sync() {
        this.name = gamerMinion.getName();
        this.hp = gamerMinion.getHp();
        this.attack = gamerMinion.getAttack();
        syncAbilities();
        try {
            objectImage = ImageIO.read(new File(guiConfigLoader.getString("GMinion_path") + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void syncAbilities() {
        enabled = false;
        inRush = false;
        inCharge = false;
        isTaunt = false;
        hasDivineShield = false;
        isLifeSteal = false;
        isPoisonous = false;
        isWindFury = false;

        if (gamerMinion.isEnabled())
            this.enabled = true;
        if (gamerMinion.getIsTaunt())
            this.isTaunt = true;
        if (gamerMinion.getHasDivineShield())
            this.hasDivineShield = true;
        if (gamerMinion.getIsLifeSteal())
            this.isLifeSteal = true;
        if (gamerMinion.getIsPoisonous())
            this.isPoisonous = true;
        if (gamerMinion.isInCharge())
            this.inCharge = true;
        if (gamerMinion.isInRush())
            this.inRush = true;
        if (gamerMinion.getIsWindFury())
            this.isWindFury = true;

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(objectImage, xPos, yPos, dimension.width, dimension.height, null);
        drawEffects(g2d);
        drawText(g2d);
    }

    private void drawText(Graphics2D g2d) {
        g2d.setPaint(Color.WHITE);
        g2d.setFont(new Font(guiConfigLoader.getString("GMinion_font_name"), Font.BOLD,
                guiConfigLoader.getInt("GMinion_font_size")));
        g2d.drawString(attack + "", getxPos() + guiConfigLoader.getInt("GMinion_attack_x"),
                getyPos() + guiConfigLoader.getInt("GMinion_attack_y"));
        g2d.drawString(hp + "", getxPos() + guiConfigLoader.getInt("GMinion_hp_x"),
                getyPos() + guiConfigLoader.getInt("GMinion_hp_y"));
    }

    private void drawEffects(Graphics2D g2d) {
        if (!enabled) {
            g2d.setPaint(new Color(50, 50, 50, 180));
            g2d.fillOval(xPos, yPos, dimension.width, dimension.height);
        }
        if (isTaunt) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_isTauntColor"));
            g2d.drawOval(xPos, yPos, dimension.width, dimension.height);

        }
        if (isWindFury) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_isWindFuryColor"));
            g2d.drawOval(xPos + 1, yPos + 1, dimension.width - 2, dimension.height - 2);
        }
        if (isPoisonous) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_isPoisonousColor"));
            g2d.drawOval(xPos + 2, yPos + 2, dimension.width - 4, dimension.height - 4);
        }
        if (isLifeSteal) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_isLifeStealColor"));
            g2d.drawOval(xPos + 3, yPos + 3, dimension.width - 6, dimension.height - 6);
        }
        if (hasDivineShield) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_hasDivineShieldColor"));
            g2d.drawOval(xPos + 4, yPos + 4, dimension.width - 8, dimension.height - 8);
        }
        if (inRush) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_inRushColor"));
            g2d.drawOval(xPos + 5, yPos + 5, dimension.width - 10, dimension.height - 10);
        }
        if (inCharge) {
            g2d.setPaint(guiConfigLoader.getColor("GMinion_inChargeColor"));
            g2d.drawOval(xPos + 6, yPos + 6, dimension.width - 12, dimension.height - 12);
        }
    }

    public boolean isMouseInside(MouseEvent mouseEvent) {
        minionBox = new Ellipse2D.Double(xPos, yPos, dimension.width, dimension.height);
        if (minionBox.contains(mouseEvent.getPoint()))
            return true;
        else
            return false;
    }

}
