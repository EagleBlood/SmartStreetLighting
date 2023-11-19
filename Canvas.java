import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Canvas extends JPanel {
    private final List<Dot> dots;

    public Canvas(List<Dot> dots) {
        this.dots = dots;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw each Dot
        for (Dot dot : dots) {
            // Draw each Drawable (paths, lamps) in the Dot object
            for (Drawable drawable : dot.getDrawables()) {
                drawable.draw(g2d);
            }
            // Draw the dot
            dot.draw(g2d);

        }
        g2d.dispose();
        g.dispose();
    }

}
