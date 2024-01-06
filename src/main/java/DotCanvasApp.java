import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DotCanvasApp extends JFrame {

    private static JPanel canvasPanel;
    private final ArrayList<Point> dots = new ArrayList<>();
    private final ArrayList<RoadPoint> roadPoints = new ArrayList<>();
    private final ArrayList<Point> newDots = new ArrayList<>();

    private final String[] categoriesRoad = {"A", "B"};
    private BufferedImage backgroundImage;
    private final JTextField streetNameField;
    private final JComboBox category;
    private final JButton roadWithIntersectionsButton;
    private final JButton windingRoadButton;
    private final JButton addRoad;
    private final JButton clearAddedPoints;
    private final JTextField lampCountField;
    private final JButton saveToJsonButton;
    private boolean addingPointsAllowed = false;
    private boolean intersectionMode = false;
    private boolean windingMode = false;
    private final JTextField lampDistanceField;
    private final int dotRadius = 20;


    public DotCanvasApp() {

        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        canvasPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1000, 900);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, this);
                }

                g.setColor(Color.BLACK);
                for (Point dot : dots) {
                    g.fillOval(dot.x - 5, dot.y - 5, 10, 10);
                }

                if (addingPointsAllowed)
                {
                    g.setColor(Color.BLUE);
                    for (Point dot : newDots) {
                        g.fillOval(dot.x - 5, dot.y - 5, 10, 10);
                    }
                }


            }
        };

        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (addingPointsAllowed) {
                    boolean isNearExistingDot = false;
                    for (Point dot : dots) {
                        double distance = Math.sqrt(Math.pow(dot.x - e.getX(), 2) + Math.pow(dot.y - e.getY(), 2));
                        if (distance <= dotRadius) {
                            newDots.add(new Point(dot.x, dot.y));
                            isNearExistingDot = true;
                            break;
                        }
                    }

                    if (!isNearExistingDot) {
                        newDots.add(new Point(e.getX(), e.getY()));
                    }

                    canvasPanel.repaint();
                }
            }
        });


        JPanel settingsPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(350, 900);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Color getBackground() {
                return new Color(0xD4D9DB);
            }

        };

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

        add(left, BorderLayout.WEST);
        add(center);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // ########## buttons ############

        settingsPanel.setLayout(new GridBagLayout());

        JButton loadRoadButton = new JButton("Load Road Image");
        roadWithIntersectionsButton = new JButton("Road with Intersections");
        windingRoadButton = new JButton("Winding Road");
        saveToJsonButton = new JButton("Save to JSON File");
        streetNameField = new JTextField();

        category = new JComboBox<>(categoriesRoad);
        resetCombobox(category);

        lampCountField = createFormattedTextField();
        lampDistanceField = createFormattedTextField();
        addRoad = new JButton("Add road");
        clearAddedPoints = new JButton("Clear");

        settingsPanel.add(loadRoadButton, new Gbc(0,0,4).build());
        settingsPanel.add(Gbc.createVerticalStrut(25), new Gbc(0, 1, 4).build());
        settingsPanel.add(roadWithIntersectionsButton, new Gbc(0,2,2).build());
        settingsPanel.add(windingRoadButton, new Gbc(2,2,2).build());
        settingsPanel.add(new JLabel("Street Name:"), new Gbc(0, 3, 1).build());
        settingsPanel.add(streetNameField, new Gbc(1, 3, 3).build());
        settingsPanel.add(new JLabel("Options:"), new Gbc(0, 4, 1).build());
        settingsPanel.add(category, new Gbc(1, 4, 2).build());
        settingsPanel.add(new JLabel("Lamp Count:"), new Gbc(0, 5, 1).build());
        settingsPanel.add(lampCountField, new Gbc(1, 5, 3).build());
        settingsPanel.add(new JLabel("Lamp Distance:"), new Gbc(0, 6, 1).build());
        settingsPanel.add(lampDistanceField, new Gbc(1, 6, 3).build());
        settingsPanel.add(addRoad, new Gbc(0,7, 2).build());
        settingsPanel.add(clearAddedPoints, new Gbc(2,7, 2).build());
        settingsPanel.add(Gbc.createVerticalStrut(25), new Gbc(0, 8, 4).build());
        settingsPanel.add(saveToJsonButton, new Gbc(0,9,4).build());

        // ########## disable fields ############

        disableFields();
        windingRoadButton.setEnabled(false);
        roadWithIntersectionsButton.setEnabled(false);
        saveToJsonButton.setEnabled(false);

        // ########## button listeners ############

        loadRoadButton.addActionListener(e -> loadRoadImage());
        roadWithIntersectionsButton.addActionListener(e -> {
            intersectionMode = true;
            enableFields();
        });
        windingRoadButton.addActionListener(e -> {
            windingMode = true;
            enableFields();
        });
        addRoad.addActionListener(e -> addRoad());
        saveToJsonButton.addActionListener(e -> saveToJson());
        clearAddedPoints.addActionListener(e -> {
            newDots.clear();
            canvasPanel.repaint();
        });

    }

    private void resetCombobox(JComboBox<String> comboBox) {
        comboBox.setSelectedIndex(-1);

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "No selection" : getText());
                return comp;
            }
        });
    }

    private void loadRoadImage() {
        JFileChooser fileChooser = new JFileChooser();

        File programDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(programDirectory);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                backgroundImage = ImageIO.read(selectedFile);
                canvasPanel.repaint();
                windingRoadButton.setEnabled(true);
                roadWithIntersectionsButton.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void enableFields() {
        lampCountField.setEnabled(true);
        lampDistanceField.setEnabled(true);
        streetNameField.setEnabled(true);
        category.setEnabled(true);
        addRoad.setEnabled(true);
        addingPointsAllowed = true;
        clearAddedPoints.setEnabled(true);
    }

    private void disableFields() {
        lampCountField.setEnabled(false);
        lampDistanceField.setEnabled(false);
        streetNameField.setEnabled(false);
        category.setEnabled(false);
        addRoad.setEnabled(false);
        addingPointsAllowed = false;
        clearAddedPoints.setEnabled(false);
    }

    private void addRoad() {
        if (!areFieldsValid()) {
            JOptionPane.showMessageDialog(this, "Fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (windingMode) {
            RoadPoint roadPoint = new RoadPoint("winding", newDots, lampDistanceField.getText(), lampCountField.getText(), (String) category.getSelectedItem(), streetNameField.getText());
            roadPoints.add(roadPoint);
            dots.addAll(newDots);
        } else if (intersectionMode) {
            RoadPoint roadPoint = new RoadPoint("intersection", newDots, lampDistanceField.getText(), lampCountField.getText(), (String) category.getSelectedItem(), streetNameField.getText());
            roadPoints.add(roadPoint);
            dots.addAll(newDots);
        }

        newDots.clear();

        addingPointsAllowed = false;
        canvasPanel.repaint();
        disableFields();

        saveToJsonButton.setEnabled(true);
        clearFields();
    }

    private boolean areFieldsValid() {
        String streetName = streetNameField.getText().trim();
        String lampCount = lampCountField.getText().trim();
        String lampDistance = lampDistanceField.getText().trim();
        String selectedPreset = (String) category.getSelectedItem();

        if (streetName.isEmpty() || lampCount.isEmpty() || lampDistance.isEmpty() || selectedPreset == null) {
            return false;
        }

        try {
            Integer.parseInt(lampCount);
            Double.parseDouble(lampDistance);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private void clearFields() {
        streetNameField.setText("");
        lampCountField.setText("");
        lampDistanceField.setText("");
        resetCombobox(category);
    }

    private JTextField createFormattedTextField() {
        JTextField textField = new JTextField(2);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                currentText += text;
                if (currentText.length() <= 4 && currentText.matches("\\d*")) {
                    int value = Integer.parseInt(currentText);
                    if (value >= 0) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }
        });
        return textField;
    }


    public void saveToJson() {

        for (RoadPoint point : roadPoints) {
            System.out.println("Connection Type: " + point.getConnectionType());
            System.out.println("Dots: ");
//            for (Point dot : point.getDots()) {
//                System.out.print("(" + dot.x + ", " + dot.y + ") ");
//            }
            System.out.println("Lamp Distance: " + point.getLampDistance());
            System.out.println("Lamp Count: " + point.getLampCount());
            System.out.println("Street Name: " + point.getStreetName());
            System.out.println("Category: " + point.getCategory());
            System.out.println("=================");
        }


        JSONArray mainArray = new JSONArray();

        for (RoadPoint roadPoint : roadPoints) {
            if (intersectionMode && roadPoint.getConnectionType().equals("intersection")) {

                ArrayList<Point> dots = roadPoint.getArrayDots();

                for (int i = 0; i < dots.size() - 1; i++) {

                    Point currentPoint = dots.get(i);
                    Point nextPoint = dots.get(i + 1);

                    JSONObject jsonObject = new JSONObject();

                    JSONArray pointsArray = new JSONArray();
                    JSONObject pointObject = new JSONObject();

                    pointObject.put("x", currentPoint.getX());
                    pointObject.put("y", currentPoint.getY());
                    pointObject.put("x2", nextPoint.getX());
                    pointObject.put("y2", nextPoint.getY());
                    pointsArray.add(pointObject);

                    jsonObject.put("points", pointsArray);
                    jsonObject.put("lampDistance", roadPoint.getLampDistance());
                    jsonObject.put("lampCount", roadPoint.getLampCount());
                    jsonObject.put("roadCategory", roadPoint.getCategory());
                    jsonObject.put("streetName", roadPoint.getStreetName());

                    mainArray.add(jsonObject);
                }

            } else if (windingMode && roadPoint.getConnectionType().equals("winding")) {

                JSONObject jsonObject = new JSONObject();

                JSONArray pointsArray = new JSONArray();

                ArrayList<Point> dots = roadPoint.getArrayDots();

                for (int i = 0; i < dots.size() - 1; i++) {

                    Point currentPoint = dots.get(i);
                    Point nextPoint = dots.get(i + 1);

                    JSONObject pointObject = new JSONObject();
                    pointObject.put("x", currentPoint.getX());
                    pointObject.put("y", currentPoint.getY());
                    pointObject.put("x2", nextPoint.getX());
                    pointObject.put("y2", nextPoint.getY());
                    pointsArray.add(pointObject);
                }

                jsonObject.put("points", pointsArray);
                jsonObject.put("lampDistance", roadPoint.getLampDistance());
                jsonObject.put("lampCount", roadPoint.getLampCount());
                jsonObject.put("roadCategory", roadPoint.getCategory());
                jsonObject.put("streetName", roadPoint.getStreetName());

                mainArray.add(jsonObject);

            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save JSON File");

        // Set the default directory
        File programDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(programDirectory);

        // Set a file filter for JSON files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        fileChooser.setFileFilter(filter);

        // Show the save dialog
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            try (FileWriter fileWriter = new FileWriter(selectedFile)) {
                // Write JSON content to the selected file
                fileWriter.write(mainArray.toJSONString());
                System.out.println("JSON file created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}