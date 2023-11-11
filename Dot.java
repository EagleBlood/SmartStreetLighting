import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class Dot{
    private double step = 3;
    private boolean inLampRange;
    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 12;
    private Point2D.Double position; // Current position of the dot
    private List<Path> paths;
    private Path path;
    private int currentPathIndex = 0;
    private double currentDistance = 0.0;

    public Dot(Point2D.Double initialPosition) {
        this.position = initialPosition;
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
        if (paths == null || paths.isEmpty()) {
            // No paths to follow, exit.
            return;
        }
    
        // Calculate the total length of all paths
        double totalLength = paths.stream().mapToDouble(Path::getLength).sum();
    
        // Check if the dot has reached the end of the entire path sequence
        if (currentDistance >= totalLength) {
            // Move to the beginning of the first path
            currentDistance = 0.0;
            currentPathIndex = 0;
        }
    
        // Find the current path and update the distance
        Path currentPath = null;
        double remainingDistance = currentDistance;
        int i;
        for (i = 0; i < paths.size(); i++) {
            currentPath = paths.get(i);
            double pathLength = currentPath.getLength();
            if (remainingDistance < pathLength) {
                break;
            }
            remainingDistance -= pathLength;
        }
    
        // Get the current position on the current path
        double[] pointAtLength = currentPath.getPointAtLength(remainingDistance);
        position.setLocation(pointAtLength[0], pointAtLength[1]);
    
        // Update the canvas to repaint the dot's position
        if (currentPath.getCanvas() != null) {
            ((Canvas) currentPath.getCanvas()).repaint();
        }
    
        // Update the current path index and distance for the next iteration
        currentPathIndex = i;
        currentDistance += step;
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

    public void setPathList(List<Path> paths) {
        this.paths = paths;
        if (!paths.isEmpty()) {
            // Find the last path in the list
            this.path = paths.get(paths.size() - 1);
            
            // Set the current path index based on the last path
            this.currentPathIndex = paths.size() - 1;
            
            // Set the distance to the total distance traveled across all paths
            this.currentDistance = paths.stream().limit(currentPathIndex).mapToDouble(Path::getLength).sum();
        }
    }
    
}
