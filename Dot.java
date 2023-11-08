import java.awt.*;
import java.awt.geom.Point2D;

public class Dot {
    private double step = 3;
    private boolean inLampRange;
    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 12;
    private Point2D.Double position; // Current position of the dot
    private Path path;
    private double distance = 0.0;

    public Dot(Point2D.Double initialPosition, Path path) {
        this.position = initialPosition;
        this.path = path;
    }

    public void draw(Graphics g) {
        g.setColor(DOT_COLOR);

        // Draw the dot from its center
        int drawX = (int) position.getX() - DOT_SIZE / 2;
        int drawY = (int) position.getY() - DOT_SIZE / 2;
        g.fillOval(drawX, drawY, DOT_SIZE, DOT_SIZE);

        // Move the dot on the path
        moveDot();
    }

    public void moveDot() {
        if (path == null) {
            // No path to follow, exit.
            return;
        }

        if (distance >= path.getLength()) {
            // If the dot has reached the end of the path, move to the next path segment
            if (path.hasNextPath()) {
                path = path.getNextPath();
                distance = 0.0;
            } else {
                // If there are no more paths, reset to the beginning of the path
                path = path.getFirstPath();
                distance = 0.0;
            }
        }

        float[] pointAtLength = path.getPointAtLength(distance);
        position.setLocation(pointAtLength[0], pointAtLength[1]);
        distance += step;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public void setPosition(Point2D.Double newPosition) {
        this.position = newPosition;
    }

    public boolean isInLampRange() {
        return inLampRange;
    }

    public void setInLampRange(boolean inLampRange) {
        this.inLampRange = inLampRange;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
