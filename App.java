import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class App {
    public enum ButtonMode {
        NONE, MODE1, MODE2
    }
    public static ButtonMode buttonMode = ButtonMode.NONE;

    private static Canvas canvas1 = new Canvas();
    private static Canvas canvas2 = new Canvas();
    private static Timer timer;

    public static void main(String[] args) {

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute each time the timer fires
        }
        });

        List<Path> paths1 = new ArrayList<>();

        //Create preview paths
        Path2D.Float path2D = new Path2D.Float();
        Line2D.Double line1 = new Line2D.Double(100, 100, 200, 200);
        Line2D.Double line2 = new Line2D.Double(200, 200, 300, 100);
        Line2D.Double line3 = new Line2D.Double(300, 100, 400, 200);
        Line2D.Double line4 = new Line2D.Double(400, 200, 500, 100);
        Line2D.Double line5 = new Line2D.Double(500, 100, 600, 200);
        Line2D.Double line6 = new Line2D.Double(600, 200, 700, 100);
        Line2D.Double line7 = new Line2D.Double(700, 100, 100, 100);
        Line2D.Double line8 = new Line2D.Double(100, 100, 600, 200);

        path2D.append(line1, true);
        path2D.append(line2, true);
        path2D.append(line3, true);
        path2D.append(line4, true);
        path2D.append(line5, true);
        path2D.append(line6, true);
        path2D.append(line7, true);
        path2D.append(line8, true);

        paths1.add(new Path(path2D));

        canvas1.drawConnectedPathList(paths1, true);
        canvas1.setDotPathList(paths1);

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

                    case MODE2:
                        canvas2.paintComponent(g);
                        break;

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
        ButtonAction buttonAction2 = new ButtonAction(timer, canvasPanel, canvas2);

        button1.addActionListener(buttonAction1.button1Action());
        button2.addActionListener(buttonAction2.button2Action());

    }
}

