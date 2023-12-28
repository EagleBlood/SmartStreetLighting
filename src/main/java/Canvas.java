import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private final List<Dot> dots;
    private List<Drawable> drawableList; // New field

    public Canvas() {
        this.dots = new ArrayList<>();
    }

    public void setDrawables(List<Drawable> drawableList) { // New method
        this.drawableList = drawableList;
        repaint();
        System.out.println("przebudowa canvas");
    }

    public void setDots(List<Dot> dots) { // New method
        this.dots.clear();
        this.dots.addAll(dots);
        repaint();
        System.out.println("Ustawiono nową listę kropek w canvas");
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

        // Draw each Drawable in the drawableList
        if (drawableList != null) {
            for (Drawable drawable : drawableList) {
                drawable.draw(g2d);
            }
        }

        g2d.dispose();
    }
}
