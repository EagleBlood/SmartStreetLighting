import java.awt.*;
import java.util.List;

public class Lamp {
    private int x;
    private int y;
    private static final int LAMP_RADIUS = 60;

    public Lamp(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, 10, 10);
    }

    public void activateDots(List<Dot> dots) {
        for (Dot dot : dots) {
            if (checkActivation(dot)) {
                // You can perform actions or set flags on the Dot here
            }
        }
    }

    private boolean checkActivation(Dot dot) {
        int dotX = dot.getX();
        int dotY = dot.getY();
        return Math.abs(dotX - x) <= LAMP_RADIUS;
    }
}
