import java.awt.Graphics;

public class Canvas {
    private Dot dot;
    private Lamp lamp;

    public Canvas(Dot dot, Lamp lamp) {
        this.dot = dot;
        this.lamp = new Lamp(50, 90);
    }

    public void draw(Graphics g) {
        // Clear the canvas or background, if needed
        // Draw other elements, if any
        if (dot != null) {
            dot.draw(g);
        }

        lamp.draw(g);
    }
}
