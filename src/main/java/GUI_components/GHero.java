package GUI_components;

import Logic_GUIInterfaces.GamerHero;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GHero extends GraphicalObject {

    private Rectangle heroBox;
    private int hp;
    private GamerHero gamerHero;
    private GWeapon heroWeapon;
    private Point weaponLocation;

    public GHero(Dimension dimension, GamerHero gamerHero, Point location, Point weaponLocation) {
        this.name = gamerHero.getName();
        this.dimension = dimension;
        this.xPos = location.x;
        this.yPos = location.y;
        this.weaponLocation = weaponLocation;
        this.heroBox = new Rectangle(xPos, yPos, dimension.width, dimension.height);
        this.gamerHero = gamerHero;
        objectImage=imageLoader.getHeroImage(name);
//        try {
//            objectImage = ImageIO.read(new File(guiConfigLoader.getString("GHero_path")+name+".png"));
//            System.out.println(name);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        sync();
    }

    public void sync() {
        this.hp = gamerHero.getHp();
        syncWeapon();
    }

    private void syncWeapon() {
        if (gamerHero.getGamerWeapon() != null) {
            this.heroWeapon = new GWeapon(guiConfigLoader.getSize("GWeapon_size"), gamerHero.getGamerWeapon(), weaponLocation);
        } else {
            heroWeapon = null;
        }

    }
//    public GHero(Dimension dimension, String name, int xPos, int yPos) {
//        this.dimension = dimension;
//        this.name = name;
//        this.xPos = xPos;
//        this.yPos = yPos;
//        heroBox = new Rectangle(xPos, yPos, dimension.width, dimension.height);
//        try {
//            objectImage = ImageIO.read(GUIConfigLoader.gHero.getFile(name));
//        } catch (IOException e) {
//
//        }
//    }

//    public GHero(Dimension dimension, String name, Point O) {
//        this.dimension = dimension;
//        this.name = name;
//        this.xPos = O.x;
//        this.yPos = O.y;
//        heroBox = new Rectangle(xPos, yPos, dimension.width, dimension.height);
//        try {
//            objectImage = ImageIO.read(GUIConfigLoader.gHero.getFile(name));
//        } catch (IOException e) {
//
//        }
//    }


    // this method should render weapon
    public void render(Graphics2D g2d) {
        if (heroWeapon != null)
            heroWeapon.render(g2d);
        g2d.drawImage(objectImage, xPos, yPos, dimension.width, dimension.height, null);
        drawText(g2d);
    }

    private void drawText(Graphics2D g2d) {
        g2d.setPaint(Color.WHITE);
        g2d.setFont(new Font(guiConfigLoader.getString("GHero_font_name"), Font.BOLD,
                guiConfigLoader.getInt("GHero_font_size")));
        g2d.drawString(hp + "", getxPos() + guiConfigLoader.getInt("GHero_hp_x"),
                getyPos() + guiConfigLoader.getInt("GHero_hp_y"));
    }

    public boolean isMouseInside(MouseEvent e) {
        if (heroBox.contains(e.getPoint())) {
            return true;
        }
        return false;
    }
}

