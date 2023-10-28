import java.awt.*;

public class Dot {
    private int x;
    private int y;
    private static int step = 1;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 10, 10);
    }

    public void moveDot() {
        int x1 = 50;
        int y1 = 100;
        int x2 = 250;
        int x3 = 250;
        int y3 = 300;
        int x4 = 50;

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

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }
}
