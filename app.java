import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app {

    private static Dot dot;
    private static Lamp lamp;
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
        frame.setSize(300, 400);
        frame.setVisible(true);

        dot = new Dot(50, 100);

        canvas = new Canvas(dot, lamp);

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
