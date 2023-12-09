import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WidePath extends Path {
    private static final Color WIDE_PATH_COLOR = Color.BLACK; // New color for wide paths
    private static final Color WIDE_LAMP_COLOR = Color.ORANGE; // New color for wide paths
    private static final double WIDE_LAMP_INTERVAL = 60.0; // Double the regular interval
    private static final int WIDE_LAMP_RADIUS = 30; // Double the regular radius for lamps
    private final List<Lamp> widePathLamps = new ArrayList<>();
    
    public WidePath(Path2D.Float path2D) {
        super(path2D); // Call Path constructor that doesn't update lamps
    }

    @Override
    public void updateLamps(List<Drawable> connectedDrawables) {
        widePathLamps.clear(); // Clear existing lamps
        double interval = WIDE_LAMP_INTERVAL;
        double distance = 0.0;

        // Convert List<Drawable> to Set<Point2D>
        Set<Point2D> existingLamps = new HashSet<>();
        for (Drawable drawable : connectedDrawables) {
            if (drawable instanceof Lamp) {
                existingLamps.add(((Lamp) drawable).getPosition());
            }
        }

        while (distance < this.getLength()) {
            Point2D.Double lampPosition = this.getPosition(distance);
            if (!isNearExistingLamp(lampPosition, existingLamps)) {
                Lamp newLamp = new Lamp(lampPosition, WIDE_LAMP_RADIUS, WIDE_LAMP_COLOR); // Create lamp with WidePath attributes
                widePathLamps.add(newLamp);
            }
            distance += interval;
        }
    }


    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
    
        g2d.setColor(WIDE_PATH_COLOR);
        g2d.setStroke(new BasicStroke(PATH_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        g2d.draw(path2D); // Draw the wide path only, excluding lamps
    
        // Draw the lamps exclusive to WidePath
        for (Lamp lamp : this.widePathLamps) {
            lamp.draw(g);
        }
    }

    // This helper method is adjusted to use the wider lamp radius for checking proximity
    private boolean isNearExistingLamp(Point2D.Double newLampPosition, Set<Point2D> existingLamps) {
        final double tolerance = 15.0; // Define a tolerance distance
        for (Point2D existingPosition : existingLamps) {
            if (existingPosition.distance(newLampPosition) < tolerance) {
                return true; // Too close to an existing lamp
            }
        }
        return false; // Not near any existing lamp
    }

    public void activateLamps(Dot dot) {
        for (Lamp lamp : widePathLamps) {
            lamp.activate(dot);
        }
    }

}