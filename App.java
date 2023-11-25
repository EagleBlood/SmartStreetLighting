import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.geom.Point2D;

public class App {
    public enum ButtonMode {
        NONE, MODE1, MODE2
    }
    public static ButtonMode buttonMode = ButtonMode.NONE;

    private static Canvas canvas1;
//    private static Canvas canvas2;
    private static JPanel canvasPanel;

    private static Timer globalTimer;


    public static void main(String[] args) {

        List<Drawable> drawables = Config.PRESET3;

        Point2D.Double startPoint = drawables.get(0).getEntryPoint();
        Dot dot1 = new Dot(startPoint, drawables);


        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;


        canvas1 = new Canvas(List.of(dot1));
        canvasPanel = new JPanel() {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 700);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                canvas1.paintComponent(g);
                /*switch (buttonMode) {
                    case MODE1:
                        canvas1.paintComponent(g);
                        break;

//                    case MODE2:
//                        canvas2.paintComponent(g);
//                        break;

                    default:
                        break;
                }*/
            }
        };

        SettingsPanel settingsPanel = new SettingsPanel();
        left.add(settingsPanel, gbc);

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

        //#####################################################

        JFrame frame = new JFrame("Street Lighting Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(settingsPanel, BorderLayout.WEST);
        frame.add(center);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //ButtonAction buttonAction1 = new ButtonAction(timer, canvasPanel);
        //ButtonAction buttonAction2 = new ButtonAction(timer, canvasPanel, canvas2);

        //button1.addActionListener(buttonAction1.button1Action());
        //button2.addActionListener(buttonAction2.button2Action());
    }

    public static JPanel getCanvas() {
        return canvasPanel;
    }
}


