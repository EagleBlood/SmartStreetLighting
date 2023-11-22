import java.awt.geom.*;
import java.util.*;

public class Config {
    public static final List<Drawable> PRESET1 = List.of(
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
    );

    public static final List<Drawable> PRESET2 = List.of(
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
            append(new Line2D.Double(500, 200, 300, 300), true);
        }})
    );

     public static final List<Drawable> PRESET3 = List.of(
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
    );
}

