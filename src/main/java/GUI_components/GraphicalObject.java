package GUI_components;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GraphicalObject implements Paintable {

    protected GUIConfigLoader guiConfigLoader=new GUIConfigLoader("components");
    protected String name;
    protected int xPos, yPos;
    protected Dimension dimension;

    protected BufferedImage objectImage;


    public abstract void render(Graphics2D g2d);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public BufferedImage getObjectImage() {
        return objectImage;
    }

    public void setObjectImage(BufferedImage objectImage) {
        this.objectImage = objectImage;
    }
}
