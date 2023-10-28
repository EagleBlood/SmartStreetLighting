import java.awt.*;

public class Dot {
    private int x;
    private int y;
    private static int x1 = 50;
    private static int y1 = 100;
    private static int x2 = 250;
    private static int y3 = 300;
    private static int x3 = 250;
    private static int x4 = 50;
    private static int step = 1;

    private boolean inLampRange;

    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 10;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(DOT_COLOR);
        // Draw the dot from its center
        int drawX = x - DOT_SIZE / 2;
        int drawY = y - DOT_SIZE / 2;
        g.fillOval(drawX, drawY, DOT_SIZE, DOT_SIZE);
    }

    public void moveDot() {


        if (x >= x1 && x < x2 && y == y1) {
            x += step;
        } else if (x == x2 && y >= y1 && y < y3) {
            y += step;
        } else if (x <= x3 && x > x4 && y == y3) {
            x -= step;
        } else if (x == x4 && y <= y3 && y > y1) {
            y -= step;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY3() {
        return y3;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public boolean isInLampRange() {
        return inLampRange;
    }

    public void setInLampRange(boolean inLampRange) {
        this.inLampRange = inLampRange;
    }
}
