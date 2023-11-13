import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
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
    private List<Drawable> allDrawables;
    private Map<Drawable, List<Drawable>> drawableConnections;
    private Drawable currentDrawable;
    private Random random = new Random();
    private int currentDrawableIndex;
    private Drawable previousDrawable;
    private boolean isReversing = false;
    private Drawable lastReversedDrawable;
    private double tolerance = DOT_SIZE / 2.0;


    public Dot(Point2D.Double position, List<Drawable> drawables, List<Drawable> allDrawables, Map<Drawable, List<Drawable>> drawableConnections) {
        this.position = position;
        this.drawables = drawables;
        this.drawableConnections = drawableConnections; // Initialize drawableConnections
        this.currentDrawable = getFirstDrawable();
        this.allDrawables = allDrawables;
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
        // Update connections
        for (Drawable drawable : allDrawables) {
            double distance = currentDrawable.getDistanceTo(drawable);
            if (distance < threshold) {
                addConnection(currentDrawable, drawable);
            } else {
                removeConnection(currentDrawable, drawable);
            }
        }
        
        if (currentDrawable == null) {
            System.out.println("CurrentDrawable is null");
            return;
        }
    
        Double currentPosition = currentDrawable.getPosition(currentDistance);
        Double nextPosition;
    
        if (currentDistance >= currentDrawable.getLength() - tolerance) {
            List<Drawable> connectedDrawables = drawableConnections.get(currentDrawable);
            if (connectedDrawables != null && !connectedDrawables.isEmpty()) {
                currentDrawable = getNextDrawable();
                currentDistance = 0.0;
                isReversing = false;
                System.out.println("Moving to next drawable");
            } else {
                currentDistance = currentDrawable.getLength() - tolerance;
                isReversing = true;
                System.out.println("Reversing");
            }
        } else if (currentDistance <= step) {
            isReversing = false;
            System.out.println("Forward");
        }
    
        if (isReversing) {
            nextPosition = currentDrawable.getPosition(currentDistance - step);
        } else {
            nextPosition = currentDrawable.getPosition(currentDistance + step);
        }
    
        if (nextPosition != null) {
            double dx = nextPosition.getX() - currentPosition.getX();
            double dy = nextPosition.getY() - currentPosition.getY();
            position.setLocation(currentPosition.getX() + dx, currentPosition.getY() + dy);
            if (isReversing) {
                currentDistance -= step;
            } else {
                currentDistance += step;
            }
        }
    }

    public void setAllDrawables(List<Drawable> allDrawables) {
        this.allDrawables = allDrawables;
    }
    
    
    
    
    public Drawable getNextDrawable() {
        List<Drawable> connectedDrawables = drawableConnections.get(currentDrawable);
        if (connectedDrawables != null && !connectedDrawables.isEmpty()) {
            for (Drawable drawable : connectedDrawables) {
                double distance = currentDrawable.getDistanceTo(drawable);
                if (distance < threshold) {
                    return drawable;
                }
            }
        }
        return null;
    }
    



    private Drawable getPreviousDrawable() {
        return previousDrawable;
    }

    public void addConnection(Drawable drawable1, Drawable drawable2) {
    List<Drawable> connections1 = drawableConnections.get(drawable1);
    if (connections1 == null) {
        connections1 = new ArrayList<>();
        drawableConnections.put(drawable1, connections1);
    }
    connections1.add(drawable2);

    List<Drawable> connections2 = drawableConnections.get(drawable2);
    if (connections2 == null) {
        connections2 = new ArrayList<>();
        drawableConnections.put(drawable2, connections2);
    }
    connections2.add(drawable1);
}

public void removeConnection(Drawable drawable1, Drawable drawable2) {
    List<Drawable> connections1 = drawableConnections.get(drawable1);
    if (connections1 != null) {
        connections1.remove(drawable2);
    }

    List<Drawable> connections2 = drawableConnections.get(drawable2);
    if (connections2 != null) {
        connections2.remove(drawable1);
    }
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
