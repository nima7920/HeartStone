package GUI_components;

import Logic_GUIInterfaces.GamerWeapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

public class GWeapon extends GraphicalObject {

    private GamerWeapon gamerWeapon;
    private int attack, durability;
    private boolean enabled;
    private Ellipse2D objectBox;

    public GWeapon(Dimension dimension, GamerWeapon gamerWeapon, Point location) {

        this.gamerWeapon = gamerWeapon;
        this.dimension = dimension;
        this.xPos = location.x;
        this.yPos = location.y;
        this.objectBox = new Ellipse2D.Double(xPos, yPos, dimension.width, dimension.height);
        sync();
    }

    public void sync() {
        this.attack = gamerWeapon.getAttack();
        this.durability = gamerWeapon.getDurability();
        this.name = gamerWeapon.getName();
        this.enabled = gamerWeapon.isEnabled();
        try {
            objectImage = ImageIO.read(new File(guiConfigLoader.getString("GWeapon_path") + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(objectImage, xPos, yPos, dimension.width, dimension.height, null);
        drawText(g2d);
        drawEnabled(g2d);
    }

    private void drawText(Graphics2D g2d) {

        g2d.setPaint(Color.WHITE);
        g2d.setFont(new Font(guiConfigLoader.getString("GWeapon_font_name"), Font.BOLD,
                guiConfigLoader.getInt("GWeapon_font_size")));
        g2d.drawString(attack + "", getxPos() + guiConfigLoader.getInt("GWeapon_attack_x"),
                getyPos() + guiConfigLoader.getInt("GWeapon_attack_y"));
        g2d.drawString(durability + "", getxPos() + guiConfigLoader.getInt("GWeapon_durability_x"),
                getyPos() + guiConfigLoader.getInt("GWeapon_durability_y"));
    }

    private void drawEnabled(Graphics2D g2d) {
        if (!enabled) {
            g2d.setPaint(new Color(50, 50, 50, 180));
            g2d.fillOval(xPos, yPos, dimension.width, dimension.height);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
