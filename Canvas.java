import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private Dot dot;
    private List<Lamp> lamps;
    private static final Color PATH_COLOR = Color.GRAY;
    private int pathThickness = 1;

    public Canvas(Dot dot) {
        this.dot = dot;
        lamps = new ArrayList<>();
        // Add lamps in each corner of the dot's path
        updatePathAndLamps();
    }

    public void draw(Graphics g) {
        // Clear the canvas or background, if needed
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(pathThickness));
        g2d.setColor(PATH_COLOR);

        // Draw the dot's path in grey
        g2d.drawRect(dot.getX1(), dot.getY1(), dot.getX2() - dot.getX1(), dot.getY3() - dot.getY1());

        // Draw other elements, if any
        dot.draw(g);

        // Draw the lamps
        for (Lamp lamp : lamps) {
            lamp.draw(g);
        }
    }

    // Update the path and lamps based on the dot's position
    private void updatePathAndLamps() {
        lamps.clear();
        // Add lamps in each corner of the dot's path
        lamps.add(new Lamp(dot.getX1(), dot.getY1())); // Top left corner
        lamps.add(new Lamp(dot.getX2(), dot.getY1())); // Top right corner
        lamps.add(new Lamp(dot.getX2(), dot.getY3())); // Bottom right corner
        lamps.add(new Lamp(dot.getX1(), dot.getY3())); // Bottom left corner
    }
}
