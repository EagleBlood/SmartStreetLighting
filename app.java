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

    private static Canvas canvas1 = null;
    private static Canvas canvas2 = null;
    private static Dot dot = new Dot(new Point2D.Double(0, 0), null);
    private static Timer timer;
    // TODO: Fix buttonMode for sth more elegant
    private static int buttonMode = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Street Lighting Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Canvas 1
        //Path2D.Double path2D1 = new Path2D.Double();
        Path path = new Path(new Rectangle2D.Double(50, 50, 100, 150));

        /*
        Line2D.Double line1 = new Line2D.Double(50, 50, 300, 50);
        Line2D.Double line2 = new Line2D.Double(300, 50, 300, 200);
        Line2D.Double line3 = new Line2D.Double(300, 200, 50, 50);
        path2D1.append(line1, true);
        path2D1.append(line2, true);
        path2D1.append(line3, true);
        Path path1 = new Path(path2D1);*/
        canvas1 = new Canvas();
        canvas1.addPath(path);



        // Panel for the canvas
        JPanel canvasPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                switch (buttonMode) {
                    case 1:
                        canvas1.paintComponent(g);
                        break;

                    case 2:
                        if (canvas2 != null) {
                            canvas2.paintComponent(g);
                        }
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
        /*SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(2, 2, 6, 1);
        JSpinner spinner = new JSpinner(spinnerNumberModel);
        spinnerPanel.add(spinner);*/

        // Add a radio button to the GUI
        JRadioButton radioButton = new JRadioButton("Draw separate paths");
        spinnerPanel.add(radioButton);

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
        //int[] pathsPlaced = { 0 };

        // Add a mouse listener to the canvas
        canvasPanel.addMouseListener(new MouseAdapter() {
            private int startX, startY, endX, endY;
            private boolean isDrawingPath = false;
        
            @Override public void mouseClicked(MouseEvent e) {
                // Initialize canvas2 if it doesn't exist
                if (canvas2 == null) {
                    canvas2 = new Canvas();
                }
        
                if (buttonMode == 2) {
                    if (radioButton.isSelected()) {
                        // Separate paths mode
                        if (!isDrawingPath) {
                            // Start drawing a new path
                            startX = e.getX();
                            startY = e.getY();
                            isDrawingPath = true;
                        } else {
                            // Finish drawing the current path
                            endX = e.getX();
                            endY = e.getY();
                            isDrawingPath = false;
        
                            // Create a new path object using the starting and ending points
                            Path2D.Double path2D = new Path2D.Double();
                            Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);
                            path2D.append(line, true);
                            Path userPath = new Path(path2D);
        
                            // Add the path to the existing canvas2 object
                            canvas2.addPath(userPath);
                        }
                    } else {
                        // Continuous paths mode
                        if (!isDrawingPath) {
                            // Start drawing a new path
                            startX = e.getX();
                            startY = e.getY();
                            isDrawingPath = true;
                        } else {
                            // Finish drawing the current path
                            endX = e.getX();
                            endY = e.getY();
        
                            // Get the start point of the new path
                            Point2D startPoint;
                            if (!canvas2.getPaths().isEmpty()) {
                                Path lastPath = canvas2.getPaths().get(canvas2.getPaths().size() - 1);
                                startPoint = lastPath.getEndPoint();
                            } else {
                                startPoint = new Point2D.Double(startX, startY);
                            }
        
                            // Create a new path object using the starting and ending points
                            Path2D.Double path2D = new Path2D.Double();
                            Line2D.Double line = new Line2D.Double(startPoint.getX(), startPoint.getY(), endX, endY);
                            path2D.append(line, true);
                            Path userPath = new Path(path2D);
        
                            // Add the path to the existing canvas2 object
                            canvas2.addConnectedPath(userPath, true);
        
                            // Reset the start point for the next path
                            startX = endX;
                            startY = endY;
                        }
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
                //pathsPlaced[0] = 0;

                // Add spinner panel to the bottom when button2 is clicked
                frame.add(spinnerPanel, BorderLayout.SOUTH);
                frame.revalidate(); // For Java 1.7 or above.
                frame.repaint();

                canvasPanel.repaint(); // Repaint the canvas panel
            }
        });

        // Create and start the timer
        timer = new Timer(10, new ActionListener() {
            private int currentPathIndex = 0;
            private double currentPathProgress = 0.0;
        
            @Override public void actionPerformed(ActionEvent e) {
                if (canvas2 != null && !canvas2.getPaths().isEmpty()) {
                    // Get the current path
                    Path currentPath = canvas2.getPaths().get(currentPathIndex);
        
                    // Move the dot along the current path
                    currentPathProgress += 0.01; // Adjust this value to change the speed of the dot
                    if (currentPathProgress > 1.0) {
                        // The dot has reached the end of the current path
                        currentPathProgress = 0.0;
                        currentPathIndex++;
                        if (currentPathIndex >= canvas2.getPaths().size()) {
                            // The dot has reached the end of the last path
                            currentPathIndex = 0; // Reset to the first path
                        }
                    }
        
                    // Calculate the new position of the dot
                    Point2D startPoint = currentPath.getStartPoint();
                    Point2D endPoint = currentPath.getEndPoint();
                    double newX = startPoint.getX() + (endPoint.getX() - startPoint.getX()) * currentPathProgress;
                    double newY = startPoint.getY() + (endPoint.getY() - startPoint.getY()) * currentPathProgress;
        
                    // Update the position of the existing dot
                    dot.setPosition(newX, newY);
                }
        
                canvasPanel.repaint();
            }
        });
    }
}
