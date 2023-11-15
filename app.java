import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;

    private static Dot dot;
    private static Canvas canvas;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Symulator oświetlenia ulicznego");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                canvas.draw(g);
            }
        };
        frame.add(panel);
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        frame.setVisible(true);

        dot = new Dot(50, 100);

        canvas = new Canvas(dot);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dot.moveDot();
                panel.repaint();
            }
        });
        timer.start();
    }
}
