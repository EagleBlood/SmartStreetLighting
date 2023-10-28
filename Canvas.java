import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private Dot dot;
    private List<Lamp> lamps;
    private Color pathColor = Color.GRAY;
    private int pathThickness = 30; // Adjust the thickness as needed

    public Canvas(Dot dot) {
        this.dot = dot;
        lamps = new ArrayList<>();

        // Add lamps in each corner of the path
        lamps.add(new Lamp(50, 90)); // Top left corner
        lamps.add(new Lamp(250, 90)); // Top right corner
        lamps.add(new Lamp(250, 310)); // Bottom right corner
        lamps.add(new Lamp(50, 310)); // Bottom left corner
    }

    public void draw(Graphics g) {
        // Clear the canvas or background, if needed
        // Draw the path with a thicker line
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(pathColor);
        g2d.setStroke(new BasicStroke(pathThickness));
        g2d.drawRect(50, 100, 200, 200); // Adjust the coordinates and size as needed

        // Draw other elements, if any
        if (dot != null) {
            dot.draw(g);
        }

        for (Lamp lamp : lamps) {
            lamp.draw(g);
        }
    }
}
