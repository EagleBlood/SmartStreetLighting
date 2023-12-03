import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Lamp {
    private final Point2D.Double position;
    private Graphics2D g2d;
    private boolean active = false;
    private static final int LAMP_RADIUS = 15; // Adjusted radius for 150 lux norm
    private static final Color LAMP_COLOR = Color.ORANGE;
    private static final Color LAMP_LIT_COLOR = Color.RED;
    private static final int LAMP_SIZE = 10;

    public Lamp(Point2D.Double position) {
        this.position = position;
    }

    public void draw(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setColor(LAMP_COLOR);
        int drawX = (int) position.getX() - LAMP_SIZE / 2;
        int drawY = (int) position.getY() - LAMP_SIZE / 2;
        g2d.fill(new Ellipse2D.Double(drawX, drawY, LAMP_SIZE, LAMP_SIZE));
    }

    public void activate(Dot dot) {
        active = dot != null && isDotInRange(dot) && isTimeInRange(SettingsPanel.getCurrentTime());
        if (active) {
            g2d.setColor(LAMP_LIT_COLOR);
            // Draw the effective lighting area
            g2d.draw(new Ellipse2D.Double(position.getX() - LAMP_RADIUS, position.getY() - LAMP_RADIUS, 2 * LAMP_RADIUS, 2 * LAMP_RADIUS));
        }
    }

    private boolean isTimeInRange(String currentTime) {
        // Convert time to int for comparison
        int currentHour = Integer.parseInt(currentTime.split(":")[0]);

        // Check if the hour is between 17:00 and 6:00
        return (currentHour >= 17 && currentHour <= 23) || (currentHour >= 0 && currentHour < 6);
    }

    private boolean isDotInRange(Dot dot) {
        double distance = position.distance(dot.getPosition());
        return distance <= LAMP_RADIUS;
    }

    public Point2D.Double getPosition() {
        return position;
    }
}
