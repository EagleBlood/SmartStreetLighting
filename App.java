import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class App {
    public enum ButtonMode {
        NONE, MODE1, MODE2
    }
    public static ButtonMode buttonMode = ButtonMode.NONE;

    private static Canvas canvas;
    private static JPanel canvasPanel;
    public static void main(String[] args) {
        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        canvas = new Canvas();  // Create Canvas without preset initially
        canvasPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 700);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                canvas.paintComponent(g);
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
        frame.add(left, BorderLayout.WEST);  // Fix: Add left panel, not settingsPanel
        frame.add(center);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Set up the ActionListener for the JComboBox in SettingsPanel
        settingsPanel.getComboBox().addActionListener(e -> {
            JComboBox<?> source = (JComboBox<?>) e.getSource();
            String selectedPreset = (String) source.getSelectedItem();
            updateDrawables(selectedPreset);
        });
    }

    public static void updateDrawables(String selectedPreset) {
        List<Drawable> drawables = Config.getDrawablesForPreset(selectedPreset);

        Point2D.Double startPoint = drawables.get(0).getEntryPoint();
        Dot dot = new Dot(startPoint, drawables);

        // Log to check if dot is created and drawables list is not empty
        System.out.println("Dot: " + dot);
        System.out.println("Drawables: " + drawables);

        canvas.setDots(List.of(dot));
        canvas.setDrawables(drawables);
        canvasPanel.repaint();
    }

    public static JPanel getJPanel() {
        return canvasPanel;
    }

    public static Canvas getCanvas() {
        return canvas;
    }
}
