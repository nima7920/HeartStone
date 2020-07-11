package GUI_components;

import GUI_components.GraphicalObject;

import java.awt.*;

public class AnimationEngine {
    private Component container;
    private long duration;
    private long delay;
    private long numberOfMoves;
    public AnimationEngine(Component component) {
        this.container = component;
    }

    public void move(GraphicalObject graphicalObject, Point destination) {
        numberOfMoves=duration/delay;
        int xDifference = destination.x - graphicalObject.getxPos(), yDifference = destination.y - graphicalObject.getyPos();
        int movedX = graphicalObject.getxPos(), movedY = graphicalObject.getyPos();
        double xMovement = ((double) xDifference) / ((double) numberOfMoves);
        double yMovement = ((double) yDifference) / ((double) numberOfMoves);

        System.out.println(numberOfMoves);
        for (int i = 0; i <numberOfMoves ; i++) {
            movedX = (int) Math.floor(movedX + xMovement);
            movedY=(int) Math.floor(movedY+yMovement);
            graphicalObject.setxPos(movedX);
            graphicalObject.setyPos(movedY);
            container.revalidate();
            container.repaint();
            System.out.println("waiting at "+movedX + ","+movedY);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void moveReturn(GraphicalObject graphicalObject, Point destination) {
        Point originPoint = new Point(graphicalObject.getxPos(), graphicalObject.getyPos());
        move(graphicalObject, destination);
        move(graphicalObject, originPoint);

    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
