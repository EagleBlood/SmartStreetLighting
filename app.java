import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app {
    private static int kropkaX = 50;
    private static int kropkaY = 100;
    private static int step = 1;
    private static int x1 = 50, y1 = 100, x2 = 200, y2 = 100, x3 = 150, y3 = 300, x4 = 100, y4 = 300;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Symulator Ruchu Kropek");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                rysujKropke(g);
            }

            private void rysujKropke(Graphics g) {
                g.setColor(Color.RED);
                g.fillOval(kropkaX, kropkaY, 10, 10);
            }
        };
        frame.add(panel);
        frame.setSize(300, 400);
        frame.setVisible(true);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kropkaX >= x1 && kropkaX < x2 && kropkaY == y1) {
                    kropkaX += step;
                } else if (kropkaX == x2 && kropkaY >= y1 && kropkaY < y3) {
                    kropkaY += step;
                } else if (kropkaX <= x3 && kropkaX > x4 && kropkaY == y3) {
                    kropkaX -= step;
                } else if (kropkaX == x4 && kropkaY <= y3 && kropkaY > y1) {
                    kropkaY -= step;
                }
                panel.repaint();
            }
        });
        timer.start();
    }
}
