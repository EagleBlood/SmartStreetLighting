import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Lamp {
    private Point2D.Double position;
    private boolean active = false;
    private static final int LAMP_RADIUS = 30;
    private static final Color LAMP_COLOR = Color.ORANGE;
    private static final Color LAMP_LIT_COLOR = Color.RED;
    private static final int LAMP_SIZE = 10;

    public Lamp(Point2D.Double position) {
        this.position = position;
    }

    public void draw(Graphics g, Dot dot) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(LAMP_COLOR);
        int drawX = (int) position.getX() - LAMP_SIZE / 2;
        int drawY = (int) position.getY() - LAMP_SIZE / 2;
        g2d.fill(new Ellipse2D.Double(drawX, drawY, LAMP_SIZE, LAMP_SIZE));

        if (isDotInRange(dot)) {
            active = true;
        } else {
            active = false;
        }
        
        if (active) {
            g2d.setColor(LAMP_LIT_COLOR);
            g2d.draw(new Ellipse2D.Double(position.getX() - LAMP_RADIUS, position.getY() - LAMP_RADIUS, 2 * LAMP_RADIUS, 2 * LAMP_RADIUS));
        }
    }

    private boolean isDotInRange(Dot dot) {
        double distance = position.distance(dot.getPosition());
        return distance <= LAMP_RADIUS;
    }

    public void setPosition(Point2D.Double newPosition) {
        this.position = newPosition;
    }

    public boolean isActive() {
        return active;
    }
}
