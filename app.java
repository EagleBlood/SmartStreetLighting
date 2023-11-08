import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class app {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;

    private static Canvas canvas;
    private static Timer timer;
    private static boolean isButton1Pressed = false; // Flag to track if Button 1 is pressed

    public static void main(String[] args) {
        JFrame frame = new JFrame("Street Lighting Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel for buttons
        JPanel buttonPanel = new JPanel();

        // Button panel
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Panel for the canvas
        JPanel canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isButton1Pressed) {
                    canvas.draw(g);
                }
            }
        };

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH); // Add the button panel to the top
        frame.add(canvasPanel, BorderLayout.CENTER); // Add the canvas panel to the center
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        frame.setVisible(true);

        // Create a dynamic path, e.g., a rectangle
        //Path path = new Path(new Rectangle2D.Double(50, 50, 100, 150));
        Path2D.Double path2D = new Path2D.Double();

        Line2D.Double line1 = new Line2D.Double(50, 50, 300, 50);
        Line2D.Double line2 = new Line2D.Double(300, 50, 300, 200);
        Line2D.Double line3 = new Line2D.Double(300, 200, 50, 50);

        path2D.append(line1, true);
        path2D.append(line2, true);
        path2D.append(line3, true);

        Path path = new Path(path2D);

        // Create a canvas object
        canvas = new Canvas(path);

        // Default selection for Button 1
        button1.setSelected(true);

        // Action listener for Button 1
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isButton1Pressed) {
                    isButton1Pressed = true; // Set the flag to true
                    // Create and start the timer when Button 1 is clicked for the first time
                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            canvasPanel.repaint(); // Repaint the canvas panel
                        }
                    });
                    timer.start();
                } else {
                    // Restart the timer when Button 1 is clicked again
                    if (!timer.isRunning()) {
                        timer.start();
                    }
                }
            }
        });

        // Action listener for Button 2
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isButton1Pressed = false; // Set the flag to false
                if (timer != null) {
                    timer.stop(); // Stop the timer if it's running
                }
                canvasPanel.repaint(); // Clear the canvas when Button 2 is pressed
            }
        });
    }
}