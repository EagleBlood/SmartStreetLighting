import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class app {
    public enum ButtonMode {
        NONE, MODE1, MODE2
    }
    public static ButtonMode buttonMode = ButtonMode.NONE;

    private static final int CANVAS_WIDTH = 700;
    private static final int CANVAS_HEIGHT = 700;

    private static Canvas canvas1;
    private static Canvas canvas2;
    private static Timer timer;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Street Lighting Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute each time the timer fires
        }
        });



        // Create a Map to represent the connections between Drawable objects
        Map<Drawable, List<Drawable>> drawableConnections = new HashMap<>();

        // Create the Drawable objects
        Path2D.Float path2D1 = new Path2D.Float();
        Line2D.Double line1 = new Line2D.Double(200, 200, 300, 300);
        Line2D.Double line2 = new Line2D.Double(300, 300, 400, 300);
        path2D1.append(line1, true);
        path2D1.append(line2, true);
        Drawable drawable1 = new Path(path2D1);

        Path2D.Float path2D2 = new Path2D.Float();
        Line2D.Double line3 = new Line2D.Double(400, 300, 400, 200);
        Line2D.Double line4 = new Line2D.Double(400, 200, 400, 100);
        path2D2.append(line3, true);
        path2D2.append(line4, true);
        Drawable drawable2 = new Path(path2D2);

        Path2D.Float path2D3 = new Path2D.Float();
        Line2D.Double line5 = new Line2D.Double(400, 300, 400, 400);
        Line2D.Double line6 = new Line2D.Double(400, 400, 400, 500);
        path2D3.append(line5, true);
        path2D3.append(line6, true);
        Drawable drawable3 = new Path(path2D3);

        // Add the Drawable objects to the drawables list
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(drawable1);
        drawables.add(drawable2);
        drawables.add(drawable3);

        List<Drawable> allDrawables = drawables;

        // Create the Dot objects
        Point2D.Double startPoint = drawable1.getEntryPoint();
        Dot dot1 = new Dot(startPoint, drawables, allDrawables, drawableConnections);

        // For each Drawable object, add a List of Drawable objects that are connected to it to the Map
        drawableConnections.put(drawable1, Arrays.asList(drawable2, drawable3));
        drawableConnections.put(drawable2, Arrays.asList(drawable1, drawable3));
        drawableConnections.put(drawable3, Arrays.asList(drawable1, drawable2));
        
        // Initialize the Canvas objects with the drawables and drawableConnections
        canvas1 = new Canvas(Arrays.asList(dot1));
                        

        JPanel canvasPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                switch (buttonMode) {
                    case MODE1:
                        canvas1.paintComponent(g);
                        break;

                    case MODE2:
                        canvas2.paintComponent(g);
                        break;

                    default:
                        break;
                }
            }
        };

        JPanel buttonPanel = new JPanel();
        JButton button1 = new JButton("Preset");
        JButton button2 = new JButton("User-defined");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(canvasPanel, BorderLayout.CENTER);
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        frame.setVisible(true);


        ButtonAction buttonAction1 = new ButtonAction(timer, canvasPanel, canvas1);
        ButtonAction buttonAction2 = new ButtonAction(timer, canvasPanel, canvas2);

        button1.addActionListener(buttonAction1.button1Action());
        //button2.addActionListener(buttonAction2.button2Action());
    }
}