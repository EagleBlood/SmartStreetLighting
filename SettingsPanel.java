import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(275, 300);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Color getBackground() {
        return new Color(0xD4D9DB);
    }
    public SettingsPanel() {
        setLayout(new GridBagLayout());

        // Tekst - zegar
        JLabel clockLabel = new JLabel("13:00");
        add(clockLabel, new Gbc(0,0,4).build());

        // Label - "Hour settings"
        JLabel hourLabel = new JLabel("Hour settings");
        add(hourLabel, new Gbc(0,2,4).build());

        // 4 przyciski w jednej linii
        JButton button1 = new JButton("6:00");
        JButton button2 = new JButton("12:00");
        JButton button3 = new JButton("18:00");
        JButton button4 = new JButton("24:00");

        add(button1, new Gbc(0,4,1).build());
        add(button2, new Gbc(1,4,1).build());
        add(button3, new Gbc(2,4,1).build());
        add(button4, new Gbc(3,4,1).build());

        // Pole tekstowe (2 znaki) + h
        JTextField hourTextField = new JTextField(2);
        JLabel hourTextFieldLabel = new JLabel("h");
        add(hourTextField, new Gbc(0,6,1).build());
        add(hourTextFieldLabel, new Gbc(1,6,1).build());

        // Pole tekstowe (2 znaki) + m
        JTextField minuteTextField = new JTextField(2);
        JLabel minuteTextFieldLabel = new JLabel("m");
        add(minuteTextField, new Gbc(2,6,1).build());
        add(minuteTextFieldLabel, new Gbc(3,6,1).build());

        // Label - "Season"
        JLabel seasonLabel = new JLabel("Season");
        add(seasonLabel, new Gbc(0,10,4).build());

        // 3 przyciski "Spring", "Summer", "Autumn", "Winter"
        JButton springButton = new JButton("Spring");
        JButton summerButton = new JButton("Summer");
        JButton autumnButton = new JButton("Autumn");
        JButton winterButton = new JButton("Winter");

        add(springButton, new Gbc(0,12,2).build());
        add(summerButton, new Gbc(2,12,2).build());
        add(autumnButton, new Gbc(0,14,2).build());
        add(winterButton, new Gbc(2,14,2).build());

        // Label - "Weather"
        JLabel weatherLabel = new JLabel("Weather");
        add(weatherLabel, new Gbc(0,16,4).build());

        // 2 przyciski "Sun", "Rain"
        JButton sunButton = new JButton("Sun");
        JButton rainButton = new JButton("Rain");

        add(sunButton, new Gbc(0,18,2).build());
        add(rainButton, new Gbc(2,18,2).build());

    }
}

class Gbc {
    private final GridBagConstraints gbc;

    public Gbc(int x, int y, int width) {
        gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.NONE;
    }

    public GridBagConstraints build() {
        return gbc;
    }
}
