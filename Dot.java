import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dot{
    private static final Color DOT_COLOR = Color.RED;
    private static final int DOT_SIZE = 12;
    private final Point2D.Double position; // Current position of the dot;
    private double currentDistance = 0.0;
    private final List<Drawable> drawables;
    private final Map<Drawable, List<Drawable>> drawableConnections;
    private Drawable currentDrawable;
    private Drawable previousDrawable;
    private boolean isReversing = false;
    private Random random = new Random();
    private double step = 4;


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
        //int index = random.nextInt(drawables.size());

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

        ///chyba tutaj trzeba dodać sprawdzanie lampy
    }

    public void moveDot() {
        if (currentDrawable == null) {
            System.out.println("CurrentDrawable is null");
            return;
        }

        Double currentPosition = currentDrawable.getPosition(currentDistance);
        Double nextPosition;

        for (Lamp lamp : currentDrawable.getLamps()) {
            lamp.activate(this);
        }

        
        double tolerance = DOT_SIZE / 2.0;
        if (currentDistance >= currentDrawable.getLength() - tolerance) {
            Drawable nextDrawable = getNextDrawable();
            if (nextDrawable != null) {
                previousDrawable = currentDrawable;
                currentDrawable = nextDrawable;
                currentDistance = 0.0;
                isReversing = false;
                System.out.println("Moving to next drawable");
            } else {
                isReversing = true;
                System.out.println("Reversing");
                // Add a connection back to the previousDrawable
                List<Drawable> connectedDrawables = drawableConnections.computeIfAbsent(currentDrawable, k -> new ArrayList<>());
                connectedDrawables.add(previousDrawable);
            }
        } else if (currentDistance <= step && isReversing) {
            isReversing = false;
            System.out.println("Forward");
            // Remove the connection to the previousDrawable
            List<Drawable> connectedDrawables = drawableConnections.get(currentDrawable);
            if (connectedDrawables != null) {
                connectedDrawables.remove(previousDrawable);
            }
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


    //TODO Znajduje tylko ścieszki któe są połączone z pierwszą ścieżką (w punkcie startowym) i to nie zawsze, trzeba to ogarnac
    public Drawable getNextDrawable() {
        List<Drawable> connectedDrawables = drawableConnections.get(currentDrawable);
        if (connectedDrawables != null && !connectedDrawables.isEmpty()) {
            List<Drawable> closestDrawables = new ArrayList<>();
            double minDistance = currentDrawable.getDistanceTo(connectedDrawables.get(0));
            closestDrawables.add(connectedDrawables.get(0));
            for (int i = 1; i < connectedDrawables.size(); i++) {
                Drawable drawable = connectedDrawables.get(i);
                double distance = currentDrawable.getDistanceTo(drawable);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestDrawables.clear();
                    closestDrawables.add(drawable);
                } else if (distance == minDistance) {
                    closestDrawables.add(drawable);
                }
            }
            // If there are multiple closest drawables, select one randomly
            if (closestDrawables.size() > 1) {
                System.out.println("Multiple closest drawables: " + closestDrawables);
                Random rand = new Random();
                Drawable nextDrawable = closestDrawables.get(rand.nextInt(closestDrawables.size()));
                System.out.println("Selected drawable: " + nextDrawable);
                return nextDrawable;
            } else {
                
                System.out.println("Single closest drawable: " + closestDrawables.get(0));
                return closestDrawables.get(0);
            }
        }
        return null;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public Drawable[] getDrawables() {
        return drawables.toArray(new Drawable[0]);
    }

}
