import java.awt.*;
import java.util.List;

public class Lamp {
    private int x;
    private int y;
    private boolean active = false;

    private static final int LAMP_RADIUS = 60;
    private static final Color LAMP_COLOR = Color.ORANGE;
    private static final int LAMP_SIZE = 10;

    public Lamp(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, Dot dot) {
        g.setColor(LAMP_COLOR);
        // Draw the dot from its center
        int drawX = x - LAMP_SIZE / 2;
        int drawY = y - LAMP_SIZE / 2;
        g.fillOval(drawX, drawY, LAMP_SIZE, LAMP_SIZE);

        if (checkActivation(dot)) {
            g.setColor(new Color(255, 165, 0, 102));
            g.drawOval(x - LAMP_RADIUS, 400 / 2 - LAMP_RADIUS, 2 * LAMP_RADIUS, 2 * LAMP_RADIUS);
            active = true;
        } else {
            active = false;
        }
    }

    public void activateDots(List<Dot> dots) {
        for (Dot dot : dots) {
            if (checkActivation(dot)) {
                dot.setInLampRange(true);
            } else {
                dot.setInLampRange(false);
            }
        }
    }

    public boolean checkActivation(Dot dot) {
        int dotX = dot.getX();
        int dotY = dot.getY();
        int lampX = x;
        int lampY = y;
        double distance = Math.sqrt(Math.pow(dotX - lampX, 2) + Math.pow(dotY - lampY, 2));
        return distance <= LAMP_RADIUS;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isActive() {
        return active;
    }
}
