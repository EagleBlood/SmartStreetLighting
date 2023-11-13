import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class ButtonAction {
    private Timer timer;
    private JPanel canvasPanel;
    private Canvas canvas = new Canvas();
    List<Path> userPathList = new ArrayList<>();


    public ButtonAction(Timer timer, JPanel canvasPanel, Canvas canvas) {
        this.timer = timer;
        this.canvasPanel = canvasPanel;
        this.canvas = canvas;
    }

    public ActionListener button1Action() {
        return new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                App.buttonMode = App.ButtonMode.MODE1;
                if (!timer.isRunning()) {
                    timer = new Timer(10, new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                            // Move the dot along the path
                            // ...

                            // Repaint the canvas panel
                            canvasPanel.repaint();
                        }
                    });
                    timer.start();
                }

                canvasPanel.repaint(); // Repaint the canvas panel
            }
        };
    }

    public ActionListener button2Action() {
        return new ActionListener() {
            private int startX, startY, endX, endY;
            private boolean isDrawingPath = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                App.buttonMode = App.ButtonMode.MODE2;
                if (!timer.isRunning()) {
                    timer.start();
                }

                // Add a mouse listener to the canvas
                canvasPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Continuous paths mode
                        if (!isDrawingPath) {
                            // Start drawing a new path
                            startX = e.getX();
                            startY = e.getY();
                            isDrawingPath = true;
                        }
                        else
                        {
                            // Finish drawing the current path
                            endX = e.getX();
                            endY = e.getY();

                            // Get the start point of the new path
                            Point2D startPoint;
                            if (!canvas.getPathList().isEmpty()) {
                                Path lastPath = canvas.getPathList().get(canvas.getPathList().size() - 1);
                                startPoint = lastPath.getEndPoint();
                            } else {
                                startPoint = new Point2D.Float(startX, startY);
                            }

                            // Create a new path object using the starting and ending points
                            Path2D.Float path2D = new Path2D.Float();
                            Line2D.Double line = new Line2D.Double(startPoint.getX(), startPoint.getY(), endX, endY);
                            path2D.append(line, true);
                            Path userPath = new Path(path2D);

                            userPathList.add(userPath); // Add the userPath to the list

                            canvas.drawConnectedPathList(userPathList, true); // Add the path to the canvas
                            canvas.setDotPathList(userPathList); // Set the path for the dot

                            // Reset the start point for the next path
                            startX = endX;
                            startY = endY;
                        }

                        // Update the timer to move the dot along the new path
                        if (timer != null) {
                            timer.stop();
                        }

                        timer = new Timer(10, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                canvasPanel.repaint();
                            }
                        });
                        timer.start();
                    }
                });

                canvasPanel.repaint(); // Repaint the canvas panel
            }

        };
    }
}
