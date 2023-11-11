import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private List<Path> pathList = new ArrayList<>();
    private Dot dot; // Add Dot instance

    public Canvas() {
        dot = new Dot(new Point2D.Double(0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the paths
        for (Path path : pathList) {
            path.draw(g2d, dot);
        }

        // Draw the dot
        dot.draw(g2d);
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
    
    
    
    


    public List<Path> getPathList() {
        return pathList;
    }

    public Dot getDot() {
        return dot;
    }

    public void setDotPath(Path path) {
        dot.setPath(path);
        dot.setPosition((Double) path.getStartPoint());
        repaint();
    }

    public void setDotPathList(List<Path> paths) {
        dot.setPathList(paths);
        repaint();
    }
}
