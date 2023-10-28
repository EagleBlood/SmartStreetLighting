import java.awt.Graphics;

public class Canvas {
    private Dot dot;

    public Canvas(Dot dot) {
        this.dot = dot;
    }

    public void draw(Graphics g) {
        // Clear the canvas or background, if needed
        // Draw other elements, if any
        if (dot != null) {
            dot.draw(g);
        }
    }
}
