import java.awt.*;
import java.util.List;

public class Lamp {
    private int x;
    private int y;

    private static final int LAMP_RADIUS = 60;
    private static final Color LAMP_COLOR = Color.ORANGE;
    private static final int LAMP_SIZE = 10;

    public Lamp(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(LAMP_COLOR);
        // Draw the dot from its center
        int drawX = x - LAMP_SIZE / 2;
        int drawY = y - LAMP_SIZE / 2;
        g.fillOval(drawX, drawY, LAMP_SIZE, LAMP_SIZE);
    }

    public void activateDots(List<Dot> dots) {
        for (Dot dot : dots) {
            if (checkActivation(dot)) {
                // You can perform actions or set flags on the Dot here
            }
        }
    }

    public boolean checkActivation(Dot dot) {
        int dotX = dot.getX();
        //int dotY = dot.getY();
        return Math.abs(dotX - x) <= LAMP_RADIUS;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
