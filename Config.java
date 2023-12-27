import java.awt.geom.*;
import java.util.*;

public class Config {
    public static final int lampInterval = 50;
    public static final int lampCount = 10;
    public static final char roadCategory = 'A';

    public static final List<Drawable> PRESET1 = initializePreset(new ArrayList<>(List.of(
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(200, 200, 300, 300), true);
            append(new Line2D.Double(300, 300, 400, 300), true);
        }}, lampInterval, lampCount, 'A'),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 400, 200), true);
            append(new Line2D.Double(400, 200, 200, 200), true);
        }}, lampInterval, lampCount, 'B'),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 400, 400), true);
            append(new Line2D.Double(400, 400, 200, 400), true);
            append(new Line2D.Double(200, 400, 200, 200), true);
        }}, lampInterval, lampCount, 'A'),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 500, 300), true);
            append(new Line2D.Double(500, 300, 200, 300), true);
        }}, lampInterval, lampCount, 'B')
    )));
    /* 
    public static final List<Drawable> PRESET1 = initializePreset(new ArrayList<>(List.of(
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(200, 200, 300, 300), true);
            append(new Line2D.Double(300, 300, 400, 300), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 400, 200), true);
            append(new Line2D.Double(400, 200, 200, 200), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 400, 400), true);
            append(new Line2D.Double(400, 400, 200, 400), true);
            append(new Line2D.Double(200, 400, 200, 200), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(400, 300, 500, 300), true);
            append(new Line2D.Double(500, 300, 200, 300), true);
        }})
    )));

    public static final List<Drawable> PRESET2 = initializePreset(new ArrayList<>(List.of(
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(200, 200, 300, 200), true);
            append(new Line2D.Double(300, 200, 300, 300), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(300, 300, 500, 300), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(500, 300, 500, 400), true);
            append(new Line2D.Double(500, 400, 300, 300), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(500, 300, 500, 200), true);
            append(new Line2D.Double(500, 200, 300, 100), true);
            append(new Line2D.Double(300, 100, 200, 200), true);
        }})
    )));

    public static final List<Drawable> PRESET3 = initializePreset(new ArrayList<>(List.of(
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(200, 200, 300, 200), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(300, 200, 500, 300), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(500, 300, 500, 400), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(200, 200, 500, 200), true);
        }}),
        new Path(new Path2D.Float() {{
            append(new Line2D.Double(700, 700, 800, 800), true);
        }})
    )));
    */
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

    public static List<Drawable> getDrawablesForPreset(String presetName) {
        List<Drawable> allDrawables = new ArrayList<>();

        switch (presetName) {
            case "PRESET1":
                allDrawables.addAll(PRESET1);
                break;/* 
            case "PRESET2":
                allDrawables.addAll(PRESET2);
                break;
            case "PRESET3":
                allDrawables.addAll(PRESET3);
                break;
            */default:
                throw new IllegalArgumentException("Invalid preset name: " + presetName);
        }

        return allDrawables;
    }

}

