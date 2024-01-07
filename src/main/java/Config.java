import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class Config {
    private static String filePath = null;
    public static final List<Drawable> NO_PRESET = new ArrayList<>();
    public static final int lampInterval = 50;
    public static final int lampCount = 10;
    public static final char roadCategory = 'A';
    public static final String streetName = "Street name";


    public static final List<Drawable> PRESET1 = initializePreset(List.of(
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(200, 200, 300, 300), true);
            append(new Line2D.Double(300, 300, 400, 300), true);
        }}, lampInterval, lampCount, 'A', ""),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 400, 200), true);
            append(new Line2D.Double(400, 200, 200, 200), true);
        }}, lampInterval, lampCount, 'B', ""),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 400, 400), true);
            append(new Line2D.Double(400, 400, 200, 400), true);
            append(new Line2D.Double(200, 400, 200, 200), true);
        }}, lampInterval, lampCount, 'A', ""),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 500, 300), true);
            append(new Line2D.Double(500, 300, 200, 300), true);
        }}, lampInterval, lampCount, 'B', ""),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 500, 400), true);
            append(new Line2D.Double(500, 400, 200, 400), true);
            append(new Line2D.Double(200, 400, 200, 300), true);
        }}, lampInterval, lampCount, 'A', "")
    ));

    public static final List<Drawable> PRESET2 = initializePreset(new ArrayList<>(List.of(


            //krotkie uliczki


            // ul Krotka
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 200, 700, 200), true);
            }}, lampInterval, lampCount, 'B', "Street 1"),

            // ul krzywa
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 500, 700, 500), true);
            }}, lampInterval, lampCount, 'A', "Street 2"),

            // ul Owocowa
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(400, 50, 400, 200), true);
            }}, lampInterval, lampCount, 'B', "Street 3"),

            // ul warzywna

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 50, 550, 200), true);
            }}, lampInterval, lampCount, 'B', "Street 4"),

            // ul polna

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(50, 300, 150, 300), true);
            }}, lampInterval, lampCount, 'B' , "Street 5"),



            //kreta

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(150, 300, 250, 300), true);
                append(new Line2D.Double(250, 300, 250, 500), true);
                append(new Line2D.Double(250, 500, 550, 500), true);
            }}, lampInterval, lampCount, 'B' , "Street 6"),


            //ul. Polnocna
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(50, 50, 400, 50), true);
            }}, lampInterval, lampCount, 'B', "Street 7"),
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(400, 50, 550, 50), true);
            }}, lampInterval, lampCount, 'B', "Street 8"),
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 50, 650, 50), true);
                append(new Line2D.Double(650, 50, 700, 100), true);
                append(new Line2D.Double(700, 100, 700, 200), true);
            }}, lampInterval, lampCount, 'B', "Street 9"),

            //ul Wschodnia
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(700, 200, 700, 400), true);
            }}, lampInterval, lampCount, 'B', "Street 10"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(700, 400, 700, 500), true);
            }}, lampInterval, lampCount, 'B', "Street 11"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(700, 500, 700, 650), true);
            }}, lampInterval, lampCount, 'A', "Street 12"),

            //ul Poludniowa

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(700, 650, 550, 650), true);
            }}, lampInterval, lampCount, 'A' , "Street 13"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 650, 450, 650), true);
            }}, lampInterval, lampCount, 'A', "Street 14"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(450, 650, 150, 650), true);
            }}, lampInterval, lampCount, 'A', "Street 15"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(150, 650, 50, 650), true);
            }}, lampInterval, lampCount, 'B', "Street 16"),

            //ul Zachodnia

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(50, 650, 50, 300), true);
            }}, lampInterval, lampCount, 'B', "Street 17"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(50, 300, 50, 200), true);
            }}, lampInterval, lampCount, 'B', "Street 18"),
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(50, 200, 50, 50), true);
            }}, lampInterval, lampCount, 'A', "Street 19"),

            // ul. srodkowa

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(50, 200, 150, 200), true);
            }}, lampInterval, lampCount, 'A', "Street 20"),
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(150, 200, 400, 200), true);
            }}, lampInterval, lampCount, 'A', "Street 21"),
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(400, 200, 550, 200), true);
            }}, lampInterval, lampCount, 'A', "Street 22"),


            //ul glowna
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 200, 550, 500), true);
            }}, lampInterval, lampCount, 'A', "Street 23"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(550, 500, 550, 650), true);
            }}, lampInterval, lampCount, 'A', "Street 24"),

            //ul. mickiewicza
            new Path(new Path2D.Float() {{
                append(new Line2D.Double(150, 650, 150, 300), true);
            }}, lampInterval, lampCount, 'A', "Street 25"),

            new Path(new Path2D.Float() {{
                append(new Line2D.Double(150, 300, 150, 200), true);
            }}, lampInterval, lampCount, 'A', "Street 26")

    )));

    private static List<Drawable> initializePreset(List<Drawable> preset) {
        for (Drawable drawable : preset) {
            if (drawable instanceof Path) {

                Path path = (Path) drawable;
                path.setConnectedDrawables(preset); // Set drawables list
                path.initializeAfterSettingDrawables();
            }
        }
        return preset;
    }


    public static List<Drawable> PRESET5 = new ArrayList<>();

    public static List<Drawable> loadDrawablesFromConfigFile(String filePath) {
        List<Drawable> preset = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);

            if (obj instanceof JSONArray) {
                JSONArray drawablesArray = (JSONArray) obj;

                for (Object drawableObj : drawablesArray) {
                    if (drawableObj instanceof JSONObject) {
                        try {
                            JSONObject drawableJson = (JSONObject) drawableObj;
                            JSONArray pointsArray = (JSONArray) drawableJson.get("points");
                            Path2D.Float path2D = new Path2D.Float();

                            for (Object o : pointsArray) {
                                JSONObject pointJson = (JSONObject) o;

                                Double x1 = getDoubleValue(pointJson, "x1", "x");
                                Double y1 = getDoubleValue(pointJson, "y1", "y");
                                Double x2 = getDoubleValue(pointJson, "x2", "x");
                                Double y2 = getDoubleValue(pointJson, "y2", "y");

                                if (x1 != null && y1 != null && x2 != null && y2 != null) {
                                    if (path2D.getCurrentPoint() == null) {
                                        path2D.moveTo(x1, y1);
                                    } else {
                                        path2D.lineTo(x1, y1);
                                    }

                                    path2D.lineTo(x2, y2);

                                    // If you also want to create Line2D.Double
                                    Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
                                    // Use 'line' as needed, e.g., add it to a list or process it in some way
                                } else {
                                    System.err.println("Invalid values for x1, y1, x2, or y2 in pointsArray");
                                }
                            }

                            double lampDistance = ((Number) drawableJson.get("lampDistance")).doubleValue();
                            double lampCount = ((Number) drawableJson.get("lampCount")).doubleValue();
                            String roadCategoryStr = (String) drawableJson.get("roadCategory");
                            String streetName = (String) drawableJson.get("streetName");
                            char roadCategory = roadCategoryStr.charAt(0);
                            Path path = new Path(path2D, (int) lampDistance, (int) lampCount, roadCategory, streetName);
                            preset.add(path);

                        } catch (ClassCastException e) {
                            // Handle the case where the cast to JSONObject is not successful
                            e.printStackTrace();
                        }
                    }
                    // Add other drawable types if needed
                }
            } else {
                System.err.println("Invalid JSON format. Expected an array.");
            }
        } catch (NoSuchFileException e) {
            System.err.println("File not found: " + e.getFile());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return preset;
    }

    public static void setFilePath(String path) {
        System.out.println(path);
        filePath = path;
    }

    private static Double getDoubleValue(JSONObject json, String key1, String key2) {
        Object value = json.get(key1);
        if (value == null) {
            value = json.get(key2);
        }

        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Long) {
            // Handle Long values, if necessary
            return ((Long) value).doubleValue();
        } else {
            return null; // or handle other cases as needed
        }
    }
    public static List<Drawable> getDrawablesForPreset(String presetName) {

        List<Drawable> allDrawables = new ArrayList<>();

        switch (presetName) {
            case "PRESET1":
                allDrawables.addAll(PRESET1);
                break;
            case "PRESET2":
                allDrawables.addAll(PRESET2);
                break;
            case "Your config":
                if (filePath != null) {
                    PRESET5 = initializePreset(loadDrawablesFromConfigFile(filePath));
                    allDrawables.addAll(PRESET5);
                } else {
                    System.err.println("File path is null. Cannot load drawables.");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid preset name: " + presetName);
        }

        return allDrawables;
    }

    public static void resetConfig() {
        filePath = null;
        PRESET5.clear();
    }

}

