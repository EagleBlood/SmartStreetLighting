import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Canvas extends JPanel {
    private final List<Dot> dots;  // Change to a list of Dots

    public Canvas(List<Dot> dots) {
        this.dots = dots;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw each Dot
        for (Dot dot : dots) {
            // Draw each Drawable in the Dot object
            for (Drawable drawable : dot.getDrawables()) {
                drawable.draw(g2d);
            }
            // Draw the dot
            dot.draw(g2d);

        }
    }
}
