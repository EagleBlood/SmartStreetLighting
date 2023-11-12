import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dot{
    private double step = 4;
    private boolean inLampRange;
    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 12;
    private Point2D.Double position; // Current position of the dot
    private List<Path> paths;
    private Path path;
    private int currentPathIndex = 0;
    private double currentDistance = 0.0;
    private List<Drawable> drawables;
    private Map<Drawable, List<Drawable>> drawableConnections;
    private Drawable currentDrawable;
    private Random random = new Random();
    private int currentDrawableIndex;
    private Drawable previousDrawable;
    private boolean isReversing = false;
    private Drawable lastReversedDrawable;

    public Dot(Point2D.Double position, List<Drawable> drawables, Map<Drawable, List<Drawable>> drawableConnections) {
        this.position = position;
        this.drawables = drawables;
        this.drawableConnections = drawableConnections; // Initialize drawableConnections
        this.currentDrawable = getFirstDrawable();
    }

    private Drawable getFirstDrawable() {
        if (drawables == null || drawables.isEmpty()) {
            // No drawables to follow, exit.
            return null;
        }
    
        // Generate a random index
        int index = random.nextInt(drawables.size());
    
        // Return the Drawable at the generated index
        return drawables.get(0);
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
        if (currentDrawable == null) {
            // No drawable to follow, exit.
            return;
        }
    
        // Define a small tolerance for floating-point comparisons
        double tolerance = DOT_SIZE / 2.0;  // Adjust the tolerance to account for the radius of the dot
    
        Double currentPosition = currentDrawable.getPosition(currentDistance);
        Double nextPosition;
    
        // Check if the current distance exceeds the total length of the current drawable with tolerance
        if (currentDistance >= currentDrawable.getLength() - tolerance) {
            // Move to the next drawable when the drawable is complete
            currentDrawable = getNextDrawable();
            currentDistance = currentDrawable.getLength() - tolerance;
            isReversing = true;  // Start moving in the reverse direction
        } else if (currentDistance < 0) {
            // Move to the previous drawable when the drawable is complete in reverse
            currentDrawable = getNextDrawable();
            currentDistance = 0.0;
            isReversing = false;  // Start moving in the forward direction again
        }
    
        // Move the dot towards the next point on the drawable
        if (isReversing) {
            nextPosition = currentDrawable.getPosition(currentDistance - step);
        } else {
            nextPosition = currentDrawable.getPosition(currentDistance + step);
        }
    
        // Check if nextPosition is not null before accessing its coordinates
        if (nextPosition != null) {
            // Calculate the direction vector from the current position to the next position
            double dx = nextPosition.getX() - currentPosition.getX();
            double dy = nextPosition.getY() - currentPosition.getY();
    
            // Add the direction vector to the current position to get the new position
            position.setLocation(currentPosition.getX() + dx, currentPosition.getY() + dy);
    
            // Update the current distance
            if (isReversing) {
                currentDistance -= step;
            } else {
                currentDistance += step;
            }
        }
    }
    
    
    
    
    private Drawable getNextDrawable() {
        // Get the list of Drawable objects connected to the current Drawable
        List<Drawable> connectedDrawables = drawableConnections.get(currentDrawable);
    
        // Generate a random index
        int index = random.nextInt(connectedDrawables.size());
    
        // Return the Drawable at the generated index
        return connectedDrawables.get(index);
    }
    



    private Drawable getPreviousDrawable() {
        return previousDrawable;
    }

    
    
    
    public void setCurrentDrawable(Drawable drawable) {
        this.currentDrawable = drawable;
        if (drawable instanceof Path) {
            ((Path) drawable).setDot(this);
        }
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

    public void setDrawables(List<Drawable> drawables) {
        this.drawables = drawables;
    }

    public void setDrawableConnections(Map<Drawable, List<Drawable>> drawableConnections) {
        this.drawableConnections = drawableConnections;
    }

    public Drawable[] getDrawables() {
        return drawables.toArray(new Drawable[0]);
    }
    
}
