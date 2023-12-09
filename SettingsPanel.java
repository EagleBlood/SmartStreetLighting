import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SettingsPanel extends JPanel {

    private final JLabel clockLabel;
    private final JTextField hourTextField;
    private final JTextField minuteTextField;
    private final String[] presetNames = {"PRESET1", "PRESET2", "PRESET3", "PRESET4"};
    private final JComboBox<String> seasonComboBox;
    private final JComboBox<String> weatherComboBox;
    private final JComboBox<String> presetComboBox;
    public static SettingsPanel instance;

    public static String getCurrentTime() {
        return instance.clockLabel.getText();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(350, 700);
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

        instance = this;

        setLayout(new GridBagLayout());

        // Timer action listeners
        Timer timerDot = new Timer(100, e -> {
            App.getJPanel().repaint();
        });
        Timer timerCloak = new Timer(3000, e -> {
            incrementClock();
        });

        JLabel currentTime = new JLabel("Current time");
        add(currentTime, new Gbc(0,0,2).build());


        clockLabel = new JLabel("13:00");
        clockLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(clockLabel, new Gbc(2,0,2).build());

        add(Gbc.createVerticalStrut(20), new Gbc(0, 1, 4).build());

        // Label - "Hour settings"
        JLabel timeLabel = new JLabel("Time settings");
        add(timeLabel, new Gbc(0,2,4).build());

        JLabel customTimeLabel = new JLabel("Time");
        add(customTimeLabel, new Gbc(0,3,1).build());

        JPanel timePanel = new JPanel(new GridLayout(1, 4, 0, 0));

        hourTextField = createFormattedTextField(23);
        JLabel hourTextFieldLabel = new JLabel(" h");
        timePanel.add(hourTextField);
        timePanel.add(hourTextFieldLabel);

        minuteTextField = createFormattedTextField(59);
        JLabel minuteTextFieldLabel = new JLabel(" m");
        timePanel.add(minuteTextField);
        timePanel.add(minuteTextFieldLabel);

        timePanel.setOpaque(false);

        add(timePanel, new Gbc(1, 3, 2).build());

        JButton setTime = new JButton("SET");
        add(setTime, new Gbc(3,3,1).build());

        // 4 przyciski w jednej linii
        JButton button1 = new JButton("00:00");
        JButton button2 = new JButton("06:00");
        JButton button3 = new JButton("12:00");
        JButton button4 = new JButton("18:00");

        add(button1, new Gbc(0,4,1).build());
        add(button2, new Gbc(1,4,1).build());
        add(button3, new Gbc(2,4,1).build());
        add(button4, new Gbc(3,4,1).build());

        // Pole tekstowe (2 znaki) + h

        add(Gbc.createVerticalStrut(5), new Gbc(0, 5, 4).build());

        // Label - "Season"
        JLabel seasonLabel = new JLabel("Season");
        add(seasonLabel, new Gbc(0,6,4).build());

        // 3 przyciski "Spring", "Summer", "Autumn", "Winter"
        seasonComboBox = new JComboBox<>(new String[]{"Spring", "Summer", "Autumn", "Winter"});
        seasonComboBox.setSelectedIndex(-1);
        seasonComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "No selection" : getText());
                return comp;
            }
        });
        add(seasonComboBox, new Gbc(0, 7, 4).build());

        add(Gbc.createVerticalStrut(5), new Gbc(0, 9, 4).build());

        // Label - "Weather"
        JLabel weatherLabel = new JLabel("Weather");
        add(weatherLabel, new Gbc(0,10,4).build());


        weatherComboBox = new JComboBox<>(new String[]{"Sun", "Rain"});
        weatherComboBox.setSelectedIndex(-1);
        weatherComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "No selection" : getText());
                return comp;
            }
        });
        add(weatherComboBox, new Gbc(0, 11, 4).build());

        add(Gbc.createVerticalStrut(5), new Gbc(0, 13, 4).build());

        // Label - "Presets"
        JLabel presetLabel = new JLabel("Presets");
        add(presetLabel, new Gbc(0,14,4).build());

        presetComboBox = new JComboBox<>(presetNames);
        presetComboBox.setSelectedIndex(-1);
        presetComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "No selection" : getText());
                return comp;
            }
        });

        add(presetComboBox, new Gbc(0, 15, 4).build());
        add(Gbc.createVerticalStrut(40), new Gbc(0, 17, 4).build());



        // Start and stop buttons
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 15));

        JButton stopButton = new JButton("STOP");
        stopButton.setFont(new Font("SansSerif", Font.BOLD, 15));

        add(startButton, new Gbc(0,18,2).build());
        add(stopButton, new Gbc(2,18,2).build());

        ButtonAction buttonAction1 = new ButtonAction(timerDot, timerCloak);

        startButton.addActionListener(buttonAction1.startCanvas());
        stopButton.addActionListener(buttonAction1.stopCanvas());



        // buttons listeners


        button1.addActionListener(e -> presetButtonClicked("00:00"));

        button2.addActionListener(e -> presetButtonClicked("06:00"));

        button3.addActionListener(e -> presetButtonClicked("12:00"));

        button4.addActionListener(e -> presetButtonClicked("18:00"));

        setTime.addActionListener(e -> {
            setClock();
        });

        startButton.addActionListener(e -> {
            startButtonClicked();
        });

        presetComboBox.addActionListener(e -> {
            String selectedPreset = (String) presetComboBox.getSelectedItem();
            updateDrawables(selectedPreset);
        });

    }


    private void presetButtonClicked(String presetTime) {
        clockLabel.setText(presetTime);
    }

    private void setClock(){
        String hourText = hourTextField.getText();
        String minuteText = minuteTextField.getText();

        int hour = Integer.parseInt(hourText);
        int minute = Integer.parseInt(minuteText);

        String formattedTime = String.format("%02d:%02d", hour, minute);
        presetButtonClicked(formattedTime);

        hourTextField.setText("");
        minuteTextField.setText("");
    }

    private void updateDrawables(String selectedPreset) {
        App.buttonMode = App.ButtonMode.NONE; // Reset button mode
        App.updateDrawables(selectedPreset);
        App.getJPanel().repaint();
    }

    public JComboBox<String> getComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(presetNames);

        return comboBox;
    }

    private void startButtonClicked(){
        String selectedSeason = getSelectedComboBoxOption(seasonComboBox);
        String selectedWeather = getSelectedComboBoxOption(weatherComboBox);
        String currentTime = clockLabel.getText();

        JOptionPane.showMessageDialog(this, "Current Time: " + currentTime + "\nSelected season: " + selectedSeason + "\nSelected Weather: " + selectedWeather);
    }

    private JTextField createFormattedTextField(int maxValue) {
        JTextField textField = new JTextField(2);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                currentText += text;
                if (currentText.length() <= 2 && currentText.matches("\\d*")) {
                    int value = Integer.parseInt(currentText);
                    if (value >= 0 && value <= maxValue) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }
        });
        return textField;
    }

    private String getSelectedComboBoxOption(JComboBox<String> comboBox) {
        return (String) comboBox.getSelectedItem();
    }

    public void incrementClock() {
        String currentTime = clockLabel.getText();
        String[] parts = currentTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        // Increment time by 15 minutes
        minute += 15;
        if (minute >= 60) {
            minute -= 60;
            hour = (hour + 1) % 24; // Ensure hour stays within 24-hour format
        }

        // Update clockLabel
        String newTime = String.format("%02d:%02d", hour, minute);
        clockLabel.setText(newTime);
    }

}

class Gbc {
    private final GridBagConstraints gbc = new GridBagConstraints();

    public Gbc(int x, int y, int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets = new Insets(3, 2, 3, 2);
    }

    public static Component createVerticalStrut(int height) {
        return Box.createVerticalStrut(height);
    }

    public GridBagConstraints build() {
        return gbc;
    }
}
