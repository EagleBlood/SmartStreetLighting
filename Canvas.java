import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private Dot dot;
    private List<Lamp> lamps;

    public Canvas(Dot dot) {
        this.dot = dot;
        lamps = new ArrayList<>();

        // Add lamps in each corner of the path
        lamps.add(new Lamp(50, 90)); // Top left corner
        lamps.add(new Lamp(250, 90)); // Top right corner
        lamps.add(new Lamp(250, 300)); // Bottom right corner
        lamps.add(new Lamp(50, 300)); // Bottom left corner
    }

    public void draw(Graphics g) {
        // Clear the canvas or background, if needed
        // Draw other elements, if any
        if (dot != null) {
            dot.draw(g);
        }

        for (Lamp lamp : lamps) {
            lamp.draw(g);
        }
    }
}
