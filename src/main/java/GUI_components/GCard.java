package GUI_components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GCard extends GraphicalObject {

    private boolean isLocked = false;
    private Rectangle cardBox;

    public GCard(Dimension dimension, String name, int xPos, int yPos) {
        this.dimension = dimension;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        cardBox = new Rectangle(xPos, yPos, dimension.width, dimension.height);
        try {
            objectImage = ImageIO.read(GUIConfigLoader.gCard.getFile(name));
        } catch (IOException e) {

        }
    }

    public GCard(Dimension dimension, String name, Point O) {
        this.dimension = dimension;
        this.name = name;
        this.xPos = O.x;
        this.yPos = O.y;
        cardBox = new Rectangle(xPos, yPos, dimension.width, dimension.height);
        try {
            objectImage = ImageIO.read(getImageFile());
        } catch (IOException e) {

        }
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(objectImage, xPos, yPos, dimension.width, dimension.height, null);
        if (isLocked) { // card is locked , lockBox must be shown
            g2d.setPaint(new Color(50, 50, 50, 180));
            int xAlign = dimension.width / 15, yAlign = dimension.height / 15;
            g2d.fillRect(xPos + xAlign, yPos + yAlign, dimension.width - (2 * xAlign), dimension.height - yAlign);
        }
    }

    public boolean isMouseInside(MouseEvent e) {
        if (cardBox.contains(e.getPoint())) {
            return true;
        }
        return false;
    }

    protected File getImageFile() {
        return GUIConfigLoader.gCard.getFile(name);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Rectangle getObjectBox() {
        return cardBox;
    }


}


