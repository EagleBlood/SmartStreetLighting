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

public class App {
    public enum ButtonMode {
        NONE, MODE1, MODE2
    }
    public static ButtonMode buttonMode = ButtonMode.NONE;

    private static Canvas canvas1;
//    private static Canvas canvas2;


    public static void main(String[] args) {

        // Code to execute each time the timer fires
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute each time the timer fires
            }
        });

        // Create a Map to represent the connections between Drawable objects
        Map<Drawable, List<Drawable>> drawableConnections = new HashMap<>();

        /* Preset1 
        // Create the Drawable objects
        Path2D.Float path2D1 = new Path2D.Float();
        Line2D.Double line1 = new Line2D.Double(200, 200, 300, 300);
        Line2D.Double line2 = new Line2D.Double(300, 300, 400, 300);
        path2D1.append(line1, true);
        path2D1.append(line2, true);
        Drawable drawable1 = new Path(path2D1);

        Path2D.Float path2D2 = new Path2D.Float();
        Line2D.Double line3 = new Line2D.Double(400, 300, 400, 200);
        Line2D.Double line4 = new Line2D.Double(400, 200, 200, 200);
        path2D2.append(line3, true);
        path2D2.append(line4, true);
        Drawable drawable2 = new Path(path2D2);

        Path2D.Float path2D3 = new Path2D.Float();
        Line2D.Double line5 = new Line2D.Double(400, 300, 400, 400);
        Line2D.Double line6 = new Line2D.Double(400, 400, 200, 400);
        Line2D.Double line7 = new Line2D.Double(200, 400, 200, 200);
        path2D3.append(line5, true);
        path2D3.append(line6, true);
        path2D3.append(line7, true);
        Drawable drawable3 = new Path(path2D3);

        Path2D.Float path2D4 = new Path2D.Float();
        Line2D.Double line8 = new Line2D.Double(400, 300, 500, 300);
        Line2D.Double line9 = new Line2D.Double(500, 300, 200, 300);
        path2D4.append(line8, true);
        path2D4.append(line9, true);
        Drawable drawable4 = new Path(path2D4);

        // Add the Drawable objects to the drawables list
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(drawable1);
        drawables.add(drawable2);
        drawables.add(drawable3);
        drawables.add(drawable4);

        // For each Drawable object, add a List of Drawable objects that are connected to it to the Map
        drawableConnections.put(drawable1, new ArrayList<>(List.of(drawable2, drawable3, drawable4)));
        drawableConnections.put(drawable2, new ArrayList<>(List.of(drawable1)));
        drawableConnections.put(drawable3, new ArrayList<>(List.of(drawable1)));
        drawableConnections.put(drawable4, new ArrayList<>(List.of(drawable1)));
        */

        /* Preset2 */
        // Create the Drawable objects
        Path2D.Float path2D1 = new Path2D.Float();
        Line2D.Double line1 = new Line2D.Double(200, 200, 300, 200);
        Line2D.Double line2 = new Line2D.Double(300, 200, 300, 300);
        path2D1.append(line1, true);
        path2D1.append(line2, true);
        Drawable drawable1 = new Path(path2D1);

        Path2D.Float path2D2 = new Path2D.Float();
        Line2D.Double line3 = new Line2D.Double(300, 300, 500, 300);
        path2D2.append(line3, true);
        Drawable drawable2 = new Path(path2D2);

        Path2D.Float path2D3 = new Path2D.Float();
        Line2D.Double line4 = new Line2D.Double(500, 300, 500, 400);
        Line2D.Double line5 = new Line2D.Double(500, 400, 300, 300);
        path2D3.append(line4, true);
        path2D3.append(line5, true);
        Drawable drawable3 = new Path(path2D3);

        Path2D.Float path2D4 = new Path2D.Float();
        Line2D.Double line6 = new Line2D.Double(500, 300, 500, 200);
        Line2D.Double line7 = new Line2D.Double(500, 200, 300, 300);
        path2D4.append(line6, true);
        path2D4.append(line7, true);
        Drawable drawable4 = new Path(path2D4);

        // Add the Drawable objects to the drawables list
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(drawable1);
        drawables.add(drawable2);
        drawables.add(drawable3);
        drawables.add(drawable4);

        // For each Drawable object, add a List of Drawable objects that are connected to it to the Map
        drawableConnections.put(drawable1, new ArrayList<>(List.of(drawable2)));
        drawableConnections.put(drawable2, new ArrayList<>(List.of(drawable3, drawable4)));
        drawableConnections.put(drawable3, new ArrayList<>(List.of(drawable2)));
        drawableConnections.put(drawable4, new ArrayList<>(List.of(drawable2)));



        // Create the Dot objects
        Point2D.Double startPoint = drawable1.getEntryPoint();
        Dot dot1 = new Dot(startPoint, drawables, drawableConnections);


        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        SettingsPanel settingsPanel = new SettingsPanel();
        left.add(settingsPanel, gbc);

        JPanel buttonPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 50);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

//            @Override
//            public Color getBackground() {
//                return Color.YELLOW;
//            }

        };

        canvas1 = new Canvas(List.of(dot1));
        JPanel canvasPanel = new JPanel() {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                switch (buttonMode) {
                    case MODE1:
                        canvas1.paintComponent(g);
                        break;

//                    case MODE2:
//                        canvas2.paintComponent(g);
//                        break;

                    default:
                        break;
                }
            }
        };

        JPanel center = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        center.add(canvasPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        center.add(buttonPanel, gbc);

        //#####################################################

        JButton button1 = new JButton("Preset");
        JButton button2 = new JButton("User-defined");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        //#####################################################

        JFrame frame = new JFrame("Street Lighting Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(settingsPanel, BorderLayout.WEST);
        frame.add(center);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ButtonAction buttonAction1 = new ButtonAction(timer, canvasPanel, canvas1);
//        ButtonAction buttonAction2 = new ButtonAction(timer, canvasPanel, canvas2);

        button1.addActionListener(buttonAction1.button1Action());
        //button2.addActionListener(buttonAction2.button2Action());
    }
}
