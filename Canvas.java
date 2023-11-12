import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Canvas extends JPanel {
    private List<Path> pathList = new ArrayList<>();
    private List<Dot> dots;  // Change to a list of Dots

    public Canvas(List<Dot> dots) {
        this.dots = dots;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw each Dot
        for (Dot dot : dots) {
            // Draw each Drawable in the Dot object
            for (Drawable drawable : dot.getDrawables()) {
                drawable.draw(g2d);
            }

            // Draw the dot
            dot.draw(g2d);
        }
    }


    public void drawPath(Path userPath) {
        pathList.add(userPath);
        repaint(); // Repaint the canvas after adding a path
    }

    public void drawConnectedPath(Path userPath, boolean connect) {
        if (connect && !pathList.isEmpty()) {
            Path lastPath = pathList.get(pathList.size() - 1);
            Point2D lastPoint = lastPath.getEndPoint();
            userPath.moveFirstPointTo(lastPoint);
        }
        
        // Append the new path to the existing paths
        if (!pathList.isEmpty()) {
            Path lastPath = pathList.get(pathList.size() - 1);
            Point2D endPoint = userPath.getEndPoint();
            Line2D.Double connectingLine = new Line2D.Double(lastPath.getEndPoint(), endPoint);
            lastPath.getPath2D().append(connectingLine, true);
        }
        
        pathList.add(userPath);
        repaint(); // Repaint the canvas after adding a connected path
    }

    public void drawConnectedPathList(List<Path> userPaths, boolean connect) {
        if (userPaths == null || userPaths.isEmpty()) {
            return;
        }
    
        Path firstPath = userPaths.get(0);
        drawPath(firstPath);
    
        // Connect each subsequent path in the list to the previous path
        for (int i = 1; i < userPaths.size(); i++) {
            Path currentPath = userPaths.get(i);
            Point2D lastPoint = firstPath.getEndPoint();
            currentPath.moveFirstPointTo(lastPoint);
    
            drawPath(currentPath);
    
            firstPath = currentPath;
        }
    
        repaint(); // Repaint the canvas after drawing the connected paths
    }
    
    public List<Dot> getDots() {
        return dots;
    }

    public void setDotPathList(List<Path> paths, Dot dot) {
        dot.setPathList(paths);
        repaint();
    }
    
    
    


    public List<Path> getPathList() {
        return pathList;
    }
}
