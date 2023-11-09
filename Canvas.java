import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private List<Path> paths = new ArrayList<>();
    private static final Color PATH_COLOR = Color.GRAY;
    private int pathThickness = 1;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(pathThickness));
        g2d.setColor(PATH_COLOR);

        // Draw the paths
        for (Path path : paths) {
            path.draw(g2d);
        }
    }

    public void addPath(Path userPath) {
        paths.add(userPath);
    }

    public void addConnectedPath(Path userPath, boolean connect) {
        if (connect && !paths.isEmpty()) {
            Path lastPath = paths.get(paths.size() - 1);
            Point2D lastPoint = lastPath.getEndPoint();
            userPath.moveFirstPointTo(lastPoint);
        }
        paths.add(userPath);
    }

    public List<Path> getPaths() {
        return paths;
    }
}
