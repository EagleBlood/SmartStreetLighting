import javax.swing. *;
import java.awt. *;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom. *;

public class app {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;

    private static Canvas canvas1;
    private static Canvas canvas2;
    private static Timer timer;
    // TODO: Fix buttonMode for sth more elegant
    private static int buttonMode = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Street Lighting Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a dynamic path, e.g., a rectangle
        Path2D.Double path2D1 = new Path2D.Double();
        Path path = new Path(new Rectangle2D.Double(50, 50, 100, 150));

        /*
        Line2D.Double line1 = new Line2D.Double(50, 50, 300, 50);
        Line2D.Double line2 = new Line2D.Double(300, 50, 300, 200);
        Line2D.Double line3 = new Line2D.Double(300, 200, 50, 50);
        path2D1.append(line1, true);
        path2D1.append(line2, true);
        path2D1.append(line3, true);
        Path path1 = new Path(path2D1);*/
        canvas1 = new Canvas(path);

        // Create dummy path
        Path2D.Double path2D2 = new Path2D.Double();
        Line2D.Double line4 = new Line2D.Double(0, 0, 0, 0);
        path2D2.append(line4, true);
        Path path2 = new Path(path2D2);
        canvas2 = new Canvas(path2);

        // Panel for the canvas
        JPanel canvasPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                switch (buttonMode) {
                    case 1:
                        canvas1.paintComponent(g);
                        break;

                    case 2:
                        canvas2.paintComponent(g);
                        break;

                    default:
                        break;
                }
            }
        };

        // JPanel for buttons
        JPanel buttonPanel = new JPanel();

        // Button panel
        JButton button1 = new JButton("Preset");
        JButton button2 = new JButton("User-defined");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Spinner panel
        JPanel spinnerPanel = new JPanel();
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(2, 2, 5, 1);
        JSpinner spinner = new JSpinner(spinnerNumberModel);
        spinnerPanel.add(spinner);

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH); // Add the button panel to the top
        frame.add(canvasPanel, BorderLayout.CENTER); // Add the canvas panel to the center
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        frame.setVisible(true);

        // Action listener for Button 1
        button1.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                buttonMode = 1;
                if (!timer.isRunning()) {
                    timer.start();
                }

                // Remove spinner panel from the bottom when button1 is clicked
                frame.remove(spinnerPanel);
                frame.repaint();
                canvasPanel.repaint(); // Repaint the canvas panel
            }
        });

        // Initialize a counter for the number of paths placed
        int[] pathsPlaced = {
            0
        };

        // Add a mouse listener to the canvas
        canvasPanel.addMouseListener(new MouseAdapter() {
            private int startX,
            startY,
            endX,
            endY;
            private boolean isStartSet = false;

            @Override public void mouseClicked(MouseEvent e) {
                // Get the spinner value
                int spinnerValue = (int)spinner.getValue();

                if (buttonMode == 2) {
                    if (!isStartSet) {
                        // Store the starting point of the path
                        startX = e.getX();
                        startY = e.getY();
                        isStartSet = true;
                    } else if (pathsPlaced[0] < spinnerValue) {
                        // Store the ending point of the path
                        endX = e.getX();
                        endY = e.getY();
                        isStartSet = false;

                        // Create a new path object using the starting and ending points
                        Path2D.Double path2D = new Path2D.Double();
                        Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);
                        path2D.append(line, true);
                        Path userPath = new Path(path2D);


                        //fix paths
                        // Add the path to the existing canvas2 object
                        if (buttonMode == 2 && pathsPlaced[0] < spinnerValue) {
                            if (canvas2 == null) {
                                canvas2 = new Canvas(path2);
                            }
                            canvas2.addPath(userPath);
                        }

                        // Update the timer to move the dot along the new path
                        if (timer != null) {
                            timer.stop();
                        }
                        timer = new Timer(10, new ActionListener() {
                            @Override public void actionPerformed(ActionEvent e) {
                                canvasPanel.repaint();
                            }
                        });
                        timer.start();

                        // Increment the counter for paths placed
                        pathsPlaced[0]++;
                    }
                }
            }
        });

        // Add action listener to button2
        button2.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                buttonMode = 2;
                if (!timer.isRunning()) {
                    timer.start();
                }

                // Reset the counter when button2 is clicked
                pathsPlaced[0] = 0;

                // Add spinner panel to the bottom when button2 is clicked
                frame.add(spinnerPanel, BorderLayout.SOUTH);
                frame.revalidate(); // For Java 1.7 or above.
                frame.repaint();

                canvasPanel.repaint(); // Repaint the canvas panel
            }
        });

        // Create and start the timer
        timer = new Timer(10, new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                canvasPanel.repaint(); // Repaint the canvas panel
            }
        });
    }
}
