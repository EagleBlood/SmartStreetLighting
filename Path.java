import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Path {
    private Dot dot;
    private List<Lamp> lamps = new ArrayList<>();
    private Shape shape;
    private double lastX;
    private double lastY;
    private Path nextPath;
    
    public Path(Shape shape) {
        this.shape = shape;

        // Create a new dot if one doesn't already exist
        if (dot == null) {
            dot = new Dot(new Point2D.Double(50, 50), this);
            //dot = new Dot(path2D.getCurrentPoint().getX(), path2D.getCurrentPoint().getY());
        }
        
        updateLamps();
    }

    void updateLamps() {
        lamps.clear();
        double totalPathLength = getLength();
        double distance = 0.0;
        double interval = 100.0; // Interval between lamps
    
        while (distance < totalPathLength) {
            float[] pos = getPointAtLength(distance);
            lamps.add(new Lamp(new Point2D.Double(pos[0], pos[1])));
            distance += interval;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.draw(shape); // Draw the path
    
        dot.draw(g2d); // Draw the dot
    
        // Draw the lamps
        for (Lamp lamp : lamps) {
            lamp.draw(g2d, dot);
        }
    }

    public Shape getShape() {
        return shape;
    }

    public int getLength() {
        PathIterator pathIterator = shape.getPathIterator(null);
        double[] coords = new double[6];
        double length = 0;
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    lastX = coords[0];
                    lastY = coords[1];
                    break;
                case PathIterator.SEG_LINETO:
                    double x = coords[0];
                    double y = coords[1];
                    length += Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));
                    lastX = x;
                    lastY = y;
                    break;
                case PathIterator.SEG_QUADTO:
                    double x1 = coords[0];
                    double y1 = coords[1];
                    double x2 = coords[2];
                    double y2 = coords[3];
                    length += Math.sqrt((x1 - lastX) * (x1 - lastX) + (y1 - lastY) * (y1 - lastY));
                    length += Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                    lastX = x2;
                    lastY = y2;
                    break;
                case PathIterator.SEG_CUBICTO:
                    double x3 = coords[0];
                    double y3 = coords[1];
                    double x4 = coords[2];
                    double y4 = coords[3];
                    double x5 = coords[4];
                    double y5 = coords[5];
                    length += Math.sqrt((x3 - lastX) * (x3 - lastX) + (y3 - lastY) * (y3 - lastY));
                    length += Math.sqrt((x4 - x3) * (x4 - x3) + (y4 - y3) * (y4 - y3));
                    length += Math.sqrt((x5 - x4) * (x5 - x4) + (y5 - y4) * (y5 - y4));
                    lastX = x5;
                    lastY = y5;
                    break;
            }
            pathIterator.next();
        }
        return (int) Math.round(length);
    }

    public float[] getPointAtLength(double distance) {
        PathIterator pathIterator = shape.getPathIterator(null);
        double[] coords = new double[6];
        double totalLength = 0;
        double lastX = 0;
        double lastY = 0;
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    lastX = coords[0];
                    lastY = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double x = coords[0];
                    double y = coords[1];
                    double segmentLength = Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));
                    if (totalLength + segmentLength >= distance) {
                        double remainingLength = distance - totalLength;
                        double ratio = remainingLength / segmentLength;
                        float[] point = new float[2];
                        point[0] = (float) (lastX + ratio * (x - lastX));
                        point[1] = (float) (lastY + ratio * (y - lastY));
                        return point;
                    }
                    totalLength += segmentLength;
                    lastX = x;
                    lastY = y;
                    break;

                    case PathIterator.SEG_QUADTO:
                        // handle quadratic curve segment
                        break;

                    case PathIterator.SEG_CUBICTO:
                        // handle cubic curve segment
                        break;

            }
            pathIterator.next();
        }
        return null;
    }

    public Path getNextPath() {
        return nextPath;
    }

    public boolean hasNextPath() {
        return nextPath != null;
    }

    public Path getFirstPath() {
        Path firstPath = this;
        while (firstPath.hasNextPath()) {
            firstPath = firstPath.getNextPath();
        }
        return firstPath;
    }

    public Point2D getStartPoint() {
        return new Point2D.Double(shape.getBounds2D().getX(), shape.getBounds2D().getY());
    }

    public Point2D getEndPoint() {
        return new Point2D.Double(lastX, lastY);
    }

    public void moveFirstPointTo(Point2D lastPoint) {
        Path2D newPath = new Path2D.Double();
        PathIterator pathIterator = shape.getPathIterator(null);
        double[] coords = new double[6];
        boolean firstPoint = true;
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            if (firstPoint && segmentType == PathIterator.SEG_MOVETO) {
                newPath.moveTo(lastPoint.getX(), lastPoint.getY());
                firstPoint = false;
            } else {
                switch (segmentType) {
                    case PathIterator.SEG_MOVETO:
                        newPath.moveTo(coords[0], coords[1]);
                        break;
                    case PathIterator.SEG_LINETO:
                        newPath.lineTo(coords[0], coords[1]);
                        break;
                    // Add cases for other segment types if needed
                }
            }
            pathIterator.next();
        }
        shape = newPath;
    }



}
