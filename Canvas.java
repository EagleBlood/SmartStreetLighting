import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private Dot dot;
    private Path path; // Add the path object here
    private List<Path> paths = new ArrayList<>();
    private List<Dot> dots = new ArrayList<>();
    private List<Lamp> lamps = new ArrayList<>();

    private static final Color PATH_COLOR = Color.GRAY;
    private int pathThickness = 1;

    public Canvas(Path path) {
        this.path = path;
        lamps = new ArrayList<>();
        dot = new Dot(new Point2D.Double(50, 50), path);
        updateLamps();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(pathThickness));
        g2d.setColor(PATH_COLOR);

        // Draw the dynamic path
        path.draw(g2d);

        // Draw other elements, if any
        dot.draw(g); // Draw the dot object

        // Draw the lamps
        for (Lamp lamp : lamps) {
            lamp.draw(g, dot);
            //lamp.checkActivation(dot, path);
        }

        // Draw the paths
        for (Path path : paths) {
            path.draw(g2d);
        }
    }

    void updateLamps() {
        lamps.clear();
        double totalPathLength = path.getLength();
        double distance = 0.0;
        double interval = 100.0; // Interval between lamps
    
        while (distance < totalPathLength) {
            float[] pos = path.getPointAtLength(distance);
            lamps.add(new Lamp(new Point2D.Double(pos[0], pos[1])));
            distance += interval;
        }
    }

    public void addPath(Path userPath) {
        paths.add(userPath);
        
    }

    public List<Path> getPaths() {
        return paths;
    }
}
