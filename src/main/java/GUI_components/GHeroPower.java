package GUI_components;

import Logic_GUIInterfaces.GamerHeroPower;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

public class GHeroPower extends GraphicalObject {

    private GamerHeroPower gamerHeroPower;
    private int manaCost;
    private boolean enabled = true;

    private Ellipse2D powerBox;


    public GHeroPower(Dimension dimension, GamerHeroPower gamerHeroPower, Point location) {
        this.name = gamerHeroPower.getHeroName();
        this.gamerHeroPower = gamerHeroPower;
        this.xPos = location.x;
        this.yPos = location.y;
        this.dimension = dimension;
        this.powerBox=new Ellipse2D.Double(xPos,yPos,dimension.width,dimension.height);
        objectImage=imageLoader.getPowerImage(name);
//        try {
//            objectImage = ImageIO.read(new File(guiConfigLoader.getString("GHeroPower_path") + name + ".png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        sync();
    }

    public void sync() {
        this.manaCost = gamerHeroPower.getManaCost();
        this.enabled = gamerHeroPower.isEnabled();
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(objectImage, xPos, yPos, dimension.width, dimension.height, null);
        drawText(g2d);
        drawEffects(g2d);
    }

    private void drawText(Graphics2D g2d) {
        g2d.setPaint(Color.WHITE);
        g2d.setFont(new Font(guiConfigLoader.getString("GHeroPower_font_name"), Font.BOLD,
                guiConfigLoader.getInt("GHeroPower_font_size")));
        g2d.drawString(manaCost + "", getxPos() + guiConfigLoader.getInt("GHeroPower_manaCost_x"),
                getyPos() + guiConfigLoader.getInt("GHeroPower_manaCost_y"));
    }

    private void drawEffects(Graphics2D g2d) {
        if (!enabled) {
            g2d.setPaint(new Color(50, 50, 50, 180));
            g2d.fillOval(xPos, yPos, dimension.width, dimension.height);
        }
    }

    public boolean isMouseInside(MouseEvent e){
        if(powerBox.contains(e.getPoint())){
            return true;
        }else{
            return false;
        }
    }
}
